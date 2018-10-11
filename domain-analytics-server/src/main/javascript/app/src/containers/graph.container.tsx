import { connect } from 'react-redux';
import { Graph, GraphProps } from '~components/graph/graph';
import { NODE_OPTIONS } from '~constants/node-options';
import { ActionCreators, GlobalState } from '~ducks';

export interface GraphContainerProps {
  className?: string;
  scope: string;
  width?: number;
  height?: number;
}

export const GraphContainer = connect(
  (state: GlobalState, ownProps: GraphContainerProps): Partial<GraphProps> => ({
    graph: state.graph,
    nodeOptions: NODE_OPTIONS,
    selectedNodeIds: state.selectedNodeId ? { [state.selectedNodeId!]: true } : undefined,
    ...ownProps
  }),
  (dispatch): Partial<GraphProps> => ({
    toggleNode: (nodeId) => dispatch(ActionCreators.toggleNode({nodeId}))
  })
)(Graph);
