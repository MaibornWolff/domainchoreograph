import * as React from 'react';
import { styled } from '~utils/styled';

const Wrapper = styled('div')`
  position: relative;;
  height: 100%;
`;

const ScrollWrapper = styled('div')`
  overflow: auto;
  height: 100%;
`;

const resizeAreaWidth = 50;
const ResizeArea = styled('div')`
  position: absolute;
  width: ${resizeAreaWidth}px;
  left: -${resizeAreaWidth / 2}px;
  top: 0;
  bottom: 0;
  cursor: col-resize;
  user-select: none;
`;

export interface ResizableProps {
  className?: string;
  wrapperClassName?: string;
}

export interface ResizableState {
  isDragging: boolean;
  dragStart: number | null;
  width: number;
}

export class Resizable extends React.Component<ResizableProps, ResizableState> {
  public state: ResizableState = {
    isDragging: false,
    dragStart: null,
    width: 350,
  };

  public render() {
    return (
      <Wrapper
        style={{width: this.state.width}}
        className={this.props.wrapperClassName}
      >
        <ResizeArea
          onMouseDown={this.startDrag as any}
        />
        <ScrollWrapper className={this.props.className}>
          {this.props.children}
        </ScrollWrapper>
      </Wrapper>
    );
  }

  public componentDidMount() {
    document.addEventListener('mouseup', this.endDrag);
    document.addEventListener('mousemove', this.mouseMove);
  }

  public componentWillUnmount() {
    document.removeEventListener('mouseup', this.endDrag);
    document.removeEventListener('mousemove', this.mouseMove);
  }

  private startDrag = (event: MouseEvent) => {
    this.setState({
      isDragging: true,
      dragStart: event.clientX
    });
  }

  private endDrag = () => {
    this.setState({
      isDragging: false,
      dragStart: null
    });
  }

  private mouseMove = (event: MouseEvent) => {
    if (!this.state.isDragging || this.state.dragStart == null) {
      return;
    }
    const newPosition = event.clientX;
    const offset = this.state.dragStart - newPosition;
    this.setState(oldState => ({
      dragStart: newPosition,
      width: oldState.width + offset
    }));
  }
}
