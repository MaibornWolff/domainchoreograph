import { connect } from 'react-redux';
import { App, AppProps } from '~components/app/app';
import { ActionCreators, GlobalState } from '~ducks';

export const AppContainer = connect(
  (state: GlobalState): Partial<AppProps> => ({ loadedGraph: state.graph != null }),
  (dispatch): Partial<AppProps> => ({
    resetState: () => dispatch(ActionCreators.loadGraph({ graph: null })),
    fetchGraph: () => dispatch(ActionCreators.fetchGraph({}))
  }),
)(App);
