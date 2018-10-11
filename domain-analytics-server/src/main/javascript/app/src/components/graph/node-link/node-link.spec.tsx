import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { NodeLink } from './node-link';

describe('NodeLink', () => {
  it('should render', () => {
    expect(renderWithProviders(<NodeLink executionContextId={'id'}/>)).toMatchSnapshot();
  });
});
