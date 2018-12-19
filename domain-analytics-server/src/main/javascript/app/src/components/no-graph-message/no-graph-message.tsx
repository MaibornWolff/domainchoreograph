import * as React from 'react';
import { useEffect, useState } from 'react';
import { styled } from '~utils/styled';

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
  color: ${({ theme }) => theme.colors.secondary};
  background: ${({ theme }) => theme.colors.background[2]}
`;

const Message = styled('div')`
  flex-direction: column;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: ${({ theme }) => theme.dimensions.borderRadius.big};
  
  p {
    margin-bottom: 1rem;
  }
`;

export interface DropMessageProps {
}

export const NoGraphMessage: React.FunctionComponent<DropMessageProps> = () => (
  <Wrapper>
    <Message>
      <p>Waiting for input<LoadingDots/></p>
      <p>Please run a Choreography with the Domain Analytics Logger</p>
    </Message>
  </Wrapper>
);

const LoadingDots = () => {
  const emptyDots = `   `;
  const [dots, setDots] = useState(emptyDots);

  useEffect(() => {
    const timeout = setTimeout(() => {
      const dotsCount = Array.from(dots)
        .filter((c) => c === '.')
        .length;
      const newDots = dotsCount < 3
        ? dots.substr(0, dotsCount) + '.' + dots.substr(dotsCount + 1, 3)
        : emptyDots;
      setDots(newDots);
    }, 500);
    return () => clearTimeout(timeout);
  }, [dots]);

  return <span style={{ whiteSpace: 'pre' }}>{dots}</span>
};
