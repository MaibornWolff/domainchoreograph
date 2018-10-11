import * as React from 'react';
import { pure } from 'recompose';
import { EDGE_ARROW_HEIGHT, EDGE_ARROW_WIDTH } from '~components/graph/graph';
import { SelectedOptions } from '~components/graph/models/selected-options';
import { Theme } from '~styles/theme';
import { Point } from '~types/point';
import { styled } from '~utils/styled';

function getEdgeColor({ theme, isSelected, selectedOptions = {} }: { theme: Theme } & EdgeProps): string {
  if (isSelected) {
    return selectedOptions.color || theme.colors.graph.selectedEdge;
  }
  return theme.colors.graph.edge;
}

const StyledPath = styled('path')<EdgeProps>`
  fill: none;
  stroke: ${getEdgeColor};
  stroke-width: 2px;
  transition: ${({ theme }) => theme.animations.all};
`;

const StyledPolygon = styled('polygon')<EdgeProps>`
  fill: ${getEdgeColor};
`;

export interface EdgeModel {
  id: string;
  line: string;
  outsideScope: boolean;
  points: Point[];
}

export interface EdgeProps {
  edge: EdgeModel;
  isSelected?: boolean;
  selectedOptions?: SelectedOptions;
}

export const _Edge: React.StatelessComponent<EdgeProps> = (props) => (
  <g>
    <StyledPath
      strokeDasharray={props.edge.outsideScope ? '3, 3' : undefined}
      d={props.edge.line}
      {...props}
    />
    {createRect(props)}
  </g>
);

export const Edge = pure(_Edge);

function createRect(props: EdgeProps) {
  const { edge } = props;
  const lastPoint: Point = edge.points[edge.points.length - 1];
  const secondLastPoint: Point = edge.points[edge.points.length - 2];
  const width = EDGE_ARROW_WIDTH;
  const height = EDGE_ARROW_HEIGHT;
  const x = lastPoint.x;
  const y = lastPoint.y;
  const angle = getAngleBeetweenTwoPointsInDegree(secondLastPoint, lastPoint) - 90;

  return <StyledPolygon
    x={x}
    y={y}
    width={width}
    height={height}
    points={`${x},${y} ${x - width / 2},${y - height} ${x + width / 2},${y - height}`}
    transform={`rotate(${angle} ${x} ${y})`}
    {...props}
  />;
}

function getAngleBeetweenTwoPointsInDegree(p1: Point, p2: Point): number {
  return Math.atan2(p2.y - p1.y, p2.x - p1.x) * 180 / Math.PI;
}
