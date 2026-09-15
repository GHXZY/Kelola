import React, { useState } from 'react';
import { X, Check, CreditCard, Wallet, QrCode } from 'lucide-react';
import { DebtEntity } from '../types';
import { formatRupiah } from '../utils/format';

interface SettleDebtDialogProps {
  debt: DebtEntity;
  onConfirmSettle: (debtId: number, amount: number, paymentMethod: string, note: string) => void;
  onDismiss: () => void;
}

export const SettleDebtDialog: React.FC<SettleDebtDialogProps> = ({
  debt,
  onConfirmSettle,
  onDismiss
}) => {
  const [isFullPayment, setIsFullPayment] = useState(true);
  const [partialAmountInput, setPartialAmountInput] = useState(debt.remainingAmount.toString());
  const [selectedMethod, setSelectedMethod] = useState('Tunai');
  const [note, setNote] = useState('');

  const partialAmount = Number(partialAmountInput) || 0;
  const settleAmount = isFullPayment ? debt.remainingAmount : partialAmount;
  const isAmountValid = settleAmount > 0 && settleAmount <= debt.remainingAmount;

  const paymentMethods = ['Tunai', 'QRIS', 'Transfer', 'E-Wallet'];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!isAmountValid) return;

    onConfirmSettle(debt.id, settleAmount, selectedMethod, note);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-sm p-5 shadow-dialog border border-slate-100 flex flex-col animate-scaleUp">
        {/* Header */}
        <div className="flex items-center justify-between pb-3 border-b border-slate-100 flex-shrink-0">
          <div>
            <h3 className="text-base font-bold text-slate-800">Pelunasan Kasbon</h3>
            <p className="text-xs text-slate-400">{debt.customerName}</p>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="py-3 space-y-3.5">
          {/* Sisa Kasbon Card */}
          <div className="bg-amber-50/70 border border-amber-200 rounded-input p-3 flex justify-between items-center text-xs">
            <span className="font-medium text-amber-900">Sisa Tagihan:</span>
            <span className="text-base font-bold text-amber-900">{formatRupiah(debt.remainingAmount)}</span>
          </div>

          {/* Full vs Partial Toggle */}
          <div className="grid grid-cols-2 gap-2">
            <button
              type="button"
              onClick={() => setIsFullPayment(true)}
              className={`py-2 rounded-input text-xs font-semibold border transition-all ${
                isFullPayment
                  ? 'bg-brand-primary border-brand-primary text-white shadow-xs'
                  : 'bg-slate-50 border-slate-200 text-slate-700 hover:bg-slate-100'
              }`}
            >
              Lunasi Penuh
            </button>

            <button
              type="button"
              onClick={() => setIsFullPayment(false)}
              className={`py-2 rounded-input text-xs font-semibold border transition-all ${
                !isFullPayment
                  ? 'bg-brand-primary border-brand-primary text-white shadow-xs'
                  : 'bg-slate-50 border-slate-200 text-slate-700 hover:bg-slate-100'
              }`}
            >
              Cicil Sebagian
            </button>
          </div>

          {/* Partial Input */}
          {!isFullPayment && (
            <div>
              <label className="text-xs font-semibold text-slate-700 block mb-1">Nominal Cicilan (Rp)</label>
              <input
                type="number"
                value={partialAmountInput}
                onChange={(e) => setPartialAmountInput(e.target.value)}
                className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-base font-bold focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
              />
              <span className="text-[11px] text-slate-400 block mt-1">
                Sisa setelah bayar: {formatRupiah(Math.max(0, debt.remainingAmount - partialAmount))}
              </span>
            </div>
          )}

          {/* Payment Method */}
          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1.5">Metode Terima Uang</label>
            <div className="flex flex-wrap gap-1.5">
              {paymentMethods.map((m) => (
                <button
                  type="button"
                  key={m}
                  onClick={() => setSelectedMethod(m)}
                  className={`px-3 py-1.5 rounded-chip text-xs font-semibold border transition-all ${
                    selectedMethod === m
                      ? 'bg-brand-primary border-brand-primary text-white'
                      : 'bg-slate-50 border-slate-200 text-slate-700 hover:bg-slate-100'
                  }`}
                >
                  {m}
                </button>
              ))}
            </div>
          </div>

          {/* Note Input */}
          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Catatan Tambahan (Opsional)</label>
            <input
              type="text"
              value={note}
              onChange={(e) => setNote(e.target.value)}
              placeholder="Misal: Cicilan ke-1 lunas"
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
            />
          </div>

          {/* Total Settle Banner */}
          <div className="bg-emerald-50 border border-emerald-200 rounded-input p-3 flex justify-between items-center text-xs">
            <span className="font-semibold text-emerald-800">Uang Diterima:</span>
            <span className="text-base font-bold text-emerald-700">{formatRupiah(settleAmount)}</span>
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={!isAmountValid}
            className={`w-full h-12 rounded-input text-white text-xs font-bold flex items-center justify-center gap-2 shadow-md transition-all active:scale-[0.99] ${
              !isAmountValid
                ? 'bg-slate-300 text-slate-500 cursor-not-allowed'
                : 'bg-emerald-600 hover:bg-emerald-700'
            }`}
          >
            <Check className="w-4 h-4" />
            <span>Konfirmasi Terima Pembayaran</span>
          </button>
        </form>
      </div>
    </div>
  );
};
