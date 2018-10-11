import { shallow } from 'enzyme';
import * as React from 'react';
import { mockEvent } from '~utils/test.utils';
import { Resizable } from './resizable';

describe('Resizable', () => {
  it('should render', () => {
    expect(shallow(<Resizable/>)).toMatchSnapshot();
  });

  it('should set state on drag start', () => {
    const component = shallow(<Resizable/>);
    const resizableInstance = component.instance() as Resizable;

    expect(resizableInstance.state).toMatchObject({
      isDragging: false,
      dragStart: null,
    });

    component.find('ResizeArea').simulate('mousedown', { clientX: 100 } as any);

    expect(resizableInstance.state).toMatchObject({
      isDragging: true,
      dragStart: 100,
    });
  });

  it('should set state on mouse move if drag started', () => {
    const component = shallow(<Resizable/>);
    const resizableInstance = component.instance() as Resizable;
    resizableInstance.setState({
      isDragging: true,
      dragStart: 50,
      width: 100,
    });

    document.dispatchEvent(mockEvent('mousemove', { clientX: 100 }));

    expect(resizableInstance.state).toMatchObject({
      isDragging: true,
      dragStart: 100,
      width: 50,
    });
  });

  it('should not set state on mouse move if drag not started', () => {
    const component = shallow(<Resizable/>);
    const resizableInstance = component.instance() as Resizable;
    resizableInstance.setState({
      isDragging: false,
      dragStart: null,
      width: 100,
    });

    document.dispatchEvent(mockEvent('mousemove', { clientX: 100 }));

    expect(resizableInstance.state).toMatchObject({
      isDragging: false,
      dragStart: null,
      width: 100,
    });
  });

  it('should set state on drag end', () => {
    const component = shallow(<Resizable/>);
    const resizableInstance = component.instance() as Resizable;
    resizableInstance.setState({
      isDragging: true,
      dragStart: 0,
    });

    document.dispatchEvent(new MouseEvent('mouseup'));

    expect(resizableInstance.state).toMatchObject({
      isDragging: false,
      dragStart: null,
    });
  });
});
