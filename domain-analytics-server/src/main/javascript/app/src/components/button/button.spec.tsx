import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { Button } from './button';

describe('Button', () => {
  it('should render', () => {
    expect(renderWithProviders(<Button/>)).toMatchSnapshot();
  });

  it('should render with light font', () => {
    expect(renderWithProviders(<Button lightColor={true}/>)).toMatchSnapshot();
  });
});
