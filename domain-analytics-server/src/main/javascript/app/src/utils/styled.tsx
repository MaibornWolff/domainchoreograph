import React, { useContext } from 'react';
import _styled, { ThemeContext, ThemedStyledInterface } from 'styled-components';
import { Theme } from '~styles/theme';

export const styled: ThemedStyledInterface<Theme> = _styled;

export function useTheme(): Theme {
  const theme = useContext(ThemeContext);
  return theme as Theme;
}
