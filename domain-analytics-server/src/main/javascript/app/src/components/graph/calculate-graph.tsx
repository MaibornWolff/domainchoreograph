import { line } from 'd3-shape';
import * as dagre from 'dagre-layout';
import memoize from 'fast-memoize';
import { EdgeModel } from '~components/graph/edge/edge';
import { NodeModel } from '~components/graph/node/node';
import { NodeOptions } from '~constants/node-options';
import { ChoreoDependency, ChoreoGraph } from '~types/choreo-graph';
import { selectPreviewTextFromNode } from '~utils/node.utils';

export interface VisualGraph {
  nodes: NodeModel[];
  edges: EdgeModel[];
  scale: number;
  height: number | undefined;
  width: number | undefined;
}

export interface CalculateGraphProps {
  graph: ChoreoGraph;
  scopeKey: keyof ChoreoGraph['scopes'];
  nodeOptions: NodeOptions;
  targetWidth?: number;
  targetHeight?: number;
}

export const calculateGraph = memoize(_calculateGraph);

function _calculateGraph({ graph, scopeKey, nodeOptions, targetWidth, targetHeight }: CalculateGraphProps): VisualGraph {
  const scope = graph.scopes[scopeKey];

  const isDependencyRelevant = (dependency: ChoreoDependency) => scope.nodes.includes(dependency.target);
  const edgeData = graph.dependencies.filter(isDependencyRelevant);
  const nodeIds = edgeData
    .map(e => [e.src, e.target])
    .reduce((result, ids) => [...result, ...ids], []);
  const nodeData = nodeIds.map(id => graph.nodes[id]);

  const g = new dagre.graphlib.Graph();

  g.setGraph({});
  g.setDefaultEdgeLabel(() => ({}));

  const isOutsideScope = (nodeId: string) => !scope.nodes.includes(nodeId);
  nodeData.forEach(n => {
      const previewText = selectPreviewTextFromNode(n);
      g.setNode(n.id, {
        ...n,
        outsideScope: isOutsideScope(n.id),
        executionContext: graph.executionContexts[n.id],
        width: calculateNodeTextWidthInPx(n.name, previewText, nodeOptions),
        height: calculateNodeTextHeightInPx(previewText, nodeOptions),
      });
    }
  );
  edgeData.map(e =>
    g.setEdge(e.src, e.target, {
      id: createEdgeId(e),
      outsideScope: isOutsideScope(e.src) || isOutsideScope(e.target)
    })
  );

  dagre.layout(g);

  interface Point {
    x: number;
    y: number;
  }

  let innerHeight = g.graph().height || 0;
  let innerWidth = g.graph().width || 0;
  innerHeight = isFinite(innerHeight) ? innerHeight : 0;
  innerWidth = isFinite(innerWidth) ? innerWidth : 0;
  const scale = calculateScale(
    { width: targetWidth, height: targetHeight },
    { width: innerWidth, height: innerHeight }
  );

  const createEdgeLine = line<Point>()
    .x(p => p.x * scale)
    .y(p => p.y * scale)
  ;
  const nodes: NodeModel[] = g.nodes()
    .map(id => g.node(id));
  const edges: EdgeModel[] = g.edges()
    .map(id => g.edge(id))
    .map(edge => ({
      ...edge,
      line: createEdgeLine(edge.points)
    }));

  return {
    nodes,
    edges,
    height: innerHeight * scale,
    width: innerWidth * scale * 1.1,
    scale,
  };
}

interface WidthHeight {
  width?: number;
  height?: number;
}

export function createEdgeId({src, target}: ChoreoDependency): string {
  return `${src}->${target}`;
}

function calculateScale(target: WidthHeight, origin: WidthHeight): number {
  let result = 1;
  if (target.width && origin.width) {
    result = Math.min(target.width / origin.width, result);
  }
  if (target.height && origin.height) {
    result = Math.min(target.height / origin.height, result);
  }
  return result;
}

function calculateNodeTextWidthInPx(name: string, value: string | undefined, nodeOptions: NodeOptions): number {
  const { width: divWidth } = calculateClientRect(name + '<br>' + value);
  const { width: preWidth } = calculateClientRect(name + '<br>' + value, { elementType: 'pre' });
  return Math.min(Math.max(divWidth, preWidth), nodeOptions.maxWidthInPx) + nodeOptions.sideMarginInPx * 2;
}

function calculateNodeTextHeightInPx(value: string | undefined, nodeOptions: NodeOptions): number {
  if (value == null) {
    return nodeOptions.minHeightInPx;
  }

  const options: CalculateClientRectOptions = {
    fontSizeInRem: .8,
    elementType: 'pre',
    maxWidth: nodeOptions.maxWidthInPx,
  };
  const { height } = calculateClientRect(value, options);
  return nodeOptions.minHeightInPx + height;
}

interface CalculateClientRectOptions {
  fontSizeInRem?: number;
  elementType?: string;
  maxWidth?: number;
}

function calculateClientRect(text: string, {
  fontSizeInRem = 1,
  elementType = 'div',
  maxWidth,
}: CalculateClientRectOptions = {}): ClientRect {
  const element = document.createElement(elementType);
  element.style.display = 'inline-block';
  element.style.fontSize = `${fontSizeInRem}rem`;
  element.style.whiteSpace = 'pre-wrap';
  element.style.padding = '0 8px';
  element.innerHTML = text;
  if (maxWidth) {
    element.style.maxWidth = `${maxWidth}px`;
  }
  document.body.appendChild(element);
  const rect = element.getBoundingClientRect();
  element.remove();
  return rect;
}
