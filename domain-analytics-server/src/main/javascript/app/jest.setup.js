const Enzyme = require('enzyme');
const Adapter = require('enzyme-adapter-react-16');

Enzyme.configure({adapter: new Adapter()});

//browserMocks.js
class LocalStorageMock {
  constructor() {
    this.store = {};
  }

  getItem(key) {
    return this.store[key] || null;
  }

  setItem(key, value) {
    this.store[key] = value.toString();
  }

  clear() {
    this.store = {};
  }
}

Object.defineProperty(window, 'localStorage', {
  value: new LocalStorageMock()
});
