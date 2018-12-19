import React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { NoGraphMessage } from './no-graph-message';

describe('NoGraphMessage', () => {
  it('should render', () => {
    expect(renderWithProviders(<NoGraphMessage/>)).toMatchSnapshot();
  });

  it('should render with message', () => {
    expect(renderWithProviders(<NoGraphMessage>Test</NoGraphMessage>)).toMatchSnapshot();
  });
});
