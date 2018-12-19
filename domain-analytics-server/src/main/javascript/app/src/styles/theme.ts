import darken from 'polished/lib/color/darken';
import hsl from 'polished/lib/color/hsl';
import lighten from 'polished/lib/color/lighten';
import rgba from 'polished/lib/color/rgba';

const textLight = {
  primary: 'rgba(255, 255, 255, 1)',
  secondary: 'rgba(255, 255, 255, .7)',
  disabled: 'rgba(255, 255, 255, .5)'
};

const textDark = {
  primary: 'rgba(0, 0, 0, .87)',
  secondary: 'rgba(0, 0, 0, .54)',
  disabled: 'rgba(0, 0, 0, .38)',
};

function createColorPalette(hue: number, saturation: number, light: number) {
  const difference = .035;
  return [
    hsl(hue, saturation, light + difference * 4),
    hsl(hue, saturation, light + difference * 3),
    hsl(hue, saturation, light + difference * 2),
    hsl(hue, saturation, light + difference),
    hsl(hue, saturation, light),
    hsl(hue, saturation, light - difference),
    hsl(hue, saturation, light - difference * 2),
    hsl(hue, saturation, light - difference * 3),
    hsl(hue, saturation, light - difference * 4),
  ];
}

export const mainColorIndex = 4;

const primary = createColorPalette(225, .4, .5);

const secondary =  createColorPalette(140, .5, .45);

const animations = {
  duration: {
    toggle: 300
  },
  all: `all 0.3s cubic-bezier(.25,.8,.25,1)`
};

const shadows = {
  0: '0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)',
  1: '0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)',
};

export const THEME = {
  type: 'light' as 'light' | 'dark',
  shadows,
  colors: {
    primary,
    secondary,
    background: {
      0: '#E0E0E0',
      1: '#F5F5F5',
      2: '#FAFAFA',
      3: '#FFFFFF',
    },
    danger: '#b73a2d',
    text: {
      light: textLight,
      dark: textDark,
      normal: textDark,
      inverse: textLight
    },
    icons: {
      light: textLight,
      dark: textDark,
      normal: textDark,
      inverse: textLight
    },
    divider: {
      normal: rgba(0, 0, 0, .12),
      inverse: rgba(255, 255, 255, .12),
    },
    border: {
      normal: rgba(0, 0, 0, .3),
    },
    graph: {
      edge: lighten(.6, 'black'),
      selectedEdge: lighten(.15, primary[mainColorIndex]),
    },
    selectedNode: primary[mainColorIndex],
  },
  jsonViewTheme: 'rjv-default',
  animations,
  dimensions: {
    borderRadius: {
      normal: '3px',
      big: '5px',
    },
    border: {
      normal: '1px',
      thick: '2px'
    }
  }
};

export type Theme = typeof THEME;

const DARK_THEME: Theme = {
  type: 'dark',
  shadows,
  colors: {
    primary,
    secondary,
    background: {
      0: '#191919',
      1: '#343434',
      2: '#424242',
      3: '#303030',
    },
    text: {
      light: textLight,
      dark: textDark,
      normal: textLight,
      inverse: textDark,
    },
    icons: {
      light: textLight,
      dark: textDark,
      normal: textLight,
      inverse: textDark,
    },
    danger: '#d65246',
    divider: {
      normal: rgba(255, 255, 255, .12),
      inverse: rgba(0, 0, 0, .12),
    },
    border: {
      normal: rgba(255, 255, 255, .3),
    },
    graph: {
      edge: darken(.5, 'white'),
      selectedEdge: primary[mainColorIndex],
    },
    selectedNode: primary[mainColorIndex],
  },
  animations,
  jsonViewTheme: 'bright',
  dimensions: THEME.dimensions
};

export const THEMES = {
  light: THEME,
  dark: DARK_THEME
};
export type ThemeKey = keyof typeof THEMES;
