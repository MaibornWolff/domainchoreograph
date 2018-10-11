export interface ChoreoNode {
  id: string;
  name: string;
  scope: string;
  value: object | string | number | boolean;
  doc?: string;
  exception?: ChoreoException;
  hasException?: boolean;
  preview?: string;
}

export interface ChoreoException {
  detailMessage: string;
  stackTrace: string[];
  suppressedExceptions: string[];
}

export interface ChoreoDependency {
  src: string;
  target: string;
}

export interface ChoreoScope {
  id: string;
  executionContext: string;
  nodes: string[];
}

export interface ChoreoExecutionContext {
  id: string;
  scopes: string[];
}

export interface ChoreoGraph {
  nodes: Record<string, ChoreoNode>;
  scopes: Record<string, ChoreoScope>;
  executionContexts: Record<string, ChoreoExecutionContext>;
  dependencies: ChoreoDependency[];
}
