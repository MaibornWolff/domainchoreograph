import * as React from 'react';
import { styled } from '~utils/styled';

export interface ButtonProps {
  lightColor?: boolean;
}

export const Button = styled.button<ButtonProps>`
  border: none;
  padding: .5rem;
  background: none;
  font-size: 1rem;
  cursor: pointer;
  border-radius: ${({theme}) => theme.dimensions.borderRadius.normal};
  color: ${({ lightColor, theme }) =>
  lightColor
    ? theme.colors.text.light.primary
    : theme.colors.text.dark.primary
  };

  &:hover, &:focus {
    outline: none;
    background: rgba(0, 0, 0, .15);
  }
`;
