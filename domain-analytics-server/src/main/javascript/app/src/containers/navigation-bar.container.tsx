import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from 'react-router';
import { NavigationBar, NavigationBarProps } from '~components/navigation-bar/navigation-bar';
import { ActionCreators, GlobalState } from '~ducks';
import setTheme = ActionCreators.setTheme;

type ConnectedNavigationBarProps = Omit<NavigationBarProps, 'currentThemeKey' | 'setThemeKey' | 'graph'>;

const ConnectedNavigationBar = connect(
  (state: GlobalState, ownProps: ConnectedNavigationBarProps): Partial<NavigationBarProps> => ({
    graph: state.graph,
    currentThemeKey: state.theme,
    ...ownProps
  }),
  (dispatch): Partial<NavigationBarProps> => ({
    setThemeKey: (themeKey) => dispatch(setTheme({themeKey}))
  })
)(NavigationBar as any);

const renderNavigationBarWithRoutes: React.StatelessComponent<RouteComponentProps<any>> = (props) =>
  <ConnectedNavigationBar executionContext={props.match.params.executionContext} scope={props.match.params.scope}/>;

export const NavigationBarContainer = withRouter(renderNavigationBarWithRoutes);
