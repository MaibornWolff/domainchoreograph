import { JSONSchema4, JSONSchema4TypeName } from 'json-schema';

const defaultValues: Record<Exclude<JSONSchema4TypeName, 'object'>, any> = {
  string: '',
  number: undefined,
  integer: undefined,
  boolean: false,
  array: [],
  'null': null,
  any: ''
};

export function createDefaultValue(schema: JSONSchema4): object {
  return createDefaultValueForObject(schema);
}

function createDefaultValueForObject(schema: JSONSchema4): Promise<object> {
  if (schema.type !== 'object') {
    throw new Error('Expect schema to be an object');
  }
  let defaultValue: any = {};
  if (schema.properties) {
    Object.entries(schema.properties).forEach(([key, property]) => {
      const type = property.type || 'any';
      if (type instanceof Array) {
        throw new Error('Array types are no supported');
      } else if (type === 'object') {
        defaultValue[key] = createDefaultValueForObject(property);
      } else {
        defaultValue[key] = defaultValues[type];
      }
    });
  }
  return defaultValue;
}
