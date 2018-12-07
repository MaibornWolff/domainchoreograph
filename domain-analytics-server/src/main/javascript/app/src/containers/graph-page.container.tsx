import { connect } from 'react-redux';
import { GraphPage, GraphPageProps } from '~components/graph-page/graph-page';
import { GlobalState } from '~ducks';

export interface GraphPageContainerProps {
  executionContext: string;
  scope?: string;
}

export const GraphPageContainer = connect(
  (state: GlobalState, ownProps: GraphPageContainerProps): GraphPageProps => ({
    graph: state.app.graph,
    ...ownProps
  })
)(GraphPage);
