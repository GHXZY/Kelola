import React, { useState } from 'react';
import { X, Plus, Minus, Trash2, Check, ShoppingBag } from 'lucide-react';
import { DebtEntity, ProductEntity } from '../types';
import { formatRupiah } from '../utils/format';

interface EditDebtItemsDialogProps {
  debt: DebtEntity;
  availableProducts: ProductEntity[];
  onSave: (
    newAmount: number,
    items: { id: number; name: string; price: number; quantity: number }[]
  ) => void;
  onDismiss: () => void;
}

export const EditDebtItemsDialog: React.FC<EditDebtItemsDialogProps> = ({
  debt,
  availableProducts,
  onSave,
  onDismiss
}) => {
  const [items, setItems] = useState([
    { id: 1, name: 'Beras Pandan Wangi 5kg', price: 78000, quantity: 1 },
    { id: 2, name: 'Minyak Goreng Pouch 2L', price: 35000, quantity: 1 }
  ]);
  const [showAddMenu, setShowAddMenu] = useState(false);

  const newTotal = items.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const handleAddItem = (prod: ProductEntity) => {
    const existing = items.find((i) => i.id === prod.id);
    if (existing) {
      setItems(items.map((i) => (i.id === prod.id ? { ...i, quantity: i.quantity + 1 } : i)));
    } else {
      setItems([...items, { id: prod.id, name: prod.name, price: prod.sellingPrice, quantity: 1 }]);
    }
    setShowAddMenu(false);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-sm p-5 shadow-dialog border border-slate-100 flex flex-col max-h-[90vh] animate-scaleUp">
        {/* Header */}
        <div className="flex items-center justify-between pb-3 border-b border-slate-100 flex-shrink-0">
          <div>
            <h3 className="text-base font-bold text-slate-800">Edit Bon Kasbon</h3>
            <p className="text-xs text-slate-400">{debt.customerName}</p>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Action + Add Item */}
        <div className="flex items-center justify-between pt-3 pb-1">
          <span className="text-xs font-semibold text-slate-700">Daftar Barang</span>
          <button
            onClick={() => setShowAddMenu(!showAddMenu)}
            className="h-9 px-3 rounded-input bg-brand-primary/10 hover:bg-brand-primary/20 text-brand-primary text-xs font-semibold flex items-center gap-1 transition-all"
          >
            <Plus className="w-3.5 h-3.5" />
            <span>Tambah Item</span>
          </button>
        </div>

        {/* Dropdown to pick product */}
        {showAddMenu && (
          <div className="bg-slate-50 rounded-input p-2 border border-slate-200 space-y-1 max-h-36 overflow-y-auto mb-2">
            {availableProducts.map((p) => (
              <button
                key={p.id}
                onClick={() => handleAddItem(p)}
                className="w-full text-left p-2 text-xs rounded hover:bg-white flex justify-between items-center transition-all"
              >
                <span className="font-medium text-slate-700 truncate max-w-[180px]">{p.name}</span>
                <span className="font-bold text-brand-primary">{formatRupiah(p.sellingPrice)}</span>
              </button>
            ))}
          </div>
        )}

        {/* Items List */}
        <div className="flex-1 overflow-y-auto py-2 space-y-2">
          {items.map((item) => (
            <div
              key={item.id}
              className="bg-slate-50 rounded-card p-3 border border-slate-200 flex items-center justify-between"
            >
              <div>
                <h4 className="text-xs font-semibold text-slate-800">{item.name}</h4>
                <div className="text-[11px] text-slate-400">
                  {formatRupiah(item.price)} × {item.quantity} ={' '}
                  <span className="font-bold text-brand-primary">
                    {formatRupiah(item.price * item.quantity)}
                  </span>
                </div>
              </div>

              <div className="flex items-center gap-1">
                <button
                  onClick={() => {
                    if (item.quantity <= 1) {
                      setItems(items.filter((i) => i.id !== item.id));
                    } else {
                      setItems(items.map((i) => (i.id === item.id ? { ...i, quantity: i.quantity - 1 } : i)));
                    }
                  }}
                  className="w-8 h-8 rounded-full bg-white border border-slate-200 flex items-center justify-center text-slate-700 active:scale-95"
                >
                  {item.quantity <= 1 ? <Trash2 className="w-3.5 h-3.5 text-danger" /> : <Minus className="w-3.5 h-3.5" />}
                </button>

                <span className="w-6 text-center text-xs font-bold text-slate-800">{item.quantity}</span>

                <button
                  onClick={() => {
                    setItems(items.map((i) => (i.id === item.id ? { ...i, quantity: i.quantity + 1 } : i)));
                  }}
                  className="w-8 h-8 rounded-full bg-brand-primary text-white flex items-center justify-center active:scale-95"
                >
                  <Plus className="w-3.5 h-3.5" />
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Price Difference Summary */}
        <div className="p-3 bg-slate-50 border-t border-slate-200 rounded-input space-y-1 text-xs">
          <div className="flex justify-between text-slate-400">
            <span>Total Bon Semula:</span>
            <span>{formatRupiah(debt.amount)}</span>
          </div>
          <div className="flex justify-between font-bold text-slate-900 pt-1 border-t border-slate-200">
            <span>Total Bon Baru:</span>
            <span className="text-brand-primary text-sm">{formatRupiah(newTotal)}</span>
          </div>
        </div>

        {/* Save button */}
        <div className="pt-3 flex gap-2">
          <button
            onClick={onDismiss}
            className="flex-1 h-11 rounded-input border border-slate-200 text-slate-600 text-xs font-semibold hover:bg-slate-50"
          >
            Batal
          </button>
          <button
            onClick={() => {
              onSave(newTotal, items);
              onDismiss();
            }}
            className="flex-1 h-11 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-xs font-bold flex items-center justify-center gap-1.5 shadow-xs"
          >
            <Check className="w-4 h-4" />
            <span>Simpan Perubahan</span>
          </button>
        </div>
      </div>
    </div>
  );
};
