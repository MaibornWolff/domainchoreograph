import { ClassNames, css } from '@emotion/core';
import * as React from 'react';
import ReactJson from 'react-json-view';
import { Markdown } from '~components/markdown/markdown';
import { SectionContainer } from '~containers/section.container';
import { Theme } from '~styles/theme';
import { ChoreoNode } from '~types/choreo-graph';
import { styled } from '~utils/styled';
import { Resizable } from '../resizable/resizable';

const StyledHeader = styled('h2')`
    font-size: 1.3rem;
    font-weight: bold;
    overflow: hidden;
    text-overflow: ellipsis;
    margin: 0 0 .8rem 0;
`;

const resizableWrapperStyle = css`
  z-index: 3;
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
`;

const StyledResizable = styled(Resizable)`
  padding: .5rem 1rem;
  overflow-x: auto;
  overflow-y: scroll;
  box-sizing: border-box;
  background: ${({ theme }) => theme.colors.background[2]};
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
`;

const NoSelection = styled('div')`
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${({ theme }) => theme.colors.text.normal.secondary};
  font-size: 1.3rem;
  height: 100%;
`;

const JsonWrapper = styled('div')`
    font-size: .8rem;

    .react-json-view {
      background: none !important;
    }
`;

const Danger = styled('div')`
  font-size: .8rem;
  color: ${({ theme }) => theme.colors.danger};
  margin-bottom: 1rem;
`;

const PreviewLabel = styled('pre')`
  color: ${({ theme }) => theme.colors.text.dark.secondary};
  font-size: .8rem;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0 0 1rem;
  white-space: pre-wrap;
`;

const ExceptionLabel = styled('div')`
    font-weight: bold;
`;

const ExceptionMessage = styled('pre')`
    white-space: normal;
    margin: .5rem 0;
`;

export interface SideBarProps {
  node: ChoreoNode | null;
  theme: Theme;
}

export interface SideBarState {
}

export class SideBar extends React.Component<SideBarProps, SideBarState> {

  public render() {
    const { node, theme } = this.props;
    if (!node) {
      return null;
    }

    return (
      <ClassNames>
        {({ css }) => (
          <StyledResizable wrapperClassName={css(resizableWrapperStyle)}>
            {
              node
                ? (
                  <div>
                    <StyledHeader>{node.name}</StyledHeader>
                    {node.preview && <PreviewLabel>{node.preview}</PreviewLabel>}
                    {node.exception
                      ? (
                        <SectionContainer header={'Exception'} isInitiallyOpen={true}>
                          <Danger>
                            <ExceptionLabel>Exception:</ExceptionLabel>
                            <ExceptionMessage>{node.exception.detailMessage}</ExceptionMessage>
                          </Danger>
                        </SectionContainer>
                      )
                      : null
                    }
                    {node.doc
                      ? <SectionContainer header={'Documentation'} isInitiallyOpen={true}><Markdown
                        markdown={node.doc}/></SectionContainer>
                      : null}
                    <SectionContainer header={'Inspection'} isInitiallyOpen={true}>
                      <JsonWrapper>
                        <ReactJson
                          src={getNodeValueAsJson(node)}
                          theme={theme.jsonViewTheme}
                          name={getNodeNameForJson(node)}
                          indentWidth={2}
                          collapsed={1}
                        />
                      </JsonWrapper>
                    </SectionContainer>
                  </div>
                )
                : <NoSelection>No Selection</NoSelection>
            }
          </StyledResizable>
        )}
      </ClassNames>
    );
  }
}

function getNodeValueAsJson(node: ChoreoNode): object {
  switch (typeof node.value) {
    case 'object':
      return node.value as object;
    default:
      return { [node.name]: node.value };
  }
}

function getNodeNameForJson(node: ChoreoNode): string | null {
  switch (typeof node.value) {
    case 'object':
      return node.name;
    default:
      return null;
  }
}
