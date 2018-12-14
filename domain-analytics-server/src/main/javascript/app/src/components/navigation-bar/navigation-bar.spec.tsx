import { shallow } from 'enzyme';
import * as React from 'react';
import { Button } from '~components/button/button';
import { graphMock } from '~types/cheoreo-graph.mock';
import { shallowWithProviders } from '~utils/test.utils';
import { NavigationBar } from './navigation-bar';

describe('NavigationBar', () => {
  it('should render without graph', () => {
    expect(shallowWithProviders(<NavigationBar graph={null} executionContext={'root'}/>)).toMatchSnapshot();
  });

  it('should render with graph and without scope', () => {
    expect(shallowWithProviders(<NavigationBar graph={graphMock} executionContext={'root'}/>)).toMatchSnapshot();
  });

  it('should render with graph and scope', () => {
    expect(shallowWithProviders(<NavigationBar graph={graphMock} executionContext={'root'} scope={'root'}/>)).toMatchSnapshot();
  });

  it('should switch theme to dark on button click', () => {
    const setTheme = jest.fn();
    const element = shallow(<NavigationBar
      setThemeKey={setTheme}
      currentThemeKey={'light'}
      graph={graphMock}
      executionContext={'root'}
      scope={'root'}
    />);
    const button = element.find(Button);

    button.simulate('click');
    expect(setTheme).toBeCalledWith('dark');
  });

  it('should switch theme to default on button click', () => {
    const setTheme = jest.fn();
    const element = shallow(<NavigationBar
      setThemeKey={setTheme}
      currentThemeKey={'dark'}
      graph={graphMock}
      executionContext={'root'}
      scope={'root'}
    />);
    const button = element.find(Button);

    button.simulate('click');
    expect(setTheme).toBeCalledWith('light');
  });
});
