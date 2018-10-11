import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { GraphSelection } from './graph-selection';

describe('GraphSelection', () => {
  it('should render', () => {
    expect(renderWithProviders(<GraphSelection executionContext={'context'}
                                               scopes={['1', '2', '3']}/>)).toMatchSnapshot();
  });
});
