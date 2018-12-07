import { connectRouter } from 'connected-react-router';
import { combineReducers } from 'redux';
import { Actions } from '~constants/actions';
import { history } from '~history';
import { ThemeKey } from '~styles/theme';
import { ChoreoGraph } from '~types/choreo-graph';
import { createReduxContext } from '~utils/redux.utils';

export interface GlobalState {
  app: AppState;
}

export interface AppState {
  graph: ChoreoGraph | null;
  selectedNodeId?: string;
  theme: ThemeKey;
}

const initialState: AppState = {
  graph: null,
  theme: 'light'
};

const context = createReduxContext(initialState);

export namespace ActionCreators {
  interface LoadGraphPayload {
    graph: ChoreoGraph | null;
  }

  export const loadGraph = context.createActionCreator(
    Actions.LOAD_GRAPH,
    (state, payload: LoadGraphPayload) => ({
      ...state,
      graph: payload.graph,
    })
  );

  interface SetThemePayload {
    themeKey: ThemeKey;
  }

  export const setTheme = context.createActionCreator(
    Actions.SET_THEME,
    (state, payload: SetThemePayload) => ({
      ...state,
      theme: payload.themeKey,
    })
  );

  interface ToggleNodePayload {
    nodeId: string;
  }

  export const toggleNode = context.createActionCreator(
    Actions.TOGGLE_NODE,
    (state, payload: ToggleNodePayload) => ({
      ...state,
      selectedNodeId: state.selectedNodeId !== payload.nodeId
        ? payload.nodeId
        : undefined,
    })
  );

  interface LoadStatePayload {
    state: AppState;
  }

  export const loadState = context.createActionCreator(
    Actions.LOAD_STATE,
    (state, payload: LoadStatePayload) => payload.state
  );

  export const fetchGraph = context.createActionCreator(Actions.FETCH_GRAPH);

  interface FetchGraphSuccessPayload {
    graph: ChoreoGraph;
  }

  export const fetchGraphSuccess = context.createActionCreator(
    Actions.FETCH_GRAPH_SUCCESS,
    (state, payload: FetchGraphSuccessPayload) => ({
      ...state,
      graph: payload.graph
    })
  );

  interface FetchGraphFailedPayload {
    error: string;
  }

  export const fetchGraphFailed = context.createActionCreator(
    Actions.FETCH_GRAPH_FAILED,
    (state, payload: FetchGraphFailedPayload) => state
  );
}

export const reducer = combineReducers({
  app: context.createReducer(),
  router: connectRouter(history)
});
