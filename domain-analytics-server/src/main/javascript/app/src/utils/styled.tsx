import { ThemeContext } from '@emotion/core';
import React, { useContext } from 'react';
import _styled, { CreateStyled } from '@emotion/styled';
import { Theme } from '~styles/theme';

console.log(_styled);
export const styled: CreateStyled<Theme> = _styled;

export function useTheme(): Theme {
  const theme = useContext(ThemeContext);
  return theme as Theme;
}
