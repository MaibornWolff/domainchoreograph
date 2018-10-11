import 'katex/dist/katex.css';
import MarkdownIt from 'markdown-it';
import MarkdownItKatex from 'markdown-it-katex';
import React from 'react';
import { MarkdownStyleWrapper } from '~components/markdown/markdown-style-wrapper';
import { styled } from '~utils/styled';

const MarkdownWrapper = styled('div')`
  font-size: 1rem;
  line-height: 1rem;
  position: relative;
  font-family: inherit;
  all: inherit;
`;

export interface MarkdownProps {
  markdown: string;
}

export interface MarkdownState {
}

export class Markdown extends React.Component<MarkdownProps, MarkdownState> {
  private md: MarkdownIt.MarkdownIt = new MarkdownIt();

  public render() {
    const html: string = this.transformMarkdownToHtml(this.props.markdown);
    return (
      <MarkdownStyleWrapper>
        <MarkdownWrapper className="markdown-body" dangerouslySetInnerHTML={{ __html: html }}/>
      </MarkdownStyleWrapper>
    );
  }

  public componentWillMount() {
    this.initializeMarkdownIt();
  }

  private transformMarkdownToHtml(markdown: string): string {
    return this.md.render(markdown);
  }

  private initializeMarkdownIt(): void {
    this.md.use(MarkdownItKatex);
  }
}
