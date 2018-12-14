import React, { useCallback, useMemo } from 'react';
import { Graph } from '~components/graph/graph';
import { ChoreoGraph } from '~types/choreo-graph';

interface GraphInputNodeSelectionProps {
  graph: ChoreoGraph;
  selectedJavaClasses: string[];
  onAdd: (javaClass: string) => void;
  onRemove: (javaClass: string) => void;
}

export const GraphInputNodeSelection: React.FunctionComponent<GraphInputNodeSelectionProps> = ({
  graph,
  selectedJavaClasses,
  onAdd,
  onRemove
}) => {
  const nodeIdToJavaClass = useMemo(() => {
    const map = new Map<string, string>();
    Object.entries(graph.nodes).forEach(([id, node]) => {
      map.set(id, node.javaClass);
    });
    return map;
  }, [graph]);

  const javaClassToNodeId = useMemo(() => reverseMap(nodeIdToJavaClass), [nodeIdToJavaClass])

  const handleToggleNode = useCallback((nodeId: string) => {
    const toggledJavaClass = nodeIdToJavaClass.get(nodeId)!;
      selectedJavaClasses.includes(toggledJavaClass)
        ? onRemove(toggledJavaClass)
        : onAdd(toggledJavaClass);
  }, [nodeIdToJavaClass, onAdd, onRemove, selectedJavaClasses]);

  const selectedNodeIds = useMemo(() => {
    const selectedNodes: Record<string, boolean> = {};
    selectedJavaClasses
      .map(javaClass => javaClassToNodeId.get(javaClass)!)
      .forEach(nodeId => selectedNodes[nodeId] = true);
    return selectedNodes;
  }, [selectedJavaClasses, javaClassToNodeId]);

  return (
    <Graph
      scope="root"
      graph={graph}
      disableDeepNavigation={true}
      toggleNode={handleToggleNode}
      selectedNodeIds={selectedNodeIds}
    />
  );
};

function reverseMap<A, B>(map: Map<A, B>): Map<B, A> {
  const newMap = new Map<B, A>();
  Array.from(map.entries()).forEach(([a, b]) => newMap.set(b, a));
  return newMap;
}
