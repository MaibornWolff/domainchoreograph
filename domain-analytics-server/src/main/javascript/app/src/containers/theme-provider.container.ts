import { ThemeProvider } from 'emotion-theming';
import { connect } from 'react-redux';
import { GlobalState } from '~ducks';
import { selectTheme } from '~selectors/selectors';

export const ThemeProviderContainer = connect(
  (state: GlobalState) => ({ theme: selectTheme(state) }),
)(ThemeProvider as any);
