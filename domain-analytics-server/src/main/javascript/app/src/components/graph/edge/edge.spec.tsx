import * as React from 'react';
import { renderWithProviders } from '~utils/test.utils';
import { Edge, EdgeModel } from './edge';

describe('Edge', () => {
  it('should render inside scope', () => {
    const edge: EdgeModel = {
      id: 'a->b',
      line: 'M80,100L80,104.16666666666667C80,108.33333333333333,80,116.66666666666667,80,125C80,133.33333333333334,80,141.66666666666666,80,145.83333333333334L80,150',
      outsideScope: false,
      points: [{ x: 0, y: 0 }, { x: 1, y: 1 }]
    };
    expect(renderWithProviders(<Edge edge={edge}/>)).toMatchSnapshot();
  });

  it('should render outside scope', () => {
    const edge: EdgeModel = {
      id: 'a->b',
      line: 'M80,100L80,104.16666666666667C80,108.33333333333333,80,116.66666666666667,80,125C80,133.33333333333334,80,141.66666666666666,80,145.83333333333334L80,150',
      outsideScope: true,
      points: [{ x: 0, y: 0 }, { x: 1, y: 1 }]
    };
    expect(renderWithProviders(<Edge edge={edge}/>)).toMatchSnapshot();
  });

  it('should render if selected', () => {
    const edge: EdgeModel = {
      id: 'a->b',
      line: 'M80,100L80,104.16666666666667C80,108.33333333333333,80,116.66666666666667,80,125C80,133.33333333333334,80,141.66666666666666,80,145.83333333333334L80,150',
      outsideScope: false,
      points: [{ x: 0, y: 0 }, { x: 1, y: 1 }]
    };
    expect(renderWithProviders(<Edge edge={edge} isSelected={true}/>)).toMatchSnapshot();
  });
});
