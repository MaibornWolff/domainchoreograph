import React from 'react';
import { THEME } from '~styles/theme';
import { renderWithProviders } from '~utils/test.utils';
import { SideBar } from './side-bar';

describe('SideBar', () => {
  it('should render', () => {
    expect(renderWithProviders(<SideBar theme={THEME} node={{
      id: 'id',
      name: 'name',
      scope: 'scope',
      value: { test: '123' },
      preview: 'preview'
    }}/>)).toMatchSnapshot();
  });
});
