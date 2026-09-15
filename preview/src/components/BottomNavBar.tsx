import React from 'react';
import { Home, ShoppingBag, BarChart3, Package } from 'lucide-react';

export type TabScreen = 'home' | 'cashier' | 'products' | 'reports';

interface BottomNavBarProps {
  activeTab: TabScreen;
  onSelectTab: (tab: TabScreen) => void;
  cartCount?: number;
}

export const BottomNavBar: React.FC<BottomNavBarProps> = ({
  activeTab,
  onSelectTab,
  cartCount = 0
}) => {
  const items: { id: TabScreen; label: string; icon: React.ReactNode; testTag: string }[] = [
    {
      id: 'home',
      label: 'Beranda',
      icon: <Home className="w-5 h-5" />,
      testTag: 'nav_home'
    },
    {
      id: 'cashier',
      label: 'Kasir',
      icon: (
        <div className="relative">
          <ShoppingBag className="w-5 h-5" />
          {cartCount > 0 && (
            <span className="absolute -top-1.5 -right-2.5 bg-danger text-white text-[10px] font-bold rounded-full w-4 h-4 flex items-center justify-center ring-2 ring-white">
              {cartCount > 99 ? '99+' : cartCount}
            </span>
          )}
        </div>
      ),
      testTag: 'nav_cashier'
    },
    {
      id: 'products',
      label: 'Produk',
      icon: <Package className="w-5 h-5" />,
      testTag: 'nav_products'
    },
    {
      id: 'reports',
      label: 'Laporan',
      icon: <BarChart3 className="w-5 h-5" />,
      testTag: 'nav_reports'
    }
  ];

  return (
    <nav className="h-16 bg-white border-t border-slate-200/80 px-2 flex items-center justify-around flex-shrink-0 z-20 shadow-sm">
      {items.map((item) => {
        const isActive = activeTab === item.id;
        return (
          <button
            key={item.id}
            onClick={() => onSelectTab(item.id)}
            data-testid={item.testTag}
            className="flex-1 flex flex-col items-center justify-center py-1 transition-all group"
          >
            <div
              className={`px-4 py-1 rounded-full flex items-center justify-center transition-all ${
                isActive ? 'bg-[#8ACFF8]/25 text-brand-primary' : 'text-slate-500 group-hover:text-slate-700'
              }`}
            >
              {item.icon}
            </div>
            <span
              className={`text-[11px] mt-0.5 transition-colors ${
                isActive ? 'font-bold text-brand-primary' : 'font-medium text-slate-500'
              }`}
            >
              {item.label}
            </span>
          </button>
        );
      })}
    </nav>
  );
};
