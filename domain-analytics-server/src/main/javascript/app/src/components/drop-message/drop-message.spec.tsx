import React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { DropMessage } from './drop-message';

describe('DropMessage', () => {
  it('should render', () => {
    expect(renderWithProviders(<DropMessage/>)).toMatchSnapshot();
  });

  it('should render with message', () => {
    expect(renderWithProviders(<DropMessage>Test</DropMessage>)).toMatchSnapshot();
  });
});
