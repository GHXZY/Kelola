---
name: Oceanic Modernity
colors:
  surface: '#f7f9ff'
  surface-dim: '#d5dae4'
  surface-bright: '#f7f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eef4fe'
  surface-container: '#e9eef8'
  surface-container-high: '#e3e8f2'
  surface-container-highest: '#dde3ec'
  on-surface: '#161c23'
  on-surface-variant: '#414750'
  inverse-surface: '#2b3138'
  inverse-on-surface: '#ecf1fb'
  outline: '#717881'
  outline-variant: '#c0c7d1'
  surface-tint: '#05639b'
  primary: '#004974'
  on-primary: '#ffffff'
  primary-container: '#006199'
  on-primary-container: '#b7d9ff'
  inverse-primary: '#97cbff'
  secondary: '#0b658a'
  on-secondary: '#ffffff'
  secondary-container: '#8fd4fe'
  on-secondary-container: '#005d7f'
  tertiary: '#264865'
  on-tertiary: '#ffffff'
  tertiary-container: '#3f607e'
  on-tertiary-container: '#b8dafe'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#cee5ff'
  primary-fixed-dim: '#97cbff'
  on-primary-fixed: '#001d33'
  on-primary-fixed-variant: '#004a77'
  secondary-fixed: '#c4e7ff'
  secondary-fixed-dim: '#8acff8'
  on-secondary-fixed: '#001e2d'
  on-secondary-fixed-variant: '#004c69'
  tertiary-fixed: '#cee5ff'
  tertiary-fixed-dim: '#a8caed'
  on-tertiary-fixed: '#001d32'
  on-tertiary-fixed-variant: '#274967'
  background: '#f7f9ff'
  on-background: '#161c23'
  surface-variant: '#dde3ec'
typography:
  display:
    fontFamily: Playfair Display
    fontSize: 56px
    fontWeight: '600'
    lineHeight: 64px
    letterSpacing: -0.02em
  display-mobile:
    fontFamily: Playfair Display
    fontSize: 36px
    fontWeight: '600'
    lineHeight: 44px
    letterSpacing: -0.01em
  headline-lg:
    fontFamily: Playfair Display
    fontSize: 40px
    fontWeight: '600'
    lineHeight: 48px
    letterSpacing: -0.015em
  headline-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Playfair Display
    fontSize: 28px
    fontWeight: '500'
    lineHeight: 36px
  headline-sm:
    fontFamily: Playfair Display
    fontSize: 22px
    fontWeight: '500'
    lineHeight: 30px
  title-lg:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.01em
  title-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 15px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Inter
    fontSize: 13px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.04em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
---

## Brand & Style

The design system projects authority, intellect, and clarity. It balances editorial prestige with utilitarian digital efficiency, built for modern digital platforms, high-end SaaS, wealth management, and research-driven publications. 

The aesthetic is Modern Editorial: an intersection of classical editorial prestige and crisp, contemporary digital ergonomics. High-contrast typography pairs expressive, stately serif headlines with precise, geometric sans-serif interfaces. Layouts prioritize generous whitespace, deliberate structural alignment, and subtle atmospheric layering over heavy ornament. The emotional response should be calm, deeply trustworthy, and effortless.

## Colors

The palette draws tension between dense marine depths and high-altitude atmosphere. 

- **Primary (`#006199`)**: Deep ocean blue. Used for primary calls-to-action, structural navigation headers, active states, and foundational accents.
- **Secondary (`#8ACFF8`)**: Vibrant ice blue. Acts as an illumination tone—used for focus halos, highlight chips, active indicators, and secondary contextual fills against deep backgrounds.
- **Tertiary (`#002B47`)**: Abyssal navy. Provides high-contrast anchor tones, dark-mode surfaces, footers, and prominent editorial subheadings.
- **Neutral (`#1E242B`)**: Rich slate black. Used for high-legibility body typography, accompanied by calibrated tints for borders (`#E2E8F0`), muted backgrounds (`#F8FAFC`), and subtle card elevations (`#FFFFFF`).

Ensure all text combinations against backgrounds pass WCAG 2.1 AA requirements. Secondary `#8ACFF8` is strictly paired with dark text (`#002B47` or `#1E242B`) when used as an interactive surface.

## Typography

Typography establishes an intentional contrast between intellectual warmth and operational efficiency.

- **Playfair Display** commands titles, editorial callouts, and section hero elements. Keep letter spacing slightly tightened on larger display variants to maintain optical density.
- **Inter** handles all functional UI requirements: dense dashboards, long-form content, form inputs, metadata, and buttons. 
- Apply `label-sm` in uppercase with expanded tracking for eyebrows, metadata tags, and categorizations. Maintain standard tracking and moderate weights for all form labels and interactive controls.

