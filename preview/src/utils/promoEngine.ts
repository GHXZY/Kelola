import { PromoEntity, PromoRequirement } from '../types';
import { formatRupiah } from './format';

export interface AppliedPromoInfo {
  promoId: number;
  promoName: string;
  timesApplied: number;
  discountAmount: number;
  description: string;
  discountType: 'NOMINAL' | 'PERCENTAGE' | 'FREE_PRODUCT';
  freeProductId?: number | null;
  freeProductName?: string | null;
  freeQuantityEligible?: number;
  freeQuantityInCart?: number;
}

export interface PromoEvaluationResult {
  totalDiscount: number;
  appliedPromos: AppliedPromoInfo[];
}

export function parseRequirements(jsonStr: string): PromoRequirement[] {
  if (!jsonStr || !jsonStr.trim()) return [];
  try {
    const parsed = JSON.parse(jsonStr);
    if (!Array.isArray(parsed)) return [];
    return parsed
      .map((item: any) => ({
        productId: Number(item.productId || item.id),
        quantity: Number(item.quantity || item.qty || 1)
      }))
      .filter((item) => item.productId > 0 && item.quantity > 0);
  } catch (e) {
    return [];
  }
}

export function serializeRequirements(requirements: PromoRequirement[]): string {
  return JSON.stringify(requirements);
}

/**
 * Evaluates the current cart quantities against active promo rules.
 * Supports:
 * 1. NOMINAL (Potongan Tunai Rp)
 * 2. PERCENTAGE (Potongan Persentase %)
 * 3. FREE_PRODUCT (Gratis Produk)
 */
export function evaluatePromos(
  cartItems: { [productId: number]: number },
  productPrices: { [productId: number]: number },
  activePromos: PromoEntity[],
  productNames: { [productId: number]: string } = {}
): PromoEvaluationResult {
  const itemKeys = Object.keys(cartItems);
  if (itemKeys.length === 0 || activePromos.length === 0) {
    return { totalDiscount: 0, appliedPromos: [] };
  }

  // Mutable map tracking remaining unassigned items in cart for bundling
  const availableQty: { [productId: number]: number } = { ...cartItems };
  const appliedList: AppliedPromoInfo[] = [];
  let totalDiscount = 0;

  // Filter only active promos
  const sortedPromos = [...activePromos]
    .filter((p) => p.isActive)
    .sort((a, b) => {
      const valA = a.discountType === 'FREE_PRODUCT' 
        ? (productPrices[a.freeProductId || 0] || 0) * (a.freeQuantity || 1)
        : a.discountValue;
      const valB = b.discountType === 'FREE_PRODUCT' 
        ? (productPrices[b.freeProductId || 0] || 0) * (b.freeQuantity || 1)
        : b.discountValue;
      return valB - valA;
    });

  for (const promo of sortedPromos) {
    const requirements = parseRequirements(promo.requiredItemsJson);
    if (requirements.length === 0) continue;

    // Check how many times all required products can be formed
    let possibleTimes = Number.MAX_SAFE_INTEGER;
    for (const req of requirements) {
      const inCart = availableQty[req.productId] || 0;
      const times = Math.floor(inCart / req.quantity);
      if (times < possibleTimes) {
        possibleTimes = times;
      }
    }

    if (possibleTimes <= 0 || possibleTimes === Number.MAX_SAFE_INTEGER) continue;

    // Respect maxUsage if specified (> 0)
    const timesToApply = promo.maxUsage > 0 ? Math.min(possibleTimes, promo.maxUsage) : possibleTimes;
    if (timesToApply <= 0) continue;

    const promoDisplayName = promo.name || promo.title || 'Promo Bundling';

    if (promo.discountType === 'FREE_PRODUCT' && promo.freeProductId) {
      const freeProdId = promo.freeProductId;
      const eligibleFreeQty = timesToApply * (promo.freeQuantity || 1);

      // Deduct requirements first
      for (const req of requirements) {
        availableQty[req.productId] = Math.max(0, (availableQty[req.productId] || 0) - req.quantity * timesToApply);
      }

      // Check how many free items exist in cart to apply 100% discount
      const freeInCart = availableQty[freeProdId] || 0;
      const freeQtyToDeduct = Math.min(eligibleFreeQty, freeInCart);
      if (freeQtyToDeduct > 0) {
        availableQty[freeProdId] = Math.max(0, freeInCart - freeQtyToDeduct);
      }

      const unitPrice = productPrices[freeProdId] || 0;
      const discountForPromo = freeQtyToDeduct * unitPrice;
      totalDiscount += discountForPromo;

      const freeName = productNames[freeProdId] || 'Produk Gratis';
      let desc = '';
      if (freeQtyToDeduct >= eligibleFreeQty) {
        desc = `Gratis ${eligibleFreeQty}x ${freeName}`;
      } else if (freeQtyToDeduct > 0) {
        desc = `Gratis ${freeQtyToDeduct}/${eligibleFreeQty}x ${freeName}`;
      } else {
        desc = `Berhak Gratis ${eligibleFreeQty}x ${freeName}`;
      }

      appliedList.push({
        promoId: promo.id,
        promoName: promoDisplayName,
        timesApplied: timesToApply,
        discountAmount: discountForPromo,
        description: desc,
        discountType: 'FREE_PRODUCT',
        freeProductId: freeProdId,
        freeProductName: freeName,
        freeQuantityEligible: eligibleFreeQty,
        freeQuantityInCart: freeQtyToDeduct
      });
    } else {
      let totalBundleValue = 0;
      for (const req of requirements) {
        const price = productPrices[req.productId] || 0;
        totalBundleValue += price * req.quantity;
      }
      totalBundleValue *= timesToApply;

      let rawDiscount = 0;
      if (promo.discountType === 'PERCENTAGE') {
        const clampedPercent = Math.min(100, Math.max(0, promo.discountValue));
        rawDiscount = Math.floor((totalBundleValue * clampedPercent) / 100);
      } else {
        rawDiscount = promo.discountValue * timesToApply;
      }

      const discountForPromo = totalBundleValue > 0 ? Math.min(rawDiscount, totalBundleValue) : rawDiscount;

      if (discountForPromo > 0) {
        for (const req of requirements) {
          availableQty[req.productId] = Math.max(0, (availableQty[req.productId] || 0) - req.quantity * timesToApply);
        }

        totalDiscount += discountForPromo;
        const desc = promo.discountType === 'PERCENTAGE'
          ? `${promo.discountValue}% (${timesToApply}x)`
          : `${formatRupiah(discountForPromo)} (${timesToApply}x)`;

        appliedList.push({
          promoId: promo.id,
          promoName: promoDisplayName,
          timesApplied: timesToApply,
          discountAmount: discountForPromo,
          description: desc,
          discountType: promo.discountType
        });
      }
    }
  }

  return {
    totalDiscount,
    appliedPromos: appliedList
  };
}
