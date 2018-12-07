import { GlobalState } from '~ducks';
import { Theme, THEMES } from '~styles/theme';
import { ChoreoNode } from '~types/choreo-graph';

export const selectSelectedNode = ({ app: state }: GlobalState): ChoreoNode | null => (
  state.graph != null && state.selectedNodeId ? state.graph.nodes[state.selectedNodeId] : null
);

export const selectTheme = ({ app: state }: GlobalState): Theme => THEMES[state.theme];
