import * as React from 'react';
import { CSSProperties } from 'react';
import { calculateGraph } from '~components/graph/calculate-graph';
import { Edge } from '~components/graph/edge/edge';
import { getSelectedOptionsOrUndefined, SelectedOptions } from '~components/graph/models/selected-options';
import { Node } from '~components/graph/node/node';
import { NodeOptions } from '~constants/node-options';
import { ChoreoGraph } from '~types/choreo-graph';
import { styled } from '~utils/styled';

export const EDGE_ARROW_WIDTH = 7;
export const EDGE_ARROW_HEIGHT = 8;

const NodeWrapper = styled('div')`
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
`;

const EdgeWrapper = NodeWrapper.withComponent('svg');

export interface GraphProps {
  className?: string;
  scope: string;
  graph: ChoreoGraph | null;
  nodeOptions: NodeOptions;
  width?: number;
  height?: number;
  toggleNode?: (nodeId: string) => void;
  selectedNodeIds?: Record<string, boolean | SelectedOptions>;
  selectedEdgeIds?: Record<string, boolean | SelectedOptions>;
}

export interface GraphState {
}

export class Graph extends React.PureComponent<GraphProps, GraphState> {
  public render() {
    const props = this.props;
    const {
      graph: choreoGraph,
      scope: scopeKey,
      nodeOptions,
      toggleNode,
      selectedNodeIds = {},
      selectedEdgeIds = {}
    } = props;
    if (!choreoGraph) {
      return <p>No Data</p>;
    }

    const { width, height, nodes, edges, scale } = calculateGraph({
      graph: choreoGraph,
      nodeOptions,
      scopeKey,
      targetHeight: props.height,
      targetWidth: props.width,
    });

    const widthHeight: CSSProperties = { height, width };

    const style: CSSProperties = {
      ...widthHeight,
      position: 'relative',
      margin: 'auto'
    };

    return (
      <div style={style} className={this.props.className || ''}>
        <NodeWrapper>
          {nodes.map((node, i) => <Node
            toggleNode={toggleNode}
            isSelected={!!selectedNodeIds[node.id]}
            selectedOptions={getSelectedOptionsOrUndefined(selectedNodeIds[node.id])}
            key={i}
            node={node}
            scale={scale}
            options={nodeOptions}/>
          )}
        </NodeWrapper>
        <EdgeWrapper
          style={widthHeight}
        >
          {edges.map((edge, i) => <Edge
            key={i}
            edge={edge}
            selectedOptions={getSelectedOptionsOrUndefined(selectedEdgeIds[edge.id])}
            isSelected={!!selectedEdgeIds[edge.id]}
          />)}
        </EdgeWrapper>
      </div>
    );
  }
}
