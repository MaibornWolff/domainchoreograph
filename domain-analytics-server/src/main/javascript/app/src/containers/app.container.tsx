import * as React from 'react';
import { useCallback } from 'react';
import { App } from '~components/app/app';
import { ActionCreators } from '~ducks';
import { useRedux } from '~utils/redux.utils';

export const AppContainer: React.FunctionComponent<{}> = () => {
  const [state, dispatch] = useRedux();

  return <App
    loadedGraph={state.app.graph != null}
    resetState={useCallback(
      () => dispatch(ActionCreators.loadGraph({ graph: null })),
      [dispatch]
    )}
    fetchGraph={useCallback(
      () => dispatch(ActionCreators.fetchGraph({})),
      [dispatch]
    )}
  />
};
