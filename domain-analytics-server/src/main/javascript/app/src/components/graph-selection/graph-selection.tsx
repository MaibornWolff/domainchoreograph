import * as React from 'react';
import { Link } from 'react-router-dom';
import { GraphContainer } from '~containers/graph.container';
import { styled } from '~utils/styled';

const Wrapper = styled('div')`
  display: flex;
`;

const ItemWrapper = styled(Link)`
  padding: 0;
  margin: .5rem;
  border: ${({theme}) => `${theme.colors.border.normal} solid ${theme.dimensions.border.normal}`};
  background: ${({theme}) => theme.colors.background[1]};
  border-radius: ${({theme}) => theme.dimensions.borderRadius.normal};
  cursor: pointer;
  box-shadow: ${({theme}) => theme.shadows[0]};
  transition: ${({theme}) => theme.animations.all};
  &:hover {
    box-shadow: ${({theme}) => theme.shadows[1]};
  }
`;

const GraphWrapper = styled('div')`
  padding: .5rem;
  pointer-events: none;
`;

const Header = styled('h3')`
  padding: .2rem;
  font-size: .8rem;
  margin: 0;
  background: ${({theme}) => theme.colors.background[3]};
  text-align: center;
`;

export interface GraphSelectionProps {
  executionContext: string;
  scopes: string[];
}

export const GraphSelection: React.StatelessComponent<GraphSelectionProps> = ({ scopes, executionContext }) => (
  <Wrapper>
    {scopes.map(scope => (
      <ItemWrapper key={scope} to={`/${executionContext}/${scope}`}>
        <GraphWrapper>
          <GraphContainer
            key={scope}
            scope={scope}
            height={100}
          />
        </GraphWrapper>
        <Header>{scope}</Header>
      </ItemWrapper>
    ))}
  </Wrapper>
);
