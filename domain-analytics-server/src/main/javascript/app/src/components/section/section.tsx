import * as React from 'react';
import { ToggleSymbol } from '~components/toggle-symbol/toggle-symbol';
import { Theme } from '~styles/theme';
import { wait } from '~utils/async.utils';
import { styled } from '~utils/styled';

const Head = styled('div')`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: .5rem 0;
  cursor: pointer;
`;

const Header = styled('h2')`
  font-size: 1rem;
  padding: 0;
  margin: 0;
  font-weight: bold;
`;

const Line = styled('div')`
  height: 1px;
  background: ${({ theme }) => theme.colors.text.normal.disabled};
`;

const RightLine = styled(Line)`
  flex: 1 1 0;
  margin-left: .5rem;
`;

const LeftLine = styled(Line)`
  flex: 0 0 1rem;
  margin-right: .5rem;
`;

interface BodyProps {
  isOpen: boolean;
  innerHeight: string;
}

const Body = styled.div<BodyProps>`
  overflow: hidden;
  transition: height ${({ theme }) => theme.animations.duration.toggle}ms ease;
  height: ${({ isOpen, innerHeight }) => isOpen ? innerHeight : '0'};
`;

const ChildrenWrapper = styled('div')`
  box-sizing: border-box;
  padding: .5rem 0 .5rem 1rem;
`;

export interface SectionProps {
  header: string;
  theme: Theme;
  isInitiallyOpen: boolean;
}

export interface SectionState {
  isOpen: boolean;
  bodyHeight: string;
}

export class Section extends React.Component<SectionProps, SectionState> {
  public static defaultProps: Partial<SectionProps> = {
    isInitiallyOpen: true
  };

  public state: SectionState = {
    isOpen: Boolean(this.props.isInitiallyOpen),
    bodyHeight: 'auto',
  };

  private bodyRef: HTMLElement | null | undefined;

  public render() {
    const { header, children } = this.props;
    const { isOpen, bodyHeight } = this.state;

    return (
      <div>
        <Head onClick={this.toggleOpen}>
          <ToggleSymbol isOpen={isOpen}/>
          <LeftLine/>
          <Header>{header}</Header>
          <RightLine/>
        </Head>
        <Body
          isOpen={isOpen}
          innerRef={ref => this.bodyRef = ref}
          innerHeight={bodyHeight}
        ><ChildrenWrapper>{children}</ChildrenWrapper></Body>
      </div>
    );
  }

  private toggleOpen = async () => {
    const { isOpen } = this.state;
    const { theme } = this.props;

    this.setState({ bodyHeight: this.getBodyHeight() });

    const OPEN_DELAY = 1;
    await wait(OPEN_DELAY);
    this.setState({ isOpen: !isOpen });

    await wait(theme.animations.duration.toggle);
    this.setState({ bodyHeight: 'auto' });
  }

  private getBodyHeight(): string {
    if (this.bodyRef) {
      return `${this.bodyRef.scrollHeight}px`;
    }
    return '0';
  }
}
