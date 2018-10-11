import { withTheme } from 'emotion-theming';
import { connect } from 'react-redux';
import { SideBar, SideBarProps } from '~components/side-bar/side-bar';
import { GlobalState } from '~ducks';
import { selectSelectedNode } from '~selectors/selectors';

export const SideBarContainer = connect(
  (state: GlobalState): Partial<SideBarProps> => ({ node: selectSelectedNode(state) })
)(withTheme(SideBar));
