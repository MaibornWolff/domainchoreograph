import { push } from 'connected-react-router';
import * as React from 'react';
import { useCallback } from 'react';
import { DropZone } from '~components/drop-zone/drop-zone';
import { ActionCreators } from '~ducks';
import { useRedux } from '~utils/redux.utils';


export const DropZoneContainer: React.FunctionComponent<{}> = React.memo(function _DropZoneContainer(args) {
  const [_, dispatch] = useRedux();

  const handleLoad = useCallback(graph => {
    dispatch(ActionCreators.loadGraph({ graph }));
    dispatch(push('/'));
  }, [dispatch]);

  return <DropZone onLoad={handleLoad} {...args}/>;
});
