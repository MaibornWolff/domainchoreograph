import { createMuiTheme, MuiThemeProvider } from '@material-ui/core';
import React, { useMemo } from 'react';
import { selectTheme } from '~selectors/selectors';
import { mainColorIndex } from '~styles/theme';
import { useReduxSelector } from '~utils/redux.utils';

interface MyMuiThemeProps {
}

export const MyMuiTheme: React.FunctionComponent<MyMuiThemeProps> = ({ children }) => {
  const theme = useReduxSelector(selectTheme);
  const muiTheme = useMemo(() => {
    return createMuiTheme({
      palette: {
        type: theme.type,
        primary: {
          main: theme.colors.primary[mainColorIndex],
          contrastText: theme.colors.text.light.primary,
        },
        secondary: {
          light: theme.colors.secondary[mainColorIndex - 4],
          main: theme.colors.secondary[mainColorIndex],
          dark: theme.colors.secondary[mainColorIndex + 2],
          contrastText: theme.colors.text.light.primary
        },
        error: {
          light: theme.colors.danger,
          main: theme.colors.danger,
          dark: theme.colors.danger,
          contrastText: theme.colors.danger
        },
      }
    });
  }, [theme]);

  return <MuiThemeProvider theme={muiTheme}>{children}</MuiThemeProvider>
};
