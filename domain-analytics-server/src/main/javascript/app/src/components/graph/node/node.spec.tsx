import * as React from 'react';
import { NODE_OPTIONS } from '~constants/node-options';
import { renderWithProviders } from '~utils/test.utils';
import { Node, NodeModel } from './node';

describe('Node', () => {
  it('should render', () => {
    const node: NodeModel = {
      id: 'test',
      name: 'test',
      scope: 'test',
      x: 100,
      y: 200,
      height: 10,
      width: 20,
      value: { hello: 'world' },
      outsideScope: false
    };
    expect(renderWithProviders(<Node node={node} scale={.5} options={NODE_OPTIONS} isSelected={false}/>)).toMatchSnapshot();
  });

  it('should render if selected', () => {
    const node: NodeModel = {
      id: 'test',
      name: 'test',
      scope: 'test',
      x: 100,
      y: 200,
      height: 10,
      width: 20,
      value: { hello: 'world' },
      outsideScope: false
    };
    expect(renderWithProviders(<Node node={node} scale={.5} options={NODE_OPTIONS} isSelected={true}/>)).toMatchSnapshot();
  });

  it('should render with preview', () => {
    const node: NodeModel = {
      id: 'test',
      name: 'test',
      scope: 'test',
      preview: 'value',
      x: 100,
      y: 200,
      height: 10,
      width: 20,
      value: { hello: 'world' },
      outsideScope: false
    };
    expect(renderWithProviders(<Node node={node} scale={.5} options={NODE_OPTIONS} isSelected={false}/>)).toMatchSnapshot();
  });
});
