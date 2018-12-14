import React, { Component } from 'react';
import { TestPageForm } from '~components/testing-page/testing-page-form';

export const TestingPage = TestPageForm;

interface TestingPageProps {
}

export class _TestingPage extends Component {
  state = {
    error: null as Error | null
  };

  render() {
    if (this.state.error) {
      return <div>{this.state.error.message}</div>
    }
    return <TestPageForm/>
  }

  componentDidCatch(error: Error, errorInfo: React.ErrorInfo): void {
    this.setState({ error });
  }
}

