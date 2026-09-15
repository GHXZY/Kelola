import React from 'react';
import { X, Receipt, Trash2, RotateCcw } from 'lucide-react';
import { TransactionEntity, TransactionItemEntity, BusinessSettings } from '../types';
import { formatRupiah, formatDateTime } from '../utils/format';

interface TransactionDetailDialogProps {
  transaction: TransactionEntity;
  items: TransactionItemEntity[];
  settings: BusinessSettings;
  onCancelTransaction: (txId: number) => void;
  onDeleteTransaction: (txId: number) => void;
  onDismiss: () => void;
}

export const TransactionDetailDialog: React.FC<TransactionDetailDialogProps> = ({
  transaction,
  items,
  settings,
  onCancelTransaction,
  onDeleteTransaction,
  onDismiss
}) => {
  const isCancelled = transaction.status === 'CANCELLED';

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-sm p-5 shadow-dialog border border-slate-100 flex flex-col max-h-[90vh] animate-scaleUp">
        {/* Header */}
        <div className="flex items-center justify-between pb-3 border-b border-slate-100 flex-shrink-0">
          <div className="flex items-center gap-2">
            <Receipt className="w-5 h-5 text-brand-primary" />
            <h3 className="text-base font-bold text-slate-800">Detail Struk Transaksi</h3>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Struk Content */}
        <div className="flex-1 overflow-y-auto py-3 space-y-3 font-mono text-xs">
          {/* Store Info */}
          <div className="text-center font-sans">
            <h4 className="font-bold text-sm text-slate-800">{settings.businessName}</h4>
            <p className="text-[11px] text-slate-400">{settings.address}</p>
            <p className="text-[11px] text-slate-400">{settings.phone}</p>
            <div className="mt-2 text-[10px] text-slate-400 border-t border-b border-dashed border-slate-200 py-1 flex justify-between">
              <span>{transaction.transactionNumber}</span>
              <span>{formatDateTime(transaction.createdAt)}</span>
            </div>
          </div>

          {/* Items List */}
          <div className="space-y-1.5 pt-1">
            {items.length === 0 ? (
              <div className="text-slate-400 text-center py-2 font-sans">Produk kasir reguler</div>
            ) : (
              items.map((item) => (
                <div key={item.id} className="flex justify-between items-start">
                  <div>
                    <div className="font-medium text-slate-800">{item.productNameSnapshot}</div>
                    <div className="text-[10px] text-slate-400">
                      {item.quantity} × {formatRupiah(item.sellingPriceSnapshot)}
                    </div>
                  </div>
                  <span className="font-semibold text-slate-700">
                    {formatRupiah(item.quantity * item.sellingPriceSnapshot)}
                  </span>
                </div>
              ))
            )}
          </div>

          {/* Summary Breakdown */}
          <div className="pt-2 border-t border-dashed border-slate-300 space-y-1">
            <div className="flex justify-between text-slate-500">
              <span>Subtotal</span>
              <span>{formatRupiah(transaction.subtotal)}</span>
            </div>
            {transaction.discount > 0 && (
              <div className="flex justify-between text-emerald-600">
                <span>Diskon</span>
                <span>-{formatRupiah(transaction.discount)}</span>
              </div>
            )}
            <div className="flex justify-between font-bold text-sm text-slate-900 pt-1 border-t border-slate-200">
              <span>TOTAL</span>
              <span className="text-brand-primary">{formatRupiah(transaction.total)}</span>
            </div>
            <div className="flex justify-between text-[11px] text-slate-500">
              <span>Metode</span>
              <span>{transaction.paymentMethod}</span>
            </div>
            {transaction.paymentMethod === 'Tunai' && (
              <>
                <div className="flex justify-between text-[11px] text-slate-500">
                  <span>Bayar</span>
                  <span>{formatRupiah(transaction.cashReceived)}</span>
                </div>
                <div className="flex justify-between text-[11px] text-emerald-600 font-semibold">
                  <span>Kembali</span>
                  <span>{formatRupiah(transaction.change)}</span>
                </div>
              </>
            )}
          </div>

          {/* Receipt Footer */}
          {settings.receiptFooter && (
            <p className="text-center text-[10px] text-slate-400 font-sans italic pt-2 border-t border-dashed border-slate-200">
              "{settings.receiptFooter}"
            </p>
          )}
        </div>

        {/* Action Buttons */}
        <div className="pt-3 border-t border-slate-100 flex-shrink-0 flex gap-2">
          {!isCancelled && (
            <button
              onClick={() => {
                onCancelTransaction(transaction.id);
                onDismiss();
              }}
              className="flex-1 h-10 rounded-input bg-danger-container hover:bg-red-200 text-danger text-xs font-semibold flex items-center justify-center gap-1 transition-all"
            >
              <RotateCcw className="w-3.5 h-3.5" />
              <span>Batal & Retur</span>
            </button>
          )}

          <button
            onClick={() => {
              onDeleteTransaction(transaction.id);
              onDismiss();
            }}
            className="h-10 px-3 rounded-input text-slate-400 hover:text-danger hover:bg-red-50 flex items-center justify-center transition-all"
            title="Hapus Struk"
          >
            <Trash2 className="w-4 h-4" />
          </button>

          <button
            onClick={onDismiss}
            className="flex-1 h-10 rounded-input bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-semibold flex items-center justify-center transition-all"
          >
            Tutup
          </button>
        </div>
      </div>
    </div>
  );
};
