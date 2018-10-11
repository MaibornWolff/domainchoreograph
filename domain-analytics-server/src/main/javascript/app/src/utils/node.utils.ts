import { ChoreoNode } from '~types/choreo-graph';

/**
 * Select the preview text from a node
 */
export function selectPreviewTextFromNode(node: ChoreoNode): string | undefined {
  const exceptionMessage = node.exception && node.exception.detailMessage;
  return node.preview || exceptionMessage;
}
