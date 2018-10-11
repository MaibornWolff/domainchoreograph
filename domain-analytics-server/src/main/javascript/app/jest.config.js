module.exports = {
  'globals': {
    'ts-jest': {
      "useBabelrc": true,
      'tsConfigFile': './tsconfig.spec.json',
    }
  },
  'testURL': 'http://localhost/',
  'transform': {
    "^.+\\.tsx?$": "ts-jest",
    "^.+\\.jsx?$": "<rootDir>/node_modules/babel-jest",
  },
  'testRegex': '(/__tests__/.*|(\\.|/)(test|spec))\\.(jsx?|tsx?)$',
  'moduleFileExtensions': [
    'ts',
    'tsx',
    'js',
    'jsx',
    'json',
    'node'
  ],
  'snapshotSerializers': [
    'enzyme-to-json/serializer'
  ],
  'moduleNameMapper': {
    '^~(.*)$': '<rootDir>/src/$1',
    '\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$': '<rootDir>/__mocks__/fileMock.js',
    '\\.(svg)$': '<rootDir>/__mocks__/svgMock.js',
    '\\.(css)$': '<rootDir>/__mocks__/styleMock.js'
  },
  'setupTestFrameworkScriptFile': './jest.setup-test-framework.js',
  'setupFiles': [
    './jest.setup.js'
  ],
};
