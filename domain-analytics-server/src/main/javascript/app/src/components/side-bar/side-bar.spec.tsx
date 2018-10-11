import React from 'react';
import { THEME } from '~styles/theme';
import { mockEmotion, renderWithProviders } from '~utils/test.utils';
import { SideBar } from './side-bar';

jest.mock('emotion', () => mockEmotion());

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
