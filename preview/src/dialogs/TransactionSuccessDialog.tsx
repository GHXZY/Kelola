import React from 'react';
import { Check, X, Receipt, Plus } from 'lucide-react';
import { TransactionEntity } from '../types';
import { formatRupiah } from '../utils/format';

interface TransactionSuccessDialogProps {
  transaction: TransactionEntity;
  onNewSale: () => void;
  onViewDetail: (tx: TransactionEntity) => void;
  onDismiss: () => void;
}

export const TransactionSuccessDialog: React.FC<TransactionSuccessDialogProps> = ({
  transaction,
  onNewSale,
  onViewDetail,
  onDismiss
}) => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-sm p-5 shadow-dialog border border-slate-100 flex flex-col items-center text-center animate-scaleUp relative">
        {/* Top close button */}
        <button
          onClick={onDismiss}
          className="absolute right-4 top-4 text-slate-400 hover:text-slate-700"
        >
          <X className="w-5 h-5" />
        </button>

        {/* Big Success Badge */}
        <div className="w-16 h-16 rounded-full bg-emerald-100 text-emerald-600 flex items-center justify-center mb-3">
          <Check className="w-8 h-8 stroke-[2.5]" />
        </div>

        <h3 className="text-lg font-bold text-slate-800">Transaksi Berhasil!</h3>
        <p className="text-xs text-slate-400 mb-4">{transaction.transactionNumber}</p>

        {/* Breakdown Card */}
        <div className="w-full bg-slate-50 rounded-input p-3.5 border border-slate-200/80 space-y-2 text-xs mb-5 text-left">
          <div className="flex justify-between text-slate-600">
            <span>Total Tagihan:</span>
            <span className="font-bold text-slate-800">{formatRupiah(transaction.total)}</span>
          </div>

          {transaction.paymentMethod === 'Tunai' ? (
            <>
              <div className="flex justify-between text-slate-500">
                <span>Uang Diterima:</span>
                <span>{formatRupiah(transaction.cashReceived)}</span>
              </div>
              <div className="flex justify-between font-bold text-emerald-600 pt-1 border-t border-slate-200">
                <span>Kembalian:</span>
                <span className="text-sm">{formatRupiah(transaction.change)}</span>
              </div>
            </>
          ) : (
            <div className="flex justify-between text-slate-500">
              <span>Metode:</span>
              <span className="font-bold text-brand-primary">{transaction.paymentMethod}</span>
            </div>
          )}
        </div>

        {/* Action Buttons */}
        <div className="w-full space-y-2">
          <button
            onClick={onNewSale}
            className="w-full h-11 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-xs font-bold flex items-center justify-center gap-1.5 shadow-md active:scale-[0.99] transition-all"
          >
            <Plus className="w-4 h-4" />
            <span>Transaksi Baru</span>
          </button>

          <button
            onClick={() => onViewDetail(transaction)}
            className="w-full h-11 rounded-input bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-semibold flex items-center justify-center gap-1.5 transition-all"
          >
            <Receipt className="w-4 h-4 text-slate-500" />
            <span>Lihat Struk / Detail</span>
          </button>
        </div>
      </div>
    </div>
  );
};
