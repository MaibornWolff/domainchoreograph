import { rejects } from 'assert';
import * as React from 'react';
import { DragEventHandler } from 'react';
import { styled } from '~utils/styled';

const Wrapper = styled('div')`
  height: 100vh;
  width: 100vw;
`;

export interface DropZoneProps {
  onLoad: (content: any) => void;
}

export interface DropZoneState {
}

export class DropZone extends React.Component<DropZoneProps, DropZoneState> {
  public render() {
    return (
      <Wrapper
        onDrop={this.handleDrop}
      >
        {this.props.children}
      </Wrapper>
    );
  }

  public componentDidMount() {
    window.addEventListener('dragover', this.preventEvent);
    window.addEventListener('drop', this.preventEvent);
  }

  public componentWillUnmount() {
    window.removeEventListener('dragover', this.preventEvent);
    window.removeEventListener('drop', this.preventEvent);
  }

  private handleDrop: DragEventHandler<any> = async (event) => {
    const { onLoad } = this.props;
    if (!onLoad) {
      return;
    }
    event.stopPropagation();
    event.preventDefault();
    try {
      const result = await fileToJson(event.dataTransfer.files[0]);
      onLoad(result);
    } catch (err) {
      console.error(err);
    }
  }

  private preventEvent = (event: Event) => {
    event.preventDefault();
  }
}

function fileToJson<T = any>(file: File): Promise<T> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onloadend = function() {
      if (typeof this.result === 'string') {
        resolve(JSON.parse(this.result));
      } else {
        reject(new Error(`Invalid file type ${typeof this.result}`));
      }
    };
    reader.readAsText(file);
  });
}
