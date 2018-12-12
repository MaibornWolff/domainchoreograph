import { JSONSchema4 } from 'json-schema';
import {
  getComponentFromSchema,
  JsonSchemaComponentMap
} from '~components/json-schema-form/utils/get-component-from-schema';

const schema: JSONSchema4 = {
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "My Class",
  "type": "object",
  "additionalProperties": false,
  "properties": {
    "a": {
      "propertyOrder": 1,
      "type": "string",
      "title": "A"
    },
    "b": {
      "propertyOrder": 2,
      "type": "integer",
      "title": "B"
    },
    "c": {
      "propertyOrder": 3,
      "type": "number",
      "title": "C"
    },
    "d": {
      "propertyOrder": 4,
      "type": "integer",
      "format": "utc-millisec",
      "title": "D"
    },
    "e": {
      "propertyOrder": 5,
      "$ref": "#/definitions/MySubClass",
      "title": "E"
    }
  },
  "required": ["a", "b", "c", "d", "e"],
  "definitions": {
    "MySubClass": {
      "type": "object",
      "additionalProperties": false,
      "properties": {
        "a": {
          "propertyOrder": 1,
          "type": "integer",
          "title": "A"
        }
      },
      "required": ["a"]
    }
  }
};

describe('getComponentFromSchema', () => {
  it('should throw error if the schema couldn\'t be matched', () => {
    expect(() => getComponentFromSchema(schema.properties!.a, [])).toThrowErrorMatchingSnapshot();
  });

  it('should match the first entry first', () => {
    const mapping: JsonSchemaComponentMap[] = [
      { field: {} as any },
      { matcher: { type: 'string' }, field: {} as any },
    ];

    expect(getComponentFromSchema(schema.properties!.a, mapping)).toBe(mapping[0].field);
  });

  it('should fall through', () => {
    const mapping: JsonSchemaComponentMap[] = [
      { matcher: { type: 'number' }, field: {} as any },
      { field: {} as any },
    ];

    expect(getComponentFromSchema(schema.properties!.a, mapping)).toBe(mapping[1].field);
  });

  it('should match type', () => {
    const mapping: JsonSchemaComponentMap[] = [
      { matcher: { type: 'number' }, field: {} as any },
      { matcher: { type: 'string' }, field: {} as any },
      { field: {} as any },
    ];

    expect(getComponentFromSchema(schema.properties!.a, mapping)).toBe(mapping[1].field);
  });

  it('should match type and format', () => {
    const mapping: JsonSchemaComponentMap[] = [
      { matcher: { type: 'integer', format: 'utc-millisec' }, field: {} as any },
      { field: {} as any },
    ];

    expect(getComponentFromSchema(schema.properties!.d, mapping)).toBe(mapping[0].field);
  });
});
