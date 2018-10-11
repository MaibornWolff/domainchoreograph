import * as React from 'react';
import { styled } from '../../utils/styled';

const Wrapper = styled('div')`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  padding: 2rem;
  box-sizing: border-box;
  font-size: 2rem;
  color: ${({theme}) => theme.colors.secondary};
  background: ${({theme}) => theme.colors.background[2]}
`;

const Message = styled('div')`
  border: ${({theme}) => `${theme.dimensions.border.thick} ${theme.colors.border.normal} dashed`};
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: ${({theme}) => theme.dimensions.borderRadius.big};
`;

export interface DropMessageProps {
}

export const DropMessage: React.StatelessComponent<DropMessageProps> = ({children}) => (
  <Wrapper><Message>{children}</Message></Wrapper>
);
