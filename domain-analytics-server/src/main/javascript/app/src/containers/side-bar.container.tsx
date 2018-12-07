import * as React from 'react';
import { SideBar } from '~components/side-bar/side-bar';
import { selectSelectedNode } from '~selectors/selectors';
import { useReduxSelector } from '~utils/redux.utils';
import { useTheme } from '~utils/styled';

export const SideBarContainer = () => {
  const node = useReduxSelector(selectSelectedNode);
  const theme = useTheme();

  return <SideBar
    node={node}
    theme={theme}
  />;
};
