import 'babel-polyfill';
import * as React from 'react';
import { render } from 'react-dom';
import { AppContainer as HotLoadingContainer, setConfig } from 'react-hot-loader';
import { Provider } from 'react-redux';
import { startWebsocket } from '~api/websocket';
import { AppContainer } from '~containers/app.container';
import { ThemeProviderContainer } from '~containers/theme-provider.container';
import { store } from '~store';

startWebsocket();

const container = document.getElementById('app');

setConfig({
  ignoreSFC: true,
  pureRender: true
});

const renderApp = () =>
  render(
    <HotLoadingContainer>
      <Provider store={store}>
        <ThemeProviderContainer>
          <AppContainer/>
        </ThemeProviderContainer>
      </Provider>
    </HotLoadingContainer>,
    container,
  );

renderApp();

if ((module as any).hot) {
  (module as any).hot.accept('~containers/app.container', () => {
    renderApp();
  });
}
