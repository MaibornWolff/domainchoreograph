import { ConnectedRouter, routerMiddleware, RouterState } from 'connected-react-router';
import { render, shallow, ShallowWrapper } from 'enzyme';
import createMemoryHistory from 'history/createMemoryHistory';
import React from 'react';
import { Provider } from 'react-redux';
import { Store } from 'redux';
import _createMockStore, { MockStore } from 'redux-mock-store';
import { ThemeProvider } from 'styled-components';
import { GlobalState } from '~ducks';
import { history } from '~create-history';
import { Theme, THEME } from '~styles/theme';

export function mockDragEvent(file: File): DragEvent {
  return mockEvent('drag', {
    dataTransfer: {
      files: [file]
    }
  }) as any;
}

export function mockEvent<T>(name: string, override: T): Event & T {
  const event = new Event(name);
  Object.entries(override).forEach(([key, value]) => {
    Object.defineProperty(event, key, { value });
  });
  return event as any;
}

export function mockEmotion() {
  const original = require.requireActual('emotion');
  return {
    ...original,
    css: () => 'class-mock'
  };
}

export const createMockStore = _createMockStore;

interface ProviderOptions {
  store?: MockStore<Partial<GlobalState>>;
}

interface Context {
  theme: Theme;
  store?: Store<Partial<GlobalState>>;
}

function createContext(options: ProviderOptions = {}) {
  const routerState: RouterState = {
    location: history.location,
    action: 'PUSH'
  };
  options = {
    store: createMockStore([
      routerMiddleware(createMemoryHistory()),
    ])({
      app: {},
      router: routerState
    }),
    ...options,
  };
  const context: Context = {
    theme: THEME,
  };
  context.store = options.store;
  return context;
}

const Providers: React.StatelessComponent<{ options?: ProviderOptions }> = ({ options, children }) => {
  const context = createContext(options);
  let result = (
    <Provider store={context.store!}>
      <ThemeProvider theme={context.theme}>
        <ConnectedRouter history={history}>
          {children}
        </ConnectedRouter>
      </ThemeProvider>
    </Provider>
  );
  if (context.store) {
    result = <Provider store={context.store} children={result}/>;
  }
  return result;
};

export function renderWithProviders(element: JSX.Element, options?: ProviderOptions) {
  return render(<Providers options={options}>{element}</Providers>);
}

export function shallowWithProviders<P>(element: React.ReactElement<P>, options?: ProviderOptions): ShallowWrapper<P> {
  return shallow(element, { context: createContext(options) });
}
