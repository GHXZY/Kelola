import React, { useState, useEffect } from 'react';
import { X, Check } from 'lucide-react';
import { ExpenseEntity } from '../types';

interface AddExpenseDialogProps {
  initialExpense?: ExpenseEntity | null;
  onAddExpense: (category: string, amount: number, note: string) => void;
  onUpdateExpense?: (id: number, category: string, amount: number, note: string) => void;
  onDismiss: () => void;
}

export const AddExpenseDialog: React.FC<AddExpenseDialogProps> = ({
  initialExpense,
  onAddExpense,
  onUpdateExpense,
  onDismiss
}) => {
  const [category, setCategory] = useState(initialExpense?.category || 'Bahan Baku & Dapur');
  const [amount, setAmount] = useState(initialExpense ? String(initialExpense.amount) : '');
  const [note, setNote] = useState(initialExpense?.note || '');

  useEffect(() => {
    if (initialExpense) {
      setCategory(initialExpense.category);
      setAmount(String(initialExpense.amount));
      setNote(initialExpense.note || '');
    }
  }, [initialExpense]);

  const categoryPresets = [
    'Bahan Baku & Dapur',
    'Operasional & Listrik',
    'Gaji Karyawan',
    'Kemasan & Plastik',
    'Lain-lain'
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const numAmount = Number(amount) || 0;
    if (numAmount <= 0) return;

    if (initialExpense && onUpdateExpense) {
      onUpdateExpense(initialExpense.id, category, numAmount, note.trim());
    } else {
      onAddExpense(category, numAmount, note.trim());
    }
    onDismiss();
  };

  const isEditing = Boolean(initialExpense);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-sm p-5 shadow-dialog border border-slate-100 flex flex-col animate-scaleUp">
        {/* Header */}
        <div className="flex items-center justify-between pb-3 border-b border-slate-100 flex-shrink-0">
          <div>
            <h3 className="text-base font-bold text-slate-800">
              {isEditing ? 'Edit Pengeluaran' : 'Catat Pengeluaran'}
            </h3>
            <p className="text-xs text-slate-400">
              {isEditing ? 'Perbarui rincian pengeluaran operasional' : 'Pengeluaran kas operasional toko'}
            </p>
          </div>
          <button onClick={onDismiss} className="text-slate-400 hover:text-slate-700">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="py-3 space-y-3">
          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1.5">Kategori Beban</label>
            <div className="flex flex-wrap gap-1.5">
              {categoryPresets.map((c) => (
                <button
                  type="button"
                  key={c}
                  onClick={() => setCategory(c)}
                  className={`px-2.5 py-1 rounded-chip text-xs font-medium border transition-all ${
                    category === c
                      ? 'bg-danger-container border-red-300 text-danger font-semibold'
                      : 'bg-slate-50 border-slate-200 text-slate-600 hover:bg-slate-100'
                  }`}
                >
                  {c}
                </button>
              ))}
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Nominal Pengeluaran (Rp) *</label>
            <input
              type="number"
              required
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              placeholder="0"
              className="w-full h-11 px-3.5 rounded-input bg-slate-100 text-slate-800 text-base font-bold focus:bg-white focus:ring-1 focus:ring-danger focus:outline-none border border-slate-200"
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-slate-700 block mb-1">Keterangan / Keperluan</label>
            <input
              type="text"
              value={note}
              onChange={(e) => setNote(e.target.value)}
              placeholder="Misal: Beli gas elpiji & kantong kresek"
              className="w-full h-10 px-3 rounded-input bg-slate-100 text-slate-800 text-xs focus:bg-white focus:ring-1 focus:ring-brand-primary focus:outline-none border border-slate-200"
            />
          </div>

          {/* Action Buttons */}
          <div className="pt-2 flex gap-2">
            <button
              type="button"
              onClick={onDismiss}
              className="flex-1 h-11 rounded-input border border-slate-200 text-slate-600 text-xs font-semibold hover:bg-slate-50"
            >
              Batal
            </button>
            <button
              type="submit"
              disabled={!Number(amount)}
              className="flex-1 h-11 rounded-input bg-danger hover:bg-red-600 text-white text-xs font-bold flex items-center justify-center gap-1.5 shadow-xs transition-all disabled:opacity-50"
            >
              <Check className="w-4 h-4" />
              <span>{isEditing ? 'Simpan Perubahan' : 'Simpan Pengeluaran'}</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
