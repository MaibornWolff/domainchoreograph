import * as React from 'react';
import { Graph } from '~components/graph/graph';
import { NODE_OPTIONS } from '~constants/node-options';
import { ActionCreators } from '~ducks';
import { useRedux } from '~utils/redux.utils';

export interface GraphContainerProps {
  className?: string;
  scope: string;
  width?: number;
  height?: number;
}

export const GraphContainer = (props: GraphContainerProps) => {
  const [state, dispatch] = useRedux();

  return <Graph
    graph={state.app.graph}
    nodeOptions={NODE_OPTIONS}
    selectedNodeIds={state.app.selectedNodeId ? { [state.app.selectedNodeId!]: true } : undefined}
    toggleNode={(nodeId) => dispatch(ActionCreators.toggleNode({ nodeId }))}
    {...props}
  />
};
