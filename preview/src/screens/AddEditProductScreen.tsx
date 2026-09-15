import React, { useState } from 'react';
import { ArrowLeft, Check, PackagePlus, Calendar, Clock, X, Sparkles, Plus } from 'lucide-react';
import { ProductEntity, CategoryEntity } from '../types';
import { AddCategoryDialog } from '../dialogs/ProductModals';

interface AddEditProductScreenProps {
  product?: ProductEntity | null;
  categories: CategoryEntity[];
  onSave: (productData: Partial<ProductEntity>) => void;
  onNavigateBack: () => void;
  onAddCategory?: (categoryName: string) => number | void;
}

export const AddEditProductScreen: React.FC<AddEditProductScreenProps> = ({
  product,
  categories,
  onSave,
  onNavigateBack,
  onAddCategory
}) => {
  const isEditing = !!product;

  const [name, setName] = useState(product?.name || '');
  const [categoryId, setCategoryId] = useState(product?.categoryId || categories[0]?.id || 1);
  const [showAddCategoryModal, setShowAddCategoryModal] = useState(false);
  const [costPrice, setCostPrice] = useState(product?.costPrice?.toString() || '');
  const [sellingPrice, setSellingPrice] = useState(product?.sellingPrice?.toString() || '');
  const [stock, setStock] = useState(product?.stock?.toString() || '10');
  const [minimumStock, setMinimumStock] = useState(product?.minimumStock?.toString() || '3');
  const [unit, setUnit] = useState(product?.unit || 'pcs');

  // Expiration State
  const initialExpiry = product?.expirationDate ? new Date(Number(product.expirationDate)) : null;
  const [hasExpiry, setHasExpiry] = useState<boolean>(!!initialExpiry);
  const [expiryMode, setExpiryMode] = useState<'DATE_ONLY' | 'DATE_TIME'>('DATE_ONLY');
  const [expiryDate, setExpiryDate] = useState<string>(
    initialExpiry ? initialExpiry.toISOString().slice(0, 10) : ''
  );
  const [expiryTime, setExpiryTime] = useState<string>(
    initialExpiry ? initialExpiry.toTimeString().slice(0, 5) : '23:59'
  );

  const units = ['pcs', 'botol', 'cup', 'porsi', 'bungkus', 'karung', 'pouch'];

  const setPresetDays = (days: number) => {
    const target = new Date(Date.now() + days * 86400000);
    setExpiryDate(target.toISOString().slice(0, 10));
    setHasExpiry(true);
  };

  const calculateExpiryTimestamp = (): number | null => {
    if (!hasExpiry || !expiryDate) return null;
    const timeStr = expiryMode === 'DATE_ONLY' ? '23:59:59' : `${expiryTime || '00:00'}:00`;
    const dt = new Date(`${expiryDate}T${timeStr}`);
    return isNaN(dt.getTime()) ? null : dt.getTime();
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!name.trim()) return;

    onSave({
      ...(product ? { id: product.id } : {}),
      name: name.trim(),
      categoryId: Number(categoryId),
      costPrice: Number(costPrice) || 0,
      sellingPrice: Number(sellingPrice) || 0,
      stock: Number(stock) || 0,
      minimumStock: Number(minimumStock) || 0,
      unit,
      expirationDate: calculateExpiryTimestamp()
    });
  };

  return (
    <div className="flex-1 flex flex-col overflow-hidden bg-[#F7F9FF]">
      {/* Top Bar */}
      <div className="p-4 bg-white border-b border-slate-200/80 flex items-center gap-3 flex-shrink-0">
        <button
          onClick={onNavigateBack}
          className="w-10 h-10 rounded-full bg-slate-100 hover:bg-slate-200 flex items-center justify-center text-slate-700 transition-all"
        >
          <ArrowLeft className="w-5 h-5 text-brand-primary" />
        </button>
        <div>
          <h1 className="text-base font-bold text-slate-800">
            {isEditing ? 'Edit Informasi Produk' : 'Tambah Produk Baru'}
          </h1>
          <p className="text-xs text-slate-500">Lengkapi detail barang dagangan toko</p>
        </div>
      </div>

      {/* Form */}
      <form onSubmit={handleSubmit} className="flex-1 overflow-y-auto p-4 space-y-4 pb-20">
        <div className="bg-white rounded-card p-4 border border-slate-200 shadow-soft space-y-3">
          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Nama Barang *</label>
            <input
              type="text"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Misal: Es Kopi Susu Aren"
              className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Kategori Produk</label>
            <div className="flex items-center gap-2">
              <select
                value={categoryId}
                onChange={(e) => setCategoryId(Number(e.target.value))}
                className="flex-1 h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
              >
                {categories.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.name}
                  </option>
                ))}
              </select>
              <button
                type="button"
                onClick={() => setShowAddCategoryModal(true)}
                className="w-11 h-11 rounded-input bg-blue-50 hover:bg-blue-100 active:scale-95 text-brand-primary flex items-center justify-center transition-all flex-shrink-0 cursor-pointer border border-blue-200 shadow-xs"
                title="Tambah Kategori Baru"
              >
                <Plus className="w-5 h-5" />
              </button>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-slate-700 block mb-1">Harga Modal (Rp)</label>
              <input
                type="number"
                value={costPrice}
                onChange={(e) => setCostPrice(e.target.value)}
                placeholder="0"
                className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-slate-700 block mb-1">Harga Jual (Rp) *</label>
              <input
                type="number"
                required
                value={sellingPrice}
                onChange={(e) => setSellingPrice(e.target.value)}
                placeholder="0"
                className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none font-bold text-brand-primary"
              />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-slate-700 block mb-1">Stok Awal</label>
              <input
                type="number"
                value={stock}
                onChange={(e) => setStock(e.target.value)}
                placeholder="0"
                className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-slate-700 block mb-1">Batas Menipis</label>
              <input
                type="number"
                value={minimumStock}
                onChange={(e) => setMinimumStock(e.target.value)}
                placeholder="3"
                className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
              />
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1.5">Satuan Barang</label>
            <div className="flex flex-wrap gap-1.5">
              {units.map((u) => (
                <button
                  type="button"
                  key={u}
                  onClick={() => setUnit(u)}
                  className={`px-3 py-1.5 rounded-chip text-xs font-semibold border transition-all ${
                    unit === u
                      ? 'bg-brand-primary border-brand-primary text-white'
                      : 'bg-slate-100 border-transparent text-slate-600 hover:bg-slate-200'
                  }`}
                >
                  {u}
                </button>
              ))}
            </div>
          </div>
        </div>

        {/* Masa Simpan & Kadaluarsa Card */}
        <div className="bg-white rounded-card p-4 border border-slate-200 shadow-soft space-y-3">
          <div className="flex items-center justify-between pb-2 border-b border-slate-100">
            <div className="flex items-center gap-2 text-brand-primary font-bold text-xs">
              <Calendar className="w-4 h-4" />
              <span>Masa Simpan & Kadaluarsa</span>
            </div>

            <label className="flex items-center gap-2 cursor-pointer">
              <span className="text-[11px] font-medium text-slate-500">Ada Kadaluarsa</span>
              <input
                type="checkbox"
                checked={hasExpiry}
                onChange={(e) => {
                  setHasExpiry(e.target.checked);
                  if (e.target.checked && !expiryDate) {
                    setPresetDays(30);
                  }
                }}
                className="w-4 h-4 rounded text-brand-primary focus:ring-brand-primary"
              />
            </label>
          </div>

          {hasExpiry && (
            <div className="space-y-3 animate-fadeIn">
              {/* Mode Switch: Tanggal Saja vs Tanggal & Jam Spesifik */}
              <div className="grid grid-cols-2 gap-2">
                <button
                  type="button"
                  onClick={() => setExpiryMode('DATE_ONLY')}
                  className={`py-2 px-3 rounded-input text-xs font-semibold flex items-center justify-center gap-1.5 border transition-all ${
                    expiryMode === 'DATE_ONLY'
                      ? 'bg-brand-primary text-white border-brand-primary shadow-xs'
                      : 'bg-slate-100 text-slate-700 border-transparent hover:bg-slate-200'
                  }`}
                >
                  <Calendar className="w-3.5 h-3.5" />
                  <span>Tanggal Saja</span>
                </button>

                <button
                  type="button"
                  onClick={() => setExpiryMode('DATE_TIME')}
                  className={`py-2 px-3 rounded-input text-xs font-semibold flex items-center justify-center gap-1.5 border transition-all ${
                    expiryMode === 'DATE_TIME'
                      ? 'bg-brand-primary text-white border-brand-primary shadow-xs'
                      : 'bg-slate-100 text-slate-700 border-transparent hover:bg-slate-200'
                  }`}
                >
                  <Clock className="w-3.5 h-3.5" />
                  <span>Tanggal & Jam</span>
                </button>
              </div>

              {/* Date / Time Inputs */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-2.5">
                <div>
                  <label className="text-[11px] font-medium text-slate-600 block mb-1">
                    Tanggal Kadaluarsa
                  </label>
                  <input
                    type="date"
                    required={hasExpiry}
                    value={expiryDate}
                    onChange={(e) => setExpiryDate(e.target.value)}
                    className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
                  />
                </div>

                {expiryMode === 'DATE_TIME' && (
                  <div>
                    <label className="text-[11px] font-medium text-slate-600 block mb-1">
                      Jam Spesifik
                    </label>
                    <input
                      type="time"
                      value={expiryTime}
                      onChange={(e) => setExpiryTime(e.target.value)}
                      className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none"
                    />
                  </div>
                )}
              </div>

              {/* Quick Presets */}
              <div>
                <label className="text-[11px] font-medium text-slate-500 block mb-1.5">
                  Preset Cepat:
                </label>
                <div className="flex flex-wrap gap-1.5">
                  {[
                    { label: '+3 Hari', days: 3 },
                    { label: '+7 Hari', days: 7 },
                    { label: '+14 Hari', days: 14 },
                    { label: '+1 Bulan', days: 30 },
                    { label: '+6 Bulan', days: 180 },
                    { label: '+1 Tahun', days: 365 }
                  ].map((p) => (
                    <button
                      type="button"
                      key={p.label}
                      onClick={() => setPresetDays(p.days)}
                      className="px-2.5 py-1 rounded-chip bg-slate-100 hover:bg-brand-sky/20 text-slate-700 hover:text-brand-primary text-[11px] font-medium border border-slate-200 transition-all"
                    >
                      {p.label}
                    </button>
                  ))}
                </div>
              </div>

              {/* Summary notice */}
              {expiryDate && (
                <div className="p-2.5 bg-blue-50/70 border border-brand-sky/30 rounded-input text-[11px] text-brand-deep flex items-center justify-between">
                  <span>
                    Otomatis tercatat rugi jika kadaluarsa:{' '}
                    <b>
                      {expiryDate} {expiryMode === 'DATE_TIME' ? `pukul ${expiryTime}` : '23:59'}
                    </b>
                  </span>
                  <button
                    type="button"
                    onClick={() => {
                      setHasExpiry(false);
                      setExpiryDate('');
                    }}
                    className="text-red-500 hover:text-red-700 font-semibold ml-2"
                  >
                    Hapus
                  </button>
                </div>
              )}
            </div>
          )}
        </div>

        <button
          type="submit"
          className="w-full h-12 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-sm font-bold flex items-center justify-center gap-2 shadow-md active:scale-[0.99] transition-all"
        >
          <Check className="w-4 h-4" />
          <span>{isEditing ? 'Perbarui Produk' : 'Simpan Produk'}</span>
        </button>
      </form>

      {showAddCategoryModal && (
        <AddCategoryDialog
          onAddCategory={(catName) => {
            if (onAddCategory) {
              const newId = onAddCategory(catName);
              if (typeof newId === 'number') {
                setCategoryId(newId);
              }
            }
            setShowAddCategoryModal(false);
          }}
          onDismiss={() => setShowAddCategoryModal(false)}
        />
      )}
    </div>
  );
};
