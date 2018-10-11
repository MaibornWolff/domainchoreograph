import ReconnectingWebSocket from 'reconnecting-websocket';
import { websocketUrl } from '~config';
import { ActionCreators } from '~ducks';
import { store } from '~store';
import { ChoreoGraph } from '~types/choreo-graph';

export function startWebsocket() {
  const ws: WebSocket = new ReconnectingWebSocket(websocketUrl);

  ws.addEventListener('open', () => {
    console.info('Connected to websocket');
  });

  ws.addEventListener('close', () => {
    console.info('Disconnected from websocket');
  });

  ws.addEventListener('error', (err) => {
    console.error('Websocket Error', err);
  });

  ws.addEventListener('message', (message) => {
    try {
      const graph: ChoreoGraph = JSON.parse(message.data);
      store.dispatch(ActionCreators.loadGraph({graph}));
    } catch (e) {
      console.error(e);
    }
  });
}
