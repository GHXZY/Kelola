/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./preview/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        brand: {
          primary: '#006199',
          deep: '#004974',
          sky: '#8ACFF8',
          navy: '#002B47',
          container: '#CCE5FF',
          onContainer: '#001D32',
        },
        surface: {
          canvas: '#F7F9FF',
          card: '#FFFFFF',
          variant: '#EDF2F7',
          border: '#E2E8F0',
        },
        success: {
          DEFAULT: '#10B981',
          container: '#DCFCE7',
          text: '#15803D',
        },
        danger: {
          DEFAULT: '#EF4444',
          container: '#FEE2E2',
          text: '#B91C1C',
        },
        warning: {
          DEFAULT: '#F59E0B',
          container: '#FEF3C7',
          text: '#B45309',
        },
        teal: {
          DEFAULT: '#0D9488',
          container: '#CCFBF1',
          text: '#0F766E',
        }
      },
      borderRadius: {
        'card': '16px',
        'input': '8px',
        'sheet': '16px',
        'chip': '8px',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'sans-serif'],
      },
      boxShadow: {
        'soft': '0 2px 8px -2px rgba(0, 43, 71, 0.05), 0 1px 4px -1px rgba(0, 43, 71, 0.03)',
        'card': '0 4px 12px -2px rgba(0, 43, 71, 0.06), 0 2px 6px -1px rgba(0, 43, 71, 0.04)',
        'sheet': '0 -8px 24px -4px rgba(0, 43, 71, 0.10)',
        'dialog': '0 20px 32px -8px rgba(0, 43, 71, 0.20)',
      }
    },
  },
  plugins: [],
}
