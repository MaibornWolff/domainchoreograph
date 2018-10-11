import { shallow } from 'enzyme';
import * as React from 'react';
import { THEME } from '~styles/theme';
import { wait } from '~utils/async.utils';
import { renderWithProviders } from '~utils/test.utils';
import { Section } from './section';

describe('Section', () => {
  it('should render', () => {
    expect(renderWithProviders(<Section header={'Hello'} theme={THEME}>Body</Section>)).toMatchSnapshot();
  });

  it('should open on header click', async () => {
    const section = shallow<Section>(
      <Section header={'Hello'} theme={THEME} isInitiallyOpen={false}>Body</Section>,
    );
    const header = section.children().first();
    expect(section.state().isOpen).toBe(false);
    header.props().onClick();
    await wait(10);
    expect(section.state().isOpen).toBe(true);
  });
});
