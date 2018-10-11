import * as React from 'react';

enum ComparisonType {
  MODIFIED = 'MODIFIED',
  ADDED = 'ADDED',
  REMOVED = 'REMOVED',
}

interface ComparisonItem {
  type: ComparisonType;
  oldValue?: any;
  newValue?: any;
}

type Comparison<T> = {[key in keyof T]?: ComparisonItem};

function shallowCompare<T>(oldObj: Partial<T>, newObj: Partial<T>): Comparison<T> {
  oldObj = oldObj || {};
  newObj = newObj || {};

  type K = keyof T;
  const oldKeys = Object.keys(oldObj) as K[];
  const newKeys = Object.keys(newObj) as K[];

  const state: Comparison<T> = {};

  newKeys
    .filter(k => !oldKeys.includes(k))
    .forEach(k => state[k] = {
      type: ComparisonType.ADDED,
      newValue: newObj[k],
    });

  oldKeys
    .filter(k => !newKeys.includes(k))
    .forEach(k => state[k] = {
      type: ComparisonType.REMOVED,
      oldValue: oldObj[k],
    });

  newKeys
    .filter(k => newObj[k] !== oldObj[k])
    .forEach(k => state[k] = {
      type: ComparisonType.MODIFIED,
      oldValue: oldObj[k],
      newValue: newObj[k],
    });

  return state;
}

function hasChanged(result: Comparison<any>): boolean {
  return Object.keys(result).length > 0;
}

const reportUpdate = (subject: string) => {
  return (result: Comparison<any>) => {
    if (!hasChanged(result)) {
      return;
    }
    console.info(`%c${subject}:`, 'font-weight: bold; color: grey;');
    Object.entries(result)
      .filter(([_, state]) => !!state)
      .map(([key, state]) => {
        const { type, oldValue, newValue } = state!;
        const messageStart = `%c${key} %c${type}`;
        const messageStartStyles = [
          'font-weight: bold;',
          'font-weight: normal; color: blue;',
        ];
        switch (type) {
          case ComparisonType.ADDED:
          case ComparisonType.REMOVED:
            console.info(messageStart, ...messageStartStyles);
            break;
          case ComparisonType.MODIFIED:
            console.info(
              `${messageStart} %c%o => %o`,
              ...messageStartStyles,
              'color: inherit;',
              oldValue, newValue
            );
            break;
        }
      });
  };
};

type ShouldComponentUpdate<P, S> = (this: React.Component, nextProps: any, nextState: any) => boolean;

const analyzeShouldComponentUpdate: ShouldComponentUpdate<any, any> = function(nextProps, nextState) {
  const constructor = (this as any).constructor;
  const componentName = constructor.displayName || constructor.name || 'UnknownComponent';

  console.info(componentName);
  const reportPropsUpdate = reportUpdate('Props');
  const reportStateUpdate = reportUpdate('State');
  const propsDiff = shallowCompare(this.props, nextProps);
  const stateDiff = shallowCompare(this.state, nextState);
  const shouldUpdate = hasChanged(propsDiff) || hasChanged(stateDiff);
  if (shouldUpdate) {
    console.group(componentName);
    reportPropsUpdate(propsDiff);
    reportStateUpdate(stateDiff);
    console.groupEnd();
  }
  return shouldUpdate;
};

export class ReactDebugComponent<P, S> extends React.Component<P, S> {
  public shouldComponentUpdate(nextProps: P, nextState: S) {
    return analyzeShouldComponentUpdate.call(this, nextProps, nextState);
  }
}
