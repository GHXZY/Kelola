---
name: Blush Blossom
colors:
  surface: '#1a1114'
  surface-dim: '#1a1114'
  surface-bright: '#413639'
  surface-container-lowest: '#140c0f'
  surface-container-low: '#22191c'
  surface-container: '#271d20'
  surface-container-high: '#32272a'
  surface-container-highest: '#3d3235'
  on-surface: '#f0dee2'
  on-surface-variant: '#ddbfc1'
  inverse-surface: '#f0dee2'
  inverse-on-surface: '#382e31'
  outline: '#a58a8c'
  outline-variant: '#574143'
  surface-tint: '#ffb2ba'
  primary: '#ffb2ba'
  on-primary: '#670020'
  primary-container: '#ff788d'
  on-primary-container: '#740b29'
  inverse-primary: '#a7354b'
  secondary: '#e1bebe'
  on-secondary: '#412a2b'
  secondary-container: '#594041'
  on-secondary-container: '#cfadad'
  tertiary: '#fcb3c0'
  on-tertiary: '#51212d'
  tertiary-container: '#d5909d'
  on-tertiary-container: '#5c2935'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#ffd9dc'
  primary-fixed-dim: '#ffb2ba'
  on-primary-fixed: '#400011'
  on-primary-fixed-variant: '#871c35'
  secondary-fixed: '#ffdada'
  secondary-fixed-dim: '#e1bebe'
  on-secondary-fixed: '#2a1617'
  on-secondary-fixed-variant: '#594041'
  tertiary-fixed: '#ffd9df'
  tertiary-fixed-dim: '#fcb3c0'
  on-tertiary-fixed: '#370c18'
  on-tertiary-fixed-variant: '#6c3643'
  background: '#1a1114'
  on-background: '#f0dee2'
  surface-variant: '#3d3235'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 3.5rem
    fontWeight: '700'
    lineHeight: 4.25rem
    letterSpacing: -0.03em
  display-lg-mobile:
    fontFamily: Inter
    fontSize: 2.25rem
    fontWeight: '700'
    lineHeight: 2.75rem
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 2rem
    fontWeight: '600'
    lineHeight: 2.5rem
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 1.5rem
    fontWeight: '600'
    lineHeight: 2rem
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 1.25rem
    fontWeight: '600'
    lineHeight: 1.75rem
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Inter
    fontSize: 1.125rem
    fontWeight: '400'
    lineHeight: 1.75rem
    letterSpacing: -0.005em
  body-md:
    fontFamily: Inter
    fontSize: 1rem
    fontWeight: '400'
    lineHeight: 1.5rem
    letterSpacing: 0em
  body-sm:
    fontFamily: Inter
    fontSize: 0.875rem
    fontWeight: '400'
    lineHeight: 1.25rem
    letterSpacing: 0em
  label-lg:
    fontFamily: Inter
    fontSize: 0.875rem
    fontWeight: '600'
    lineHeight: 1.25rem
    letterSpacing: 0.01em
  label-md:
    fontFamily: Inter
    fontSize: 0.75rem
    fontWeight: '600'
    lineHeight: 1rem
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Inter
    fontSize: 0.6875rem
    fontWeight: '600'
    lineHeight: 0.875rem
    letterSpacing: 0.04em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1.5rem
  margin: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2.5rem
---

## Brand & Style

This design system pairs vibrant floral energy with soft, editorial warmth and modern digital precision. The aesthetic balances high-contrast functional typography with radiant coral-rose accents and delicate pastel mist layers in a sophisticated dark theme environment.

- **Personality**: Radiant, graceful, warm, and sophisticated.
- **Target Audience**: Modern lifestyle, wellness, beauty, creator economy, and boutique commerce platforms seeking an inviting yet high-performance digital presence.
- **Design Style**: Contemporary Soft-Editorial with Warm Minimalism. Clean spatial discipline, elevated tonal surfaces, soft ambient radiance, and high typographic rigor.

## Colors

The palette balances an energetic coral rose against gentle blush tones and grounding deep espresso berry neutrals within a dark mode environment.

- **Primary (`#FF788D`)**: Vibrant coral rose for primary calls-to-action, key active indicators, and high-impact focal points. Ensure strong text legibility by pairing with deep neutral backgrounds or using high-contrast dark text (`#2B2124`) when overlaid on primary fills.
- **Secondary (`#FFDADA`)**: Soft blush cream for secondary containers, badge backgrounds, subtle highlight fills, and active pill indicators.
- **Tertiary (`#5A2834`)**: Deep plum rose utilized for high-contrast interactive states, primary button typography fills, and rich decorative accents.
- **Neutral Canvas (`#2B2124`)**: Deep warm charcoal with a subtle berry undertone for a sophisticated dark surface canvas.
- **Text Primary (`#FFF8F8`)**: Warm soft off-white text tone that eliminates sterile digital glare while maintaining clarity against dark surfaces.
- **Text Secondary (`#DDBFC1`)**: Balanced muted neutral for secondary information, metadata, and supporting labels.

