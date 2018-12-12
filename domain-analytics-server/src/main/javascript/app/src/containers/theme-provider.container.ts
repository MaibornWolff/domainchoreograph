import { connect } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { GlobalState } from '~ducks';
import { selectTheme } from '~selectors/selectors';

export const ThemeProviderContainer = connect(
  (state: GlobalState) => ({ theme: selectTheme(state) }),
)(ThemeProvider);
