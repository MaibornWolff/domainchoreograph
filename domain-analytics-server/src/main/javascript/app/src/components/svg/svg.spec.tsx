import { shallow } from 'enzyme';
import * as React from 'react';
import { Svg } from './svg';

describe('Svg', () => {
  it('should render', () => {
    expect(shallow(<Svg inlineSvg={'<svg><text>This is a test</text></svg>'}/>)).toMatchSnapshot();
  });

  it('should render with class name', () => {
    expect(shallow(<Svg
      className="my-class"
      inlineSvg={'<svg><text>This is a test</text></svg>'}
    />)).toMatchSnapshot();
  });
});
