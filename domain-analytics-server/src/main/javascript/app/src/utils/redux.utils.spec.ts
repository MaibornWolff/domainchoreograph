import { createReduxContext } from '~utils/redux.utils';

describe('createReduxContext', () => {
  it('should create action creators and the reducer', () => {
    const defaultState = {
      a: 1,
      b: 2
    };
    const context = createReduxContext(defaultState);

    const incrementA = context.createActionCreator(
      'INCREMENT_A',
      (state, payload: { by: number }) => ({
        ...state,
        a: state.a + payload.by
      })
    );

    const setB = context.createActionCreator(
      'SET_B',
      (state, payload: { to: number }) => ({
        ...state,
        b: payload.to
      })
    );

    const reducer = context.createReducer();

    expect(reducer(undefined, incrementA({ by: 10 }))).toEqual({
      a: 11,
      b: 2
    });
    expect(reducer({ a: 10, b: 40 }, setB({ to: 0 }))).toEqual({
      a: 10,
      b: 0
    });
  });
});
