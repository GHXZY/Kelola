package com.example.ui.promo

import com.example.data.local.entity.PromoEntity
import com.example.util.FormatUtils
import org.json.JSONArray
import org.json.JSONObject

data class PromoRequirement(
    val productId: Long,
    val quantity: Int
)

data class AppliedPromoInfo(
    val promoId: Long,
    val promoName: String,
    val timesApplied: Int,
    val discountAmount: Long,
    val description: String,
    val discountType: String = "NOMINAL",
    val freeProductId: Long? = null,
    val freeProductName: String? = null,
    val freeQuantityEligible: Int = 0,
    val freeQuantityInCart: Int = 0
)

data class PromoEvaluationResult(
    val totalDiscount: Long = 0L,
    val appliedPromos: List<AppliedPromoInfo> = emptyList()
)

object PromoEngine {

    fun parseRequirements(jsonStr: String): List<PromoRequirement> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val jsonArray = JSONArray(jsonStr)
            val list = mutableListOf<PromoRequirement>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val prodId = obj.optLong("productId", -1L)
                val qty = obj.optInt("quantity", 1)
                if (prodId > 0 && qty > 0) {
                    list.add(PromoRequirement(productId = prodId, quantity = qty))
                }
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun serializeRequirements(requirements: List<PromoRequirement>): String {
        val jsonArray = JSONArray()
        requirements.forEach { req ->
            val obj = JSONObject().apply {
                put("productId", req.productId)
                put("quantity", req.quantity)
            }
            jsonArray.put(obj)
        }
        return jsonArray.toString()
    }

    /**
     * Evaluates the current cart quantities against active promo rules.
     * Supports:
     * 1. NOMINAL (Potongan Tunai Rp)
     * 2. PERCENTAGE (Potongan Persentase %)
     * 3. FREE_PRODUCT (Gratis Produk / Buy X Get Y Free)
     */
    fun evaluatePromos(
        cartItems: Map<Long, Int>, // productId -> quantity in cart
        productPrices: Map<Long, Long>, // productId -> selling price
        activePromos: List<PromoEntity>,
        productNames: Map<Long, String> = emptyMap()
    ): PromoEvaluationResult {
        if (cartItems.isEmpty() || activePromos.isEmpty()) {
            return PromoEvaluationResult()
        }

        // Mutable map tracking remaining unassigned items in cart for bundling
        val availableQty = cartItems.toMutableMap()
        val appliedList = mutableListOf<AppliedPromoInfo>()
        var totalDiscount = 0L

        // Prioritas evaluasi: Promo Gratis Produk atau diskon nominal/persen tertinggi
        val sortedPromos = activePromos.filter { it.isActive }.sortedWith(
            compareByDescending<PromoEntity> {
                if (it.discountType.uppercase() == "FREE_PRODUCT") {
                    // Nilai estimasi gratis produk
                    val freePrice = it.freeProductId?.let { id -> productPrices[id] } ?: 0L
                    freePrice * it.freeQuantity.coerceAtLeast(1)
                } else {
                    it.discountValue
                }
            }.thenBy { it.id }
        )

        for (promo in sortedPromos) {
            val requirements = parseRequirements(promo.requiredItemsJson)
            if (requirements.isEmpty()) continue

            // Check how many times all required products can be formed
            val possibleTimes = requirements.minOfOrNull { req ->
                val inCart = availableQty.getOrDefault(req.productId, 0)
                inCart / req.quantity
            } ?: 0

            if (possibleTimes <= 0) continue

            // Respect maxUsage if specified (> 0)
            val timesToApply = if (promo.maxUsage > 0) {
                possibleTimes.coerceAtMost(promo.maxUsage)
            } else {
                possibleTimes
            }

            if (timesToApply <= 0) continue

            if (promo.discountType.uppercase() == "FREE_PRODUCT") {
                val freeProdId = promo.freeProductId
                if (freeProdId != null) {
                    val eligibleFreeQty = timesToApply * promo.freeQuantity.coerceAtLeast(1)

                    // Deduct requirements first
                    requirements.forEach { req ->
                        val cur = availableQty.getOrDefault(req.productId, 0)
                        availableQty[req.productId] = (cur - (req.quantity * timesToApply)).coerceAtLeast(0)
                    }

                    // Check how many free items exist in cart to apply 100% discount
                    val freeInCart = availableQty.getOrDefault(freeProdId, 0)
                    val freeQtyToDeduct = minOf(eligibleFreeQty, freeInCart)
                    if (freeQtyToDeduct > 0) {
                        availableQty[freeProdId] = (freeInCart - freeQtyToDeduct).coerceAtLeast(0)
                    }

                    val unitPrice = productPrices[freeProdId] ?: 0L
                    val discountForPromo = freeQtyToDeduct * unitPrice
                    totalDiscount += discountForPromo

                    val freeName = productNames[freeProdId] ?: "Produk Gratis"
                    val desc = if (freeQtyToDeduct >= eligibleFreeQty) {
                        "Gratis $eligibleFreeQty x $freeName"
                    } else if (freeQtyToDeduct > 0) {
                        "Gratis $freeQtyToDeduct/$eligibleFreeQty x $freeName"
                    } else {
                        "Berhak Gratis $eligibleFreeQty x $freeName"
                    }

                    appliedList.add(
                        AppliedPromoInfo(
                            promoId = promo.id,
                            promoName = promo.name,
                            timesApplied = timesToApply,
                            discountAmount = discountForPromo,
                            description = desc,
                            discountType = "FREE_PRODUCT",
                            freeProductId = freeProdId,
                            freeProductName = freeName,
                            freeQuantityEligible = eligibleFreeQty,
                            freeQuantityInCart = freeQtyToDeduct
                        )
                    )
                }
            } else {
                val totalBundleValue = requirements.sumOf { req ->
                    val price = productPrices[req.productId] ?: 0L
                    price * req.quantity
                } * timesToApply

                // Calculate discount
                val rawDiscount = when (promo.discountType.uppercase()) {
                    "PERCENTAGE" -> {
                        val clampedPercent = promo.discountValue.coerceIn(0L, 100L)
                        (totalBundleValue * clampedPercent) / 100
                    }
                    else -> {
                        // NOMINAL
                        promo.discountValue * timesToApply
                    }
                }

                // Ensure discount does not exceed bundle retail value if products have price
                val discountForPromo = if (totalBundleValue > 0) {
                    rawDiscount.coerceAtMost(totalBundleValue)
                } else {
                    rawDiscount
                }

                if (discountForPromo > 0) {
                    // Deduct items from available cart pool
                    requirements.forEach { req ->
                        val cur = availableQty.getOrDefault(req.productId, 0)
                        availableQty[req.productId] = (cur - (req.quantity * timesToApply)).coerceAtLeast(0)
                    }

                    totalDiscount += discountForPromo
                    val desc = if (promo.discountType.uppercase() == "PERCENTAGE") {
                        "${promo.discountValue}% (${timesToApply}x)"
                    } else {
                        "${FormatUtils.formatRupiah(discountForPromo)} (${timesToApply}x)"
                    }

                    appliedList.add(
                        AppliedPromoInfo(
                            promoId = promo.id,
                            promoName = promo.name,
                            timesApplied = timesToApply,
                            discountAmount = discountForPromo,
                            description = desc,
                            discountType = promo.discountType.uppercase()
                        )
                    )
                }
            }
        }

        return PromoEvaluationResult(
            totalDiscount = totalDiscount,
            appliedPromos = appliedList
        )
    }
}
