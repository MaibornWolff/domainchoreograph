import { transparentize } from 'polished';
import * as React from 'react';
import { pure } from 'recompose';
import { SelectedOptions } from '~components/graph/models/selected-options';
import { NodeLink } from '~components/graph/node-link/node-link';
import { NodeOptions } from '~constants/node-options';
import { Theme } from '~styles/theme';
import { ChoreoExecutionContext, ChoreoNode } from '~types/choreo-graph';
import { selectPreviewTextFromNode } from '~utils/node.utils';
import { styled } from '~utils/styled';

export interface NodeModel extends ChoreoNode {
  x: number;
  y: number;
  height: number;
  width: number;
  outsideScope: boolean;
  executionContext?: ChoreoExecutionContext;
}

export interface NodeProps {
  node: NodeModel;
  scale: number;
  options: NodeOptions;
  isSelected: boolean;
  selectedOptions?: SelectedOptions;
  toggleNode?: (nodeId: string) => void;
}

function getBorderColor(args: { theme: Theme } & NodeProps) {
  const { theme, node, isSelected, selectedOptions = {} } = args;
  if (node.hasException) {
    return theme.colors.danger;
  }
  if (isSelected) {
    return selectedOptions.color || theme.colors.selectedNode;
  }
  return theme.colors.border.normal;
}

const Wrapper = styled.div<NodeProps>`
  position: absolute;
  background: ${({ theme }) => theme.colors.background[3]};
  border: ${(args) => `
    ${args.node.outsideScope ? 'dashed' : 'solid'}
    ${args.theme.dimensions.border.thick}
    ${getBorderColor(args)}
  `};
  ${(args) => args.isSelected
  ? `box-shadow: 0 0 5px 1px ${transparentize(.5, getBorderColor(args))}`
  : `box-shadow: 0 0 5px 1px ${transparentize(.15, getBorderColor(args))}`
  };
  border-radius: ${({ theme }) => theme.dimensions.borderRadius.big};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 ${({ scale, options }) => options.sideMarginInPx * scale}px;
  width: ${({ node, scale }) => node.width * scale}px;
  height: ${({ node, scale }) => node.height * scale}px;
  font-size: calc(1rem * ${({ scale }) => scale});
  box-sizing: border-box;
  left: ${({ node, scale }) => (node.x - node.width / 2) * scale}px;
  top: ${({ node, scale }) => (node.y - node.height / 2) * scale}px;
  cursor: pointer;
  transition: ${({theme}) => theme.animations.all};
  z-index: 1;
`;

const ValueWrapper = styled.pre<NodeProps>`
  margin: ${({ scale }) => scale * .3}rem;
  text-align: center;
  font-size: ${({ scale }) => scale * .8}rem;
  color: ${({ node, theme }) => node.hasException ? theme.colors.danger : theme.colors.text.normal.secondary};
  white-space: pre-wrap;
  width: 100%;
  overflow: hidden;
`;

const NameWrapper = styled.div`
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
`;

const _Node = (props: NodeProps) => {
  props = {
    ...props,
    scale: props.scale || 1
  };
  const { node, toggleNode } = props;
  const previewMessage = selectPreviewTextFromNode(node);
  return <Wrapper {...props} onClick={() => toggleNode && toggleNode(node.id)}>
    {node.executionContext ? <NodeLink executionContextId={node.executionContext.id}/> : null}
    <NameWrapper>{node.name}</NameWrapper>
    {previewMessage == null ? null : <ValueWrapper {...props}>{previewMessage}</ValueWrapper>}
  </Wrapper>;
};

export const Node = pure(_Node);
