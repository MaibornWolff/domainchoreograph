import { shallow } from 'enzyme';
import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { Markdown } from './markdown';

describe('Markdown', () => {
  it('should render', () => {
    const markdown = `
# Hello World

This is a test

This is a **bold** test

This is a *italic* test

Should support inline latex $1 + 2 = 10$

$$
x = x_2
$$
    `;
    expect(renderWithProviders(<Markdown markdown={markdown}/>)).toMatchSnapshot();
  });
});
