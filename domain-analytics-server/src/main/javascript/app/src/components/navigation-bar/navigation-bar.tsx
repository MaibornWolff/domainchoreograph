import * as React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '~components/button/button';
import { ThemeKey } from '~styles/theme';
import { ChoreoExecutionContext, ChoreoGraph, ChoreoScope } from '~types/choreo-graph';
import { styled } from '~utils/styled';

const StyledNav = styled('nav')`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 3.5rem;
  padding: 0 .8rem;
  background: ${({ theme }) => theme.colors.primary[0]};
  color: ${({ theme }) => theme.colors.text.light.primary};
`;

const NavItems = styled('div')`
  display: flex;
`;

const Ul = styled('ul')`
  display: flex;
  list-style-type: none;
  margin: 0;
  padding: 0;
`;

const StyledLabel = styled('span')`
  padding: .5rem;
  cursor: default;
`;

const StyledLink = styled(Link)`
  color: inherit;
  text-decoration: none;
  padding: .5rem;
`;

const Separator = styled('span')`
  color: ${({ theme }) => theme.colors.text.light.secondary}
`;

export interface NavigationBarProps {
  graph: ChoreoGraph | null;
  executionContext: string;
  scope?: string;
  currentThemeKey?: ThemeKey;
  setThemeKey?: (theme: ThemeKey) => void;
}

export interface NavigationBarState {
}

export class NavigationBar extends React.Component<NavigationBarProps, NavigationBarState> {
  public render() {
    const { graph, executionContext, scope, setThemeKey, currentThemeKey } = this.props;
    if (!graph) {
      return null;
    }

    let startingPoint: NavigationNode;
    if (scope) {
      startingPoint = { type: 'scope', item: graph.scopes[scope] };
    } else {
      startingPoint = { type: 'context', item: graph.executionContexts[executionContext] };
    }
    const navigationPath = getNavigationPath(startingPoint, graph);

    return (
      <StyledNav>
        <NavItems>
          <Ul>
            {
              navigationPath.map((node, i) => {
                const isLast = i === navigationPath.length - 1;

                let link: JSX.Element;
                if (isLast) {
                  link = <StyledLabel>{node.item.id}</StyledLabel>;
                } else {
                  link = node.type === 'context'
                    ? <StyledLink to={`/${node.item.id}`}>{node.item.id}</StyledLink>
                    : <StyledLink to={`/${navigationPath[i - 1].item.id}/${node.item.id}`}>{node.item.id}</StyledLink>;
                }
                return <li key={node.type + node.item.id}>
                  {link}
                  {!isLast && <Separator>></Separator>}
                </li>;
              })
            }
          </Ul>
        </NavItems>
        <NavItems>
          {setThemeKey && currentThemeKey && <Button lightColor={true} onClick={this.switchTheme}>Change Theme</Button>}
        </NavItems>
      </StyledNav>
    );
  }

  private switchTheme = () => {
    const { setThemeKey, currentThemeKey } = this.props;
    if (!setThemeKey || !currentThemeKey) {
      return;
    }

    if (currentThemeKey === 'dark') {
      setThemeKey('light');
    } else {
      setThemeKey('dark');
    }
  }
}

type NavigationNode = {
  type: 'context';
  item: ChoreoExecutionContext;
} | {
  type: 'scope';
  item: ChoreoScope;
};

function getNavigationPath(startingPoint: NavigationNode | undefined, graph: ChoreoGraph): NavigationNode[] {
  console.log(startingPoint, graph);
  let result: NavigationNode[] = [];

  if (!startingPoint) {
    return result;
  }

  let parentNode: NavigationNode;
  if (startingPoint.type === 'scope') {

    const parentContext = graph.executionContexts[startingPoint.item.executionContext];
    parentNode = { type: 'context', item: parentContext };
    if (parentContext.scopes.length > 1) {
      result = [startingPoint];
    }

  } else if (startingPoint.type === 'context') {

    const node = graph.nodes[startingPoint.item.id];
    result = [startingPoint];
    if (!node) {
      return result;
    }
    const parentScopeId = node.scope;
    const parentScope = graph.scopes[parentScopeId];
    console.log('PARENT SCOPE', parentScopeId, parentScope);
    parentNode = { type: 'scope', item: parentScope };

  } else {
    throw new Error(`Invalid navigation node type.`);
  }

  return [...getNavigationPath(parentNode, graph), ...result];
}
