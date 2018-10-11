import { Reducer } from 'redux';

export interface BaseAction<T extends string = string> {
  type: T;
}

export type Action<T extends string, P> = BaseAction<T> & { payload: P };

export interface ActionCreator<S, T extends string, P> {
  (payload: P): Action<T, P>;

  type: T;
  reducer: ActionCreatorReducer<S, P>;
}

export type ActionCreatorReducer<S, P> = (state: S, payload: P) => S;

function createActionCreator<S, T extends string, P>(
  type: T,
  reducer: ActionCreatorReducer<S, P> = (state) => state
): ActionCreator<S, T, P> {
  const createAction: ActionCreator<S, T, P> = (
    (payload: P): Action<T, P> => ({ type, payload })
  ) as ActionCreator<S, T, P>;
  createAction.type = type;
  createAction.reducer = reducer;

  return createAction;
}

function createReducer<S>(defaultState: S, actionCreators: Array<ActionCreator<S, string, any>>): Reducer<S> {
  return (state = defaultState, action) => {
    return actionCreators
      .filter(actionCreator => actionCreator.type === action.type)
      .reduce((previousState, actionCreator) => ({
        ...(previousState as any),
        ...(actionCreator.reducer(previousState, action.payload) as any),
      }), state);
  };
}

export interface ReduxContext<S> {
  createActionCreator<T extends string, P>(type: T, reducer?: ActionCreatorReducer<S, P>): ActionCreator<S, T, P>;

  createReducer(): Reducer<S>;
}

export function createReduxContext<S>(defaultState: S): ReduxContext<S> {
  const actionCreators: Array<ActionCreator<S, string, any>> = [];
  return {
    createActionCreator: (type, reducer) => {
      const actionCreator = createActionCreator(type, reducer);
      actionCreators.push(actionCreator);
      return actionCreator;
    },
    createReducer: () => createReducer(defaultState, actionCreators),
  };
}
