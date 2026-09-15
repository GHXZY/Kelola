import React from 'react';

export const KelolaLogoBadge: React.FC<{ size?: number; className?: string }> = ({ size = 38, className = '' }) => {
  return (
    <div
      style={{ width: size, height: size }}
      className={`rounded-full flex items-center justify-center bg-white shadow-sm border border-slate-200/60 overflow-hidden flex-shrink-0 ${className}`}
    >
      <img
        src="/Logo Kelola 1.svg"
        alt="Logo Kelola"
        className="w-full h-full object-cover"
        onError={(e) => {
          // Fallback if SVG not served directly
          (e.currentTarget as HTMLElement).style.display = 'none';
        }}
      />
    </div>
  );
};
