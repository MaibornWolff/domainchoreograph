import * as React from 'react';
import { styled } from '~utils/styled';

const Wrapper = styled('div')`
  height: 24px;
  width: 24px;
  svg {
    fill: ${({theme}) => theme.colors.icons.normal.primary};
  }
`;

export interface SvgProps {
  inlineSvg: string;
  className?: string;
}

export const Svg: React.StatelessComponent<SvgProps> = ({inlineSvg, className}) => (
  <Wrapper className={className} dangerouslySetInnerHTML={{__html: inlineSvg}}/>
);
