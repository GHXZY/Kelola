---
name: Blush Blossom
colors:
  surface: '#fff8f8'
  surface-dim: '#e7d6d9'
  surface-bright: '#fff8f8'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fff0f3'
  surface-container: '#fce9ed'
  surface-container-high: '#f6e4e8'
  surface-container-highest: '#f0dee2'
  on-surface: '#22191c'
  on-surface-variant: '#574143'
  inverse-surface: '#382e31'
  inverse-on-surface: '#ffecf0'
  outline: '#8a7173'
  outline-variant: '#ddbfc1'
  surface-tint: '#a7354b'
  primary: '#a7354b'
  on-primary: '#ffffff'
  primary-container: '#ff788d'
  on-primary-container: '#740b29'
  inverse-primary: '#ffb2ba'
  secondary: '#735858'
  on-secondary: '#ffffff'
  secondary-container: '#ffdada'
  on-secondary-container: '#795d5e'
  tertiary: '#874d5a'
  on-tertiary: '#ffffff'
  tertiary-container: '#d5909d'
  on-tertiary-container: '#5c2935'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
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
  background: '#fff8f8'
  on-background: '#22191c'
  surface-variant: '#f0dee2'
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

This design system pairs vibrant floral energy with soft, editorial warmth and modern digital precision. The aesthetic balances high-contrast functional typography with radiant coral-rose accents and delicate pastel mist layers.

- **Personality**: Radiant, graceful, warm, and sophisticated.
- **Target Audience**: Modern lifestyle, wellness, beauty, creator economy, and boutique commerce platforms seeking an inviting yet high-performance digital presence.
- **Design Style**: Contemporary Soft-Editorial with Warm Minimalism. Clean spatial discipline, elevated tonal surfaces, soft ambient radiance, and high typographic rigor.

## Colors

The palette balances an energetic coral rose against gentle blush tones and grounding deep espresso berry neutrals.

- **Primary (`#FF788D`)**: Vibrant coral rose for primary calls-to-action, key active indicators, and high-impact focal points. Ensure strong text legibility by pairing with deep neutral backgrounds or using high-contrast dark text (`#2B2124`) when overlaid on primary fills.
- **Secondary (`#FFDADA`)**: Soft blush cream for secondary containers, badge backgrounds, subtle highlight fills, and active pill indicators.
- **Tertiary (`#5A2834`)**: Deep plum rose utilized for high-contrast interactive states, primary button typography fills, and rich decorative accents.
- **Neutral Canvas (`#FFF9FA`)**: Warm soft off-white surface tone that eliminates sterile digital glare while maintaining clarity.
- **Neutral Surface Elevated (`#FFFFFF`)**: Pure crisp white for elevated cards and modal containers.
- **Text Primary (`#2B2124`)**: Deep warm charcoal with a subtle berry undertone, ensuring WCAG AAA legibility against white and blush surfaces.
- **Text Secondary (`#6E5A60`)**: Balanced muted neutral for secondary information, metadata, and supporting labels.

## Typography

Typography relies on `Inter` across all structural layers to deliver crisp, contemporary legibility and systematic precision.

- **Display & Large Headlines**: Set with tight letter spacing and prominent weights to ground the floral tones with architectural stability.
- **Body Hierarchy**: Prioritizes generous line heights (1.5–1.6x) to preserve breathing room and legibility on both warm tinted backgrounds and crisp white containers.
- **Labels & Microcopy**: Medium and bold weights with subtle positive tracking enhance readability for buttons, badges, metadata, and tabular navigation.

## Layout & Spacing

The layout is built on an 8pt spatial grid with responsive fluid scaling:

- **Desktop (1024px+)**: 12-column grid with `1.5rem` (24px) gutters and max-width containers up to `1280px`. Outer margin sets to `2rem` (32px).
- **Tablet (768px - 1023px)**: 8-column layout with `1rem` (16px) gutters and `1.5rem` (24px) margins.
- **Mobile (under 768px)**: 4-column fluid layout with `1rem` (16px) gutters and `1rem` (16px) outer canvas margins. Multi-column cards stack linearly.