## Layout & Spacing

The layout is built around a responsive 12-column grid system designed to scale gracefully from mobile to ultrawide interfaces.

- **Desktop (1024px and above)**: 12 columns, 1.5rem gutters, and 3rem exterior safe margin with a max-width container cap of 1360px.
- **Tablet (640px to 1023px)**: 8 columns, 1.25rem gutters, and 2rem exterior margin.
- **Mobile (below 640px)**: 4 columns, 1rem gutters, and 1.25rem exterior margins.

Rhythm is maintained using a standard 4px/8px modular base scale. Spacing tokens (`space-*`) dictate gap relationships inside cards, input containers, and structural content sections. For dense layout compositions (e.g., analytical data cards), use `space-sm` and `space-md`. For long-form narrative editorial blocks, expand vertical separation using `space-xl` and above.

## Elevation & Depth

Visual hierarchy is communicated through a blend of soft atmospheric shadows and low-contrast borders. The surface structure avoids harsh drop shadows in favor of ambient ocean-tinted depth.

- **Flat / Ground Level**: Base background canvas (`#F8FAFC`).
- **Surface Tier 1 (Cards, panels, sheets)**: Pure white (`#FFFFFF`) with a 1px border of `#E2E8F0` and a diffuse ambient shadow: `0 1px 3px rgba(0, 43, 71, 0.04), 0 6px 16px rgba(0, 43, 71, 0.03)`.
- **Surface Tier 2 (Hover states, interactive overlays)**: Border color transitions subtly toward `#8ACFF8`, with an elevated shadow: `0 8px 24px rgba(0, 97, 153, 0.08)`.
- **Surface Tier 3 (Modals, popovers, dropdowns)**: Clean background with an expanded reach: `0 16px 40px rgba(0, 43, 71, 0.12), 0 2px 6px rgba(0, 43, 71, 0.04)`.

## Shapes

The design system employs a soft, architectural shape language (Level 2: Rounded). Generous radiuses reflect contemporary polish and approachability without eroding the intellectual rigor of the typography.

- **Inputs, Buttons, Chips, and Form Elements**: 0.5rem (8px) border-radius (`rounded`).
- **Cards, Modals, and Flyout Panels**: 1rem (16px) border-radius (`rounded-lg`).
- **Inner Nested Elements**: 0.5rem to match container geometry cleanly.
- **Exceptions**: System avatars, status badges, and icon indicators may utilize fully pill-shaped styling (9999px) where circular iconography reinforces intent.

## Components

### Buttons
- **Primary**: Background `#006199`, text `#FFFFFF`, font `Inter` Medium (`label-md`). Hover state transitions to a deepened tone (`#004E7C`). Focus renders a 3px ring of `#8ACFF8` at 60% opacity.
- **Secondary**: Surface `#FFFFFF`, border 1px solid `#006199`, text `#006199`. Hover state fills with a light tint of the secondary sky blue (`#F0F8FE`).
- **Tertiary / Ghost**: Transparent fill, text `#006199`, hover background `#F1F5F9`.

### Chips & Badges
- **Editorial Category Tags**: Background `rgba(138, 207, 248, 0.2)`, text `#002B47`, uppercase `label-sm`.
- **Interactive Filter Chips**: 1px border `#E2E8F0`, surface `#FFFFFF`. When selected, border is `#006199`, surface is `#006199`, and text is `#FFFFFF`.

### Cards
- Standard cards feature a clean `#FFFFFF` ground, 1px `#E2E8F0` border, and `rounded-lg` radius. 
- Interactive cards elevate smoothly on hover with a 2px vertical lift and border highlight using `#8ACFF8`. Card titles use `title-lg` or `headline-sm` depending on editorial priority.

### Input Fields & Controls
- **Text Inputs**: Height 42px, padding `0.5rem 0.75rem`, border 1px solid `#CBD5E1`, text `body-md`. Focus applies a crisp border `#006199` and an exterior `0 0 0 3px rgba(138, 207, 248, 0.45)` aura.
- **Checkboxes & Radios**: 18px size. Selected states fill `#006199` with a `#FFFFFF` checkmark or center pip. Radii follow 4px for checkboxes and circle for radios.

### Lists
- Multi-row lists use 1px border-bottom dividers (`#F1F5F9`). Active list rows are marked with an 8px ice blue (`#8ACFF8`) leading indicator on hover/selection.