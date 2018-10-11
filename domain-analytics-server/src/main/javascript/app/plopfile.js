'use strict';

module.exports = (plop) => {
  plop.setHelper('camelCase', toCamelCase);
  plop.setHelper('toState', toState);
  plop.setHelper('toProps', toProps);

  plop.setGenerator('component', {
    description: 'Create a React component',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'The name of the component'
      },
      {
        type: 'confirm',
        name: 'stateless',
        default: true,
        message: 'Should the component be stateless'
      },
    ],
    actions: data => [
      {
        type: 'add',
        path: 'src/components/{{name}}/{{name}}.tsx',
        templateFile: data.stateless ? 'plop-templates/stateless-component.hbs' : 'plop-templates/class-component.hbs'
      },
      {
        type: 'add',
        path: 'src/components/{{name}}/{{name}}.spec.tsx',
        templateFile: 'plop-templates/component-test.hbs'
      },
    ]
  });


  plop.setGenerator('container', {
    description: 'Create a React container',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'The name of the container'
      },
    ],
    actions: [
      {
        type: 'add',
        path: 'src/containers/{{name}}.container.tsx',
        templateFile: 'plop-templates/container.hbs'
      },
    ]
  });

};

// Helpers
function toCamelCase(name) {
  return name
    .split('-')
    .map((word, i) =>
      word.length > 0
        ? `${word[0].toUpperCase()}${word.substr(1)}`
        : word
    )
    .join('');
}

function toState(name) {
  return `${toCamelCase(name)}State`;
}

function toProps(name) {
  return `${toCamelCase(name)}Props`;
}
