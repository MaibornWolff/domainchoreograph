import * as React from 'react';
import { NODE_OPTIONS } from '~constants/node-options';
import { graphMock } from '~types/cheoreo-graph.mock';
import { ChoreoGraph } from '~types/choreo-graph';
import { renderWithProviders } from '~utils/test.utils';
import { Graph } from './graph';

describe('Graph', () => {
  it('should render without data', () => {
    expect(renderWithProviders(<Graph graph={null} scope={'root'} nodeOptions={NODE_OPTIONS}/>)).toMatchSnapshot();
  });

  it('should render with data', () => {
    expect(renderWithProviders(<Graph graph={graphMock} scope={'root'} nodeOptions={NODE_OPTIONS}/>)).toMatchSnapshot();
  });

  it('should render with just one node', () => {
    const graph: ChoreoGraph = {
      nodes: {
        A: {
          id: 'A',
          name: 'A',
          javaClass: 'A',
          scope: 'root',
          value: {}
        }
      },
      dependencies: [],
      scopes: {
        root: {
          id: 'root',
          executionContext: 'Application',
          nodes: [
            'A',
            'F'
          ]
        },
      },
      executionContexts: {
        Application: {
          id: 'Application',
          scopes: [
            'root'
          ]
        },
      }
    };

    expect(renderWithProviders(<Graph graph={graph} scope={'root'} nodeOptions={NODE_OPTIONS}/>)).toMatchSnapshot();
  });

  it('should select edges', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'root'}
      nodeOptions={NODE_OPTIONS}
      selectedEdgeIds={{ '0->1': true }}
    />)).toMatchSnapshot();
  });

  it('should select edges with color', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'root'}
      nodeOptions={NODE_OPTIONS}
      selectedEdgeIds={{ '0->1': { color: 'red' } }}
    />)).toMatchSnapshot();
  });

  it('should select nodes', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'root'}
      nodeOptions={NODE_OPTIONS}
      selectedNodeIds={{ 0: true }}
    />)).toMatchSnapshot();
  });

  it('should select nodes with color', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'root'}
      nodeOptions={NODE_OPTIONS}
      selectedNodeIds={{ 0: { color: 'red' } }}
    />)).toMatchSnapshot();
  });
});