## Typography

Typography relies on `Inter` across all structural layers to deliver crisp, contemporary legibility and systematic precision.

- **Display & Large Headlines**: Set with tight letter spacing and prominent weights to ground the floral tones with architectural stability.
- **Body Hierarchy**: Prioritizes generous line heights (1.5–1.6x) to preserve breathing room and legibility on dark tinted backgrounds and rich surface containers.
- **Labels & Microcopy**: Medium and bold weights with subtle positive tracking enhance readability for buttons, badges, metadata, and tabular navigation.

## Layout & Spacing

The layout is built on an 8pt spatial grid with responsive fluid scaling:

- **Desktop (1024px+)**: 12-column grid with `1.5rem` (24px) gutters and max-width containers up to `1280px`. Outer margin sets to `2rem` (32px).
- **Tablet (768px - 1023px)**: 8-column layout with `1rem` (16px) gutters and `1.5rem` (24px) margins.
- **Mobile (under 768px)**: 4-column fluid layout with `1rem` (16px) gutters and `1rem` (16px) outer canvas margins. Multi-column cards stack linearly.

## Elevation & Depth

Visual hierarchy uses warm ambient diffusion rather than cold grey drop shadows tailored for a dark theme:

- **Base Layer (Level 0)**: Background canvas sits at `#2B2124`.
- **Raised Containers (Level 1)**: Elevated cards and panels use `#382E31` surfaces with an ambient shadow combined with a subtle border `1px solid rgba(255, 120, 141, 0.15)`.
- **Floating Overlays (Level 2)**: Menus, popovers, and sticky bars use deep warm diffusion shadows.
- **Modal Dialogs (Level 3)**: Deep soft scrim using dark backdrops with blur and heavy dialog depth.

## Shapes

The shape system employs soft geometric radii (8px to 12px) to communicate gentleness while avoiding exaggerated circular forms for structural UI.

- **Base Components**: Inputs, buttons, chips, and small alerts use `0.5rem` (8px) border radius.
- **Containers**: Cards, modal sheets, and standard surface panels use `0.75rem` (12px) border radius.
- **Special Elements**: Avatars and standalone status dots utilize full circular pill shapes (`9999px`).

## Components

### Buttons
- **Primary**: Background `#FF788D`, text `#2B2124`, border radius `8px`, vertical padding `10px`, horizontal padding `20px`. Hover state: background `#F2657B`, transform scale `0.99`.
- **Secondary**: Background `#FFDADA`, text `#5A2834`, border `1px solid rgba(255, 120, 141, 0.25)`, border radius `8px`.
- **Tertiary / Ghost**: Transparent fill, text `#FFDADA`, hover background `rgba(255, 218, 218, 0.15)`.

### Cards
- **Base Card**: Fill `#382E31`, border radius `12px`, border `1px solid rgba(255, 120, 141, 0.15)`, padding `24px`.
- **Interactive Card**: Transition on hover with border color shifting to `#FF788D`, and elevation expanding with warm diffused shadow.

### Input Fields
- **Container**: Fill `#382E31`, border radius `8px`, border `1px solid rgba(221, 191, 193, 0.2)`. Padding `12px 16px`.
- **Focus State**: Border color `#FF788D` with an outer glow box-shadow `0 0 0 3px rgba(255, 120, 141, 0.2)`. Text color `#FFF8F8`.

### Badges & Chips
- **Status Badge**: Fill `#FFDADA`, text `#5A2834`, font size `label-sm`, padding `4px 10px`, border radius `6px`.
- **Filter Chip**: Fill `#382E31`, border `1px solid rgba(255, 120, 141, 0.2)`, text `#FFF8F8`. When selected: Fill `#FF788D`, text `#2B2124`, border-color `#FF788D`.

### Checkboxes & Radio Buttons
- **Control Box**: 20x20px dimension, border `1.5px solid #DDBFC1`, background `#382E31`, border radius `4px` (checkbox) or circular (radio).
- **Checked State**: Background `#FF788D`, border-color `#FF788D`, check icon crisp dark.

### Navigation & App Bars
- **Navigation Bar**: Background `rgba(43, 33, 36, 0.85)` with backdrop-filter blur `12px`, bottom border `1px solid rgba(255, 120, 141, 0.15)`. Active links indicated by `#FF788D` text and a matching 2px bottom accent bar.