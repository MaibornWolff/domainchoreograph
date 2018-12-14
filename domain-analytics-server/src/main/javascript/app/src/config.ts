const host = process.env.production ? window.location.host : 'localhost:5400';

export const websocketUrl = `ws://${host}/graph`;
export const restUrl = `http://${host}`;
