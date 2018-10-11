import { shallow } from 'enzyme';
import * as React from 'react';
import { mockDragEvent, renderWithProviders } from '~utils/test.utils';
import { DropZone } from './drop-zone';

describe('DropZone', () => {
  it('should render', () => {
    expect(renderWithProviders(<DropZone onLoad={jest.fn()}/>)).toMatchSnapshot();
  });

  it('should handle the drop', async () => {
    const onLoad = jest.fn();
    const dropZone = shallow(<DropZone onLoad={onLoad}/>);
    const expectedObj = { a: 123 };
    const mockFile = new File([JSON.stringify(expectedObj)], 'test.json');
    const mockEvent = mockDragEvent(mockFile);
    const { onDrop } = dropZone.dive().props() as any;

    await onDrop(mockEvent);

    expect(onLoad).toHaveBeenCalledWith(expectedObj);
  });
});
