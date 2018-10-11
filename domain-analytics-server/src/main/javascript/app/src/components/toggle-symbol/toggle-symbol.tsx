import * as React from 'react';
import { Svg, SvgProps } from '~components/svg/svg';
import { styled } from '~utils/styled';
import * as svgSymbol from './chevron-right.svg';

interface StyledSvgProps {
  isOpen: boolean;
}

const StyledSvg = styled(Svg)<StyledSvgProps>`
  transition: transform ${({theme}) => theme.animations.duration.toggle}ms ease;
  transform: ${({isOpen}) => isOpen ? 'rotate(90deg)' : ''};
`;

export interface ToggleSymbolProps {
  isOpen: boolean;
}

export const ToggleSymbol: React.StatelessComponent<ToggleSymbolProps> = ({isOpen}) => (
  <StyledSvg inlineSvg={svgSymbol} isOpen={isOpen}/>
);
