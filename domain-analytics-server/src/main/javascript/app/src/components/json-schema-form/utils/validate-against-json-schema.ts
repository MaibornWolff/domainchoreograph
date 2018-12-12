import { ValidationFunction } from 'clean-forms';
import { JSONSchema4 } from 'json-schema';
import { Validator } from 'jsonschema';


export function validateAgainstJsonSchema(schema: JSONSchema4): ValidationFunction {
  const validator = new Validator();
  return ({ value }) => {
    const result = validator.validate(value, schema as any);
    const errors = result.errors.map(error => {
      let message = error.message;
      let property = error.property
        .replace(/^instance\.?/, '');
      let schema = error.schema as JSONSchema4;
      if (error.name === 'required') {
        message = `is required`;
        schema = schema.properties![error.argument];
        property = joinProperties(property, error.argument);
      }

      const title = schema.title || '';
      message = `${title} ${message}`;

      return [ property, message ] as [string, string];
    });
    return errors;
  }
}

function joinProperties(path1: string, path2: string): string {
  if (path1 === '') {
    return path2;
  }
  if (path2 === '') {
    return path1;
  }
  return `${path1}.${path2}`;
}
