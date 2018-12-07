import React from 'react';
import { push } from 'connected-react-router';
import { DropZone } from '~components/drop-zone/drop-zone';
import { DropZoneContainer } from '~containers/drop-zone.container';
import { ActionCreators } from '~ducks';
import { createMockStore, shallowWithProviders } from '~utils/test.utils';

describe('DropZoneContainer', () => {
  it('should load the graph on the onLoad callback', () => {
    const store = createMockStore([])({});
    const dropZone = shallowWithProviders(
      <DropZoneContainer/>,
      { store }
    ).find(DropZone);

    const graph: any = {};
    dropZone.props().onLoad(graph);

    expect(store.getActions()).toEqual([
      ActionCreators.loadGraph({ graph }),
      push('/')
    ]);
  });
});