## Elevation & Depth

Visual hierarchy uses warm ambient diffusion rather than cold grey drop shadows:

- **Base Layer (Level 0)**: Background canvas sits at `#FFF9FA`.
- **Raised Containers (Level 1)**: Elevated cards and panels use `#FFFFFF` surfaces with an ambient shadow: `0 4px 16px -2px rgba(90, 40, 52, 0.05), 0 1px 4px 0 rgba(90, 40, 52, 0.03)` combined with a subtle border `1px solid rgba(255, 120, 141, 0.12)`.
- **Floating Overlays (Level 2)**: Menus, popovers, and sticky bars use `0 10px 28px -4px rgba(90, 40, 52, 0.08), 0 2px 8px 0 rgba(90, 40, 52, 0.04)`.
- **Modal Dialogs (Level 3)**: Deep soft scrim using `rgba(43, 33, 36, 0.35)` backdrop blur (8px) and dialog depth `0 20px 40px -8px rgba(90, 40, 52, 0.16)`.

## Shapes

The shape system employs soft geometric radii (8px to 12px) to communicate gentleness while avoiding exaggerated circular forms for structural UI.

- **Base Components**: Inputs, buttons, chips, and small alerts use `0.5rem` (8px) border radius.
- **Containers**: Cards, modal sheets, and standard surface panels use `0.75rem` (12px) border radius.
- **Special Elements**: Avatars and standalone status dots utilize full circular pill shapes (`9999px`).

## Components

### Buttons
- **Primary**: Background `#FF788D`, text `#2B2124` (or white when paired with darker high-contrast variant states), border radius `8px`, vertical padding `10px`, horizontal padding `20px`. Hover state: background `#F2657B`, transform scale `0.99`.
- **Secondary**: Background `#FFDADA`, text `#5A2834`, border `1px solid rgba(255, 120, 141, 0.25)`, border radius `8px`.
- **Tertiary / Ghost**: Transparent fill, text `#5A2834`, hover background `rgba(255, 218, 218, 0.35)`.

### Cards
- **Base Card**: Fill `#FFFFFF`, border radius `12px`, border `1px solid rgba(255, 120, 141, 0.12)`, padding `24px`.
- **Interactive Card**: Transition on hover with border color shifting to `#FF788D`, and elevation expanding with warm diffused shadow.

### Input Fields
- **Container**: Fill `#FFFFFF`, border radius `8px`, border `1px solid rgba(110, 90, 96, 0.2)`. Padding `12px 16px`.
- **Focus State**: Border color `#FF788D` with an outer glow box-shadow `0 0 0 3px rgba(255, 120, 141, 0.2)`. Text color `#2B2124`.

### Badges & Chips
- **Status Badge**: Fill `#FFDADA`, text `#5A2834`, font size `label-sm`, padding `4px 10px`, border radius `6px`.
- **Filter Chip**: Fill `#FFFFFF`, border `1px solid rgba(255, 120, 141, 0.2)`, text `#2B2124`. When selected: Fill `#FF788D`, text `#2B2124`, border-color `#FF788D`.

### Checkboxes & Radio Buttons
- **Control Box**: 20x20px dimension, border `1.5px solid #6E5A60`, background `#FFFFFF`, border radius `4px` (checkbox) or circular (radio).
- **Checked State**: Background `#FF788D`, border-color `#FF788D`, check icon crisp white.

### Navigation & App Bars
- **Navigation Bar**: Background `rgba(255, 249, 250, 0.85)` with backdrop-filter blur `12px`, bottom border `1px solid rgba(255, 120, 141, 0.12)`. Active links indicated by `#FF788D` text and a matching 2px bottom accent bar.