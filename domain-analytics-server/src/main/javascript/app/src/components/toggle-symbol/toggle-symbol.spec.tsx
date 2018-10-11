import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { ToggleSymbol } from './toggle-symbol';

describe('ToggleSymbol', () => {
  it('should render if open', () => {
    expect(renderWithProviders(<ToggleSymbol isOpen={true}/>)).toMatchSnapshot();
  });

  it('should render if closed', () => {
    expect(renderWithProviders(<ToggleSymbol isOpen={false}/>)).toMatchSnapshot();
  });
});
