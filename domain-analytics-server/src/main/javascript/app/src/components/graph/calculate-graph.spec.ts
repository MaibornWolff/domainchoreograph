import { number } from 'prop-types';
import { calculateGraph, CalculateGraphProps } from '~components/graph/calculate-graph';

describe('calculateGraph', () => {
  const baseProps: CalculateGraphProps = {
    graph: {
      dependencies: [
        { src: '0', target: '1' },
      ],
      nodes: {
        0: {
          id: '0',
          name: 'node-0',
          scope: 'root',
          value: { key: 'value-0' },
        },
        1: {
          id: '1',
          name: 'node-1',
          scope: 'root',
          value: { key: 'value-1' },
        }
      },
      scopes: {
        root: {
          id: 'root',
          nodes: ['0', '1'],
          executionContext: 'root',
        }
      },
      executionContexts: {
        root: {
          id: 'root',
          scopes: ['root']
        }
      }
    },
    scopeKey: 'root',
    nodeOptions: {
      maxWidthInPx: 10,
      minHeightInPx: 20,
      sideMarginInPx: 5,
    },
    targetHeight: 100,
    targetWidth: 200,
  };

  it('should calculate the graph', () => {
    const expected = {
      nodes:
        [
          {
            id: '0',
            name: 'node-0',
            scope: 'root',
            value: baseProps.graph.nodes['0'].value,
            outsideScope: false,
            executionContext: undefined,
            width: baseProps.nodeOptions.maxWidthInPx,
            height: baseProps.nodeOptions.minHeightInPx,
            x: expect.any(Number),
            y: expect.any(Number)
          },
          {
            id: '1',
            name: 'node-1',
            scope: 'root',
            value: baseProps.graph.nodes['1'].value,
            outsideScope: false,
            executionContext: undefined,
            width: baseProps.nodeOptions.maxWidthInPx,
            height: baseProps.nodeOptions.minHeightInPx,
            x: expect.any(Number),
            y: expect.any(Number)
          }
        ],
      edges:
        [
          {
            id: '0->1',
            outsideScope: false,
            points: expect.any(Array),
            line: expect.any(String)
          }
        ],
      height: expect.any(Number),
      width: expect.any(Number),
      scale: expect.any(Number)
    };

    const actual = calculateGraph(baseProps);

    expect(actual).toEqual(expected);
  });

  it('should calculate if edge or node is outside the scope', () => {
    const props: CalculateGraphProps = {
      ...baseProps,
      graph: {
        ...baseProps.graph,
        nodes: {
          0: {
            id: '0',
            name: 'node-0',
            scope: 's1',
            value: { key: 'value-0' },
          },
          1: {
            id: '1',
            name: 'node-1',
            scope: 'root',
            value: { key: 'value-1' },
          }
        },
        scopes: {
          root: {
            id: 'root',
            nodes: ['1'],
            executionContext: 'root',
          },
          s1: {
            id: 's1',
            nodes: ['0'],
            executionContext: 'root',
          }
        },
        executionContexts: {
          root: {
            id: 'root',
            scopes: ['root', 's1']
          }
        }
      }
    };
    const expected = {
      nodes:
        [
          {
            id: '0',
            outsideScope: true,
          },
          {
            id: '1',
            outsideScope: false,
          }
        ],
      edges:
        [
          {
            outsideScope: true,
          }
        ],
    };

    const actual = calculateGraph(props);

    expect(actual).toMatchObject(expected);
  });
});
