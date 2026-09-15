import React from 'react';
import { Search, LucideIcon, AlertTriangle } from 'lucide-react';

export const EditorialCategoryTag: React.FC<{ text: string; className?: string }> = ({ text, className = '' }) => {
  return (
    <span
      className={`inline-block px-2.5 py-0.5 text-[11px] font-semibold tracking-wider uppercase rounded-md bg-[#8ACFF8]/20 text-[#002B47] ${className}`}
    >
      {text}
    </span>
  );
};

export const StockBadge: React.FC<{ stock: number; minimumStock: number; unit: string }> = ({
  stock,
  minimumStock,
  unit
}) => {
  if (stock <= 0) {
    return (
      <span className="inline-flex items-center px-2 py-0.5 rounded text-[11px] font-semibold bg-danger-container text-danger-text">
        Habis
      </span>
    );
  }
  if (stock <= minimumStock) {
    return (
      <span className="inline-flex items-center px-2 py-0.5 rounded text-[11px] font-semibold bg-warning-container text-warning-text">
        Sisa {stock} {unit}
      </span>
    );
  }
  return (
    <span className="inline-flex items-center px-2 py-0.5 rounded text-[11px] font-medium bg-slate-100 text-slate-600">
      Stok: {stock} {unit}
    </span>
  );
};

export const SummaryCard: React.FC<{
  title: string;
  value: string;
  subtitle: string;
  icon: LucideIcon;
  iconColorClass?: string;
  iconBgClass?: string;
  onClick?: () => void;
  className?: string;
}> = ({
  title,
  value,
  subtitle,
  icon: Icon,
  iconColorClass = 'text-brand-primary',
  iconBgClass = 'bg-brand-sky/20',
  onClick,
  className = ''
}) => {
  return (
    <div
      onClick={onClick}
      className={`bg-white rounded-card p-3.5 border border-slate-200 shadow-soft flex flex-col justify-between transition-all ${
        onClick ? 'cursor-pointer hover:border-brand-sky active:scale-[0.99]' : ''
      } ${className}`}
    >
      <div className="flex items-center justify-between mb-2">
        <span className="text-[12px] font-medium text-slate-500 leading-tight">{title}</span>
        <div className={`w-9 h-9 rounded-full flex items-center justify-center flex-shrink-0 ${iconBgClass}`}>
          <Icon className={`w-4 h-4 ${iconColorClass}`} />
        </div>
      </div>
      <div>
        <div className="text-[17px] font-bold text-slate-900 leading-tight tracking-tight">{value}</div>
        <div className="text-[11px] text-slate-500 mt-0.5 leading-none">{subtitle}</div>
      </div>
    </div>
  );
};

export const SearchField: React.FC<{
  query: string;
  onQueryChange: (val: string) => void;
  placeholder?: string;
  className?: string;
}> = ({ query, onQueryChange, placeholder = 'Cari...', className = '' }) => {
  return (
    <div className={`relative flex items-center ${className}`}>
      <Search className="absolute left-3.5 w-4 h-4 text-slate-400 pointer-events-none" />
      <input
        type="text"
        value={query}
        onChange={(e) => onQueryChange(e.target.value)}
        placeholder={placeholder}
        className="w-full h-11 pl-10 pr-4 rounded-input bg-slate-100 border border-transparent text-sm text-slate-800 placeholder-slate-400 focus:bg-white focus:border-brand-primary focus:outline-none transition-all"
      />
    </div>
  );
};

export const CategoryChipGroup: React.FC<{
  categories: string[];
  selectedCategory: string;
  onSelectCategory: (category: string) => void;
}> = ({ categories, selectedCategory, onSelectCategory }) => {
  return (
    <div className="flex items-center gap-2 overflow-x-auto no-scrollbar py-1">
      {categories.map((cat) => {
        const isSelected = selectedCategory === cat;
        return (
          <button
            key={cat}
            onClick={() => onSelectCategory(cat)}
            className={`h-9 px-3.5 rounded-chip text-[13px] font-medium whitespace-nowrap transition-all border ${
              isSelected
                ? 'bg-brand-primary border-brand-primary text-white font-semibold shadow-sm'
                : 'bg-white border-slate-200 text-slate-600 hover:bg-slate-50'
            }`}
          >
            {cat}
          </button>
        );
      })}
    </div>
  );
};

export const EmptyState: React.FC<{
  icon: LucideIcon;
  title: string;
  description: string;
  className?: string;
}> = ({ icon: Icon, title, description, className = '' }) => {
  return (
    <div className={`flex flex-col items-center justify-center p-8 text-center ${className}`}>
      <div className="w-16 h-16 rounded-full bg-slate-100 flex items-center justify-center mb-3">
        <Icon className="w-8 h-8 text-slate-400" />
      </div>
      <h3 className="text-[15px] font-semibold text-slate-800 mb-1">{title}</h3>
      <p className="text-[13px] text-slate-500 max-w-xs">{description}</p>
    </div>
  );
};

export const ConfirmationDialog: React.FC<{
  title: string;
  message: string;
  confirmText?: string;
  isDestructive?: boolean;
  onConfirm: () => void;
  onDismiss: () => void;
}> = ({
  title,
  message,
  confirmText = 'Konfirmasi',
  isDestructive = false,
  onConfirm,
  onDismiss
}) => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-card w-full max-w-xs p-5 shadow-dialog border border-slate-100">
        <div className="w-11 h-11 rounded-full bg-danger-container/60 flex items-center justify-center mb-3">
          <AlertTriangle className={`w-5 h-5 ${isDestructive ? 'text-danger' : 'text-warning'}`} />
        </div>
        <h4 className="text-[16px] font-bold text-slate-900 mb-1.5">{title}</h4>
        <p className="text-[13px] text-slate-600 mb-5 leading-relaxed">{message}</p>
        <div className="flex gap-2 justify-end">
          <button
            onClick={onDismiss}
            className="px-4 py-2 rounded-input text-[13px] font-medium text-slate-600 hover:bg-slate-100"
          >
            Batal
          </button>
          <button
            onClick={onConfirm}
            className={`px-4 py-2 rounded-input text-[13px] font-semibold text-white ${
              isDestructive ? 'bg-danger hover:bg-red-600' : 'bg-brand-primary hover:bg-brand-deep'
            }`}
          >
            {confirmText}
          </button>
        </div>
      </div>
    </div>
  );
};
