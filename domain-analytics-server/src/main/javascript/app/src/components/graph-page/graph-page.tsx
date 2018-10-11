import * as React from 'react';
import { MapInteractionCSS } from 'react-map-interaction';
import { GraphSelection } from '~components/graph-selection/graph-selection';
import { GraphContainer } from '~containers/graph.container';
import { NavigationBarContainer } from '~containers/navigation-bar.container';
import { SideBarContainer } from '~containers/side-bar.container';
import { ChoreoGraph } from '~types/choreo-graph';
import { styled } from '~utils/styled';

const Wrapper = styled('div')`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  flex: 1 1 0;
`;

const GraphWrapper = styled('div')`
  flex: 1 1 0;
  display: flex;
  align-items: center;
  flex-direction: column;
`;

const PaddingWrapper = styled('div')`
  padding: 1rem;
`;

const ContentWrapper = styled('div')`
  position: relative;
  display: flex;
  height: 100%;
`;

export interface GraphPageProps {
  graph: ChoreoGraph | null;
  executionContext: string;
  scope?: string;
}

export interface GraphPageState {
}

export class GraphPage extends React.Component<GraphPageProps, GraphPageState> {
  public render() {
    const { scope, graph, executionContext: executionContextKey } = this.props;
    if (graph == null) {
      return <p>Graph is required</p>;
    }
    const executionContext = graph.executionContexts[executionContextKey];
    if (!executionContext) {
      return <p>Execution Context with key {executionContextKey} not found</p>;
    }
    const scopes = executionContext.scopes;

    let children: JSX.Element;
    if (scopes.length === 0) {
      children = <p>No Scopes</p>;
    } else if (scope) {
      children = scopes.includes(scope)
        ? (
          <MapInteractionCSS key={scope}>
            <PaddingWrapper>
              <GraphContainer scope={scope}/>
            </PaddingWrapper>
          </MapInteractionCSS>
        )
        : <p>Scope {scope} not found</p>;
    } else {
      children = scopes.length === 1
        ? (
          <MapInteractionCSS key={scopes[0]}>
            <PaddingWrapper>
              <GraphContainer scope={scopes[0]}/>
            </PaddingWrapper>
          </MapInteractionCSS>
        )
        : <GraphSelection executionContext={executionContextKey} scopes={scopes}/>;
    }

    return (
      <Wrapper>
        <NavigationBarContainer/>
        <ContentWrapper>
          <GraphWrapper>
            {children}
          </GraphWrapper>
          <SideBarContainer/>
        </ContentWrapper>
      </Wrapper>
    );
  }
}
