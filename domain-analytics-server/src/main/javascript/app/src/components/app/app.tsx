import { Global, css } from '@emotion/core';
import * as React from 'react';
import { Redirect, Route, RouteComponentProps, Switch } from 'react-router';
import { ConnectedRouter } from 'connected-react-router';
import { DropMessage } from '~components/drop-message/drop-message';
import { DropZoneContainer } from '~containers/drop-zone.container';
import { GraphPageContainer } from '~containers/graph-page.container';
import { history } from '~history';
import { styled } from '~utils/styled';

const globalStyles = css`
  html {
    font-family: 'Roboto', sans-serif;
    font-size: 16px;
  }

  html, body {
    padding: 0;
    margin: 0;
  }

  a {
    color: inherit;
    text-decoration: none;
  }
`;

const Wrapper = styled('div')`
  color: ${({ theme }) => theme.colors.text.normal.primary};
  background: ${({ theme }) => theme.colors.background[0]};
  overflow: hidden;
`;

const ContentWrapper = styled('div')`
  position: relative;
  height: 100vh;
  width: 100vw;
  display: flex;
`;

export interface AppProps {
  loadedGraph: boolean;
  resetState: () => void;
  fetchGraph: () => void;
}

export interface AppState {
}

export class App extends React.Component<AppProps, AppState> {
  public render() {
    return (
      <Wrapper>
        <Global styles={globalStyles}/>
        <DropZoneContainer>
          <ConnectedRouter history={history}>
            <Switch>
              <Route path="/load" component={() =>
                this.props.loadedGraph
                  ? <Redirect to={'/Application'}/>
                  : <DropMessage>Drop json here</DropMessage>}
              />
              {this.props.loadedGraph ? null : <Redirect to={'/load'}/>}
              <Route path="/:executionContext/:scope" component={renderGraphPage}/>
              <Route path="/:executionContext" component={renderGraphPage}/>
              <Route path="/" component={() => <Redirect to="/Application"/>}/>
            </Switch>
          </ConnectedRouter>
        </DropZoneContainer>
      </Wrapper>
    );
  }

  public componentDidCatch(err: Error) {
    this.props.resetState();
    return <Redirect to={'/'}/>;
  }

  public componentDidMount() {
    this.props.fetchGraph();
  }
}

const renderGraphPage: React.StatelessComponent<RouteComponentProps<any>> = ({ match: { params: { executionContext, scope } } }) => {
  return (
    <ContentWrapper>
      <GraphPageContainer executionContext={executionContext} scope={scope}/>
    </ContentWrapper>
  );
};
