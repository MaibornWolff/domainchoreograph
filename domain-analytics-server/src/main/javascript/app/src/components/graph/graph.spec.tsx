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
    const graph: ChoreoGraph = {
      nodes: {
        'A': {
          id: 'A',
          name: 'A',
          scope: 'root',
          value: {},
        },
        'F': {
          id: 'F',
          name: 'F',
          scope: 'root',
          value: {},
        },
        '0-B': {
          id: '0-B',
          name: '0-B',
          scope: '0',
          value: {},
        },
        '0-C': {
          id: '0-C',
          name: '0-C',
          scope: '0',
          value: {},
        },
        '0-D': {
          id: '0-D',
          name: '0-D',
          scope: '0',
          value: {},
        },
        '0-E': {
          id: '0-E',
          name: '0-E',
          scope: '0',
          value: {},
        },
        '1-B': {
          id: '1-B',
          name: '1-B',
          scope: '1',
          value: {},
        },
        '1-C': {
          id: '1-C',
          name: '1-C',
          scope: '1',
          value: {},
        },
        '1-D': {
          id: '1-D',
          name: '1-D',
          scope: '1',
          value: {},
        },
        '1-E': {
          id: '1-E',
          name: '1-E',
          scope: '1',
          value: {},
        }
      },
      dependencies: [
        {
          src: 'A',
          target: 'F'
        },
        {
          src: '0-B',
          target: '0-C'
        },
        {
          src: '0-B',
          target: '0-D'
        },
        {
          src: '0-C',
          target: '0-E'
        },
        {
          src: '0-D',
          target: '0-E'
        },
        {
          src: '1-B',
          target: '1-C'
        },
        {
          src: '1-B',
          target: '1-D'
        },
        {
          src: '1-C',
          target: '1-E'
        },
        {
          src: '1-D',
          target: '1-E'
        },
        {
          src: 'A',
          target: '0-D'
        },
        {
          src: 'A',
          target: '1-D'
        }
      ],
      scopes: {
        root: {
          id: 'root',
          executionContext: 'Application',
          nodes: [
            'A',
            'F'
          ]
        },
        0: {
          id: '0',
          executionContext: 'F',
          nodes: [
            '0-B',
            '0-C',
            '0-D',
            '0-E'
          ]
        },
        1: {
          id: '1',
          executionContext: 'F',
          nodes: [
            '1-B',
            '1-C',
            '1-D',
            '1-E'
          ]
        }
      },
      executionContexts: {
        Application: {
          id: 'Application',
          scopes: [
            'root'
          ]
        },
        F: {
          id: 'F',
          scopes: [
            '0',
            '1'
          ]
        }
      }
    };

    expect(renderWithProviders(<Graph graph={graph} scope={'root'} nodeOptions={NODE_OPTIONS}/>)).toMatchSnapshot();
  });

  it('should render with just one node', () => {
    const graph: ChoreoGraph = {
      nodes: {
        A: {
          id: 'A',
          name: 'A',
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
      scope={'s0'}
      nodeOptions={NODE_OPTIONS}
      selectedEdgeIds={{ '0->1': true }}
    />)).toMatchSnapshot();
  });

  it('should select edges with color', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'s0'}
      nodeOptions={NODE_OPTIONS}
      selectedEdgeIds={{ '0->1': { color: 'red' } }}
    />)).toMatchSnapshot();
  });

  it('should select nodes', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'s0'}
      nodeOptions={NODE_OPTIONS}
      selectedNodeIds={{ 0: true }}
    />)).toMatchSnapshot();
  });

  it('should select nodes with color', () => {
    expect(renderWithProviders(<Graph
      graph={graphMock}
      scope={'s0'}
      nodeOptions={NODE_OPTIONS}
      selectedNodeIds={{ 0: { color: 'red' } }}
    />)).toMatchSnapshot();
  });
});
