import { ChoreoGraph } from '~types/choreo-graph';

export const graphMock: ChoreoGraph = {
  dependencies: [
    { src: '0', target: '1' },
  ],
  nodes: {
    0: {
      id: '0',
      name: 'node-0',
      scope: 'root',
      javaClass: 'my.java.class0',
      value: { key: 'value-0' },
    },
    1: {
      id: '1',
      name: 'node-1',
      scope: 'root',
      javaClass: 'my.java.class1',
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
};
