import { JSONSchema4 } from 'json-schema';
import { createDefaultValue } from './create-default-value';
import RefParser from 'json-schema-ref-parser';

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

describe('createDefaultValue', () => {
  it('should create default value for schema', async () => {
    const expectedValue = {
      a: undefined,
      b: undefined,
      c: undefined,
      d: undefined,
      e: {
        a: undefined
      },
    };


    expect(createDefaultValue(await RefParser.dereference(schema) as any)).toEqual(expectedValue);
  });
});
