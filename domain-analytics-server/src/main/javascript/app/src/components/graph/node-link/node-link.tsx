import * as React from 'react';
import { Link } from 'react-router-dom';
import { styled } from '~utils/styled';

const StyledLink = styled(Link)`
  position: absolute;
  z-index: 2;
  top: .3rem;
  right: .3rem;
  cursor: pointer;

  &, & > * {
    height: .8rem;
    width: .8rem;
    box-sizing: border-box;
  }

  & > * {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
  }
`;

const InnerPoint = styled('div')`
  background: ${({ theme }) => theme.colors.secondary[0]};
  border-radius: 100%;
  transition: transform 300ms ease;
  transform: scale(.8);

  ${StyledLink as any}:hover & {
    transform: scale(.5);
  }
`;

const OuterPoint = styled('div')`
  background: transparent;
  border: 1px solid ${({ theme }) => theme.colors.secondary[0]};
  border-radius: 100%;
  transition: transform 300ms ease;
  transform: scale(.8);

  ${StyledLink as any}:hover & {
    transform: scale(1);
  }
`;

export interface NodeLinkProps {
  executionContextId: string;
}

export const NodeLink: React.StatelessComponent<NodeLinkProps> = ({ executionContextId }) => (
  <StyledLink to={`/${executionContextId}`} onClick={(event) => event.stopPropagation()}>
    <InnerPoint/>
    <OuterPoint/>
  </StyledLink>
);
