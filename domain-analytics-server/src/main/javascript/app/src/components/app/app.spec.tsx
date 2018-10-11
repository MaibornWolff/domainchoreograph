import { shallow } from 'enzyme';
import * as React from 'react';
import { Redirect } from 'react-router';
import { App } from '~components/app/app';
import { renderWithProviders } from '~utils/test.utils';

describe('App', () => {
  it('should render if graph is loaded', () => {
    expect(renderWithProviders(<App loadedGraph={true} resetState={() => {
    }} fetchGraph={() => {
    }}/>)).toMatchSnapshot();
  });

  it('should render if graph is not loaded', () => {
    expect(renderWithProviders(<App loadedGraph={true} resetState={() => {
    }} fetchGraph={() => {
    }}/>)).toMatchSnapshot();
  });

  it('should call fetch graph on mount', () => {
    const fetchGraph = jest.fn();

    expect(shallow(<App
      loadedGraph={true}
      resetState={() => {
      }}
      fetchGraph={fetchGraph}/>)
    ).toMatchSnapshot();

    expect(fetchGraph).toHaveBeenCalled();
  });

  it('should catch errors', () => {
    const resetState = jest.fn();
    const app = shallow(<App loadedGraph={false} resetState={resetState} fetchGraph={() => {}}/>);

    const errorFallback = app.instance().componentDidCatch!(new Error('test'), {} as any);

    expect(resetState).toHaveBeenCalled();
    expect(errorFallback).toEqual(<Redirect to="/"/>);
  });
});
