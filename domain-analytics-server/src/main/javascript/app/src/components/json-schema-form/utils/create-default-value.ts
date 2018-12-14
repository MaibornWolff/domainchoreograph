import { JSONSchema4, JSONSchema4TypeName } from 'json-schema';

const defaultValues: Record<Exclude<JSONSchema4TypeName, 'object'>, any> = {
  string: undefined,
  number: undefined,
  integer: undefined,
  boolean: undefined,
  array: [],
  'null': null,
  any: ''
};

export function createDefaultValue(schema: JSONSchema4): any {
  const type = schema.type || 'any';
  if (type instanceof Array) {
    throw new Error('Array types are not supported');
  } else if (type === 'object') {
    return createDefaultValueForObject(schema);
  } else {
    return defaultValues[type];
  }
}

function createDefaultValueForObject(schema: JSONSchema4): Promise<object> {
  if (schema.type !== 'object') {
    throw new Error('Expect schema to be an object');
  }
  let defaultValue: any = {};
  if (schema.properties) {
    Object.entries(schema.properties).forEach(([key, property]) => {
      defaultValue[key] = createDefaultValue(property)
    });
  }
  return defaultValue;
}

