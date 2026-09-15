import React, { useState } from 'react';
import { X, Check, Plus, Minus, FolderPlus } from 'lucide-react';
import { ProductEntity } from '../types';

export const RestockDialog: React.FC<{
  product: ProductEntity;
  onConfirmRestock: (productId: number, addQty: number) => void;
  onDismiss: () => void;
}> = ({ product, onConfirmRestock, onDismiss }) => {
  const [qty, setQty] = useState('10');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const addQty = Number(qty) || 0;
    if (addQty > 0) {
      onConfirmRestock(product.id, addQty);
      onDismiss();
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-xs p-5 shadow-dialog border border-slate-100 flex flex-col animate-scaleUp">
        <div className="flex items-center justify-between pb-2 border-b border-slate-100">
          <div>
            <h3 className="text-sm font-bold text-slate-800">Tambah Stok Masuk</h3>
            <p className="text-[11px] text-slate-400 truncate max-w-[200px]">{product.name}</p>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-4 h-4" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="py-3 space-y-3">
          <div className="bg-slate-50 p-2.5 rounded-input text-xs flex justify-between">
            <span className="text-slate-500">Stok saat ini:</span>
            <span className="font-bold text-slate-800">{product.stock} {product.unit}</span>
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Jumlah Tambahan ({product.unit})</label>
            <input
              type="number"
              min="1"
              required
              value={qty}
              onChange={(e) => setQty(e.target.value)}
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-sm font-bold focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
            />
          </div>

          <div className="pt-2 flex gap-2">
            <button
              type="button"
              onClick={onDismiss}
              className="flex-1 h-10 rounded-input border border-slate-200 text-slate-600 text-xs font-semibold hover:bg-slate-50"
            >
              Batal
            </button>
            <button
              type="submit"
              className="flex-1 h-10 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-xs font-bold flex items-center justify-center gap-1 shadow-xs"
            >
              <Check className="w-3.5 h-3.5" />
              <span>Simpan Stok</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export const ReduceStockDialog: React.FC<{
  product: ProductEntity;
  onConfirmReduce: (productId: number, reduceQty: number, reason: string) => void;
  onDismiss: () => void;
}> = ({ product, onConfirmReduce, onDismiss }) => {
  const [qty, setQty] = useState('1');
  const [reason, setReason] = useState('Rusak / Expired');

  const reasons = ['Rusak / Expired', 'Hilang / Selisih', 'Retur Supplier', 'Lainnya'];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const reduceQty = Number(qty) || 0;
    if (reduceQty > 0) {
      onConfirmReduce(product.id, Math.min(product.stock, reduceQty), reason);
      onDismiss();
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-xs p-5 shadow-dialog border border-slate-100 flex flex-col animate-scaleUp">
        <div className="flex items-center justify-between pb-2 border-b border-slate-100">
          <div>
            <h3 className="text-sm font-bold text-slate-800">Kurangi Stok Barang</h3>
            <p className="text-[11px] text-slate-400 truncate max-w-[200px]">{product.name}</p>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-4 h-4" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="py-3 space-y-3">
          <div className="bg-slate-50 p-2.5 rounded-input text-xs flex justify-between">
            <span className="text-slate-500">Stok saat ini:</span>
            <span className="font-bold text-slate-800">{product.stock} {product.unit}</span>
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Jumlah Pengurangan ({product.unit})</label>
            <input
              type="number"
              min="1"
              max={product.stock}
              required
              value={qty}
              onChange={(e) => setQty(e.target.value)}
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-sm font-bold focus:bg-white focus:ring-1 focus:ring-amber-500 focus:outline-none"
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Alasan Pengurangan</label>
            <select
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:outline-none"
            >
              {reasons.map((r) => (
                <option key={r} value={r}>
                  {r}
                </option>
              ))}
            </select>
          </div>

          <div className="pt-2 flex gap-2">
            <button
              type="button"
              onClick={onDismiss}
              className="flex-1 h-10 rounded-input border border-slate-200 text-slate-600 text-xs font-semibold hover:bg-slate-50"
            >
              Batal
            </button>
            <button
              type="submit"
              className="flex-1 h-10 rounded-input bg-amber-600 hover:bg-amber-700 text-white text-xs font-bold flex items-center justify-center gap-1 shadow-xs"
            >
              <Check className="w-3.5 h-3.5" />
              <span>Kurangi</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export const AddCategoryDialog: React.FC<{
  onAddCategory: (categoryName: string) => void;
  onDismiss: () => void;
}> = ({ onAddCategory, onDismiss }) => {
  const [name, setName] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (name.trim()) {
      onAddCategory(name.trim());
      onDismiss();
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-xs p-5 shadow-dialog border border-slate-100 flex flex-col animate-scaleUp">
        <div className="flex items-center justify-between pb-2 border-b border-slate-100">
          <h3 className="text-sm font-bold text-slate-800">Tambah Kategori Baru</h3>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-4 h-4" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="py-3 space-y-3">
          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Nama Kategori</label>
            <input
              type="text"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Misal: Frozen Food, Bumbu Dapur"
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
            />
          </div>

          <div className="pt-2 flex gap-2">
            <button
              type="button"
              onClick={onDismiss}
              className="flex-1 h-10 rounded-input border border-slate-200 text-slate-600 text-xs font-semibold hover:bg-slate-50"
            >
              Batal
            </button>
            <button
              type="submit"
              className="flex-1 h-10 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-xs font-bold flex items-center justify-center gap-1 shadow-xs"
            >
              <Check className="w-3.5 h-3.5" />
              <span>Simpan</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
