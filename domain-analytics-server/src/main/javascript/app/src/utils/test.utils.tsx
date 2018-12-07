import { ThemeProvider } from 'emotion-theming';
import { render, shallow, ShallowWrapper } from 'enzyme';
import React from 'react';
import { Provider } from 'react-redux';
import { ConnectedRouter } from 'connected-react-router';
import { Store } from 'redux';
import _createMockStore, { MockStore } from 'redux-mock-store';
import { GlobalState } from '~ducks';
import { history } from '~history';
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
  options = {
    store: createMockStore([])({}),
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
    <ConnectedRouter history={history}>
      <ThemeProvider theme={context.theme}>
        {children}
      </ThemeProvider>
    </ConnectedRouter>
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
