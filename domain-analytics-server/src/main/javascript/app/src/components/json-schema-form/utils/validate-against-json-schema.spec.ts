import { JSONSchema4 } from 'json-schema';
import { validateAgainstJsonSchema } from '~components/json-schema-form/utils/validate-against-json-schema';

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

describe('validateAgainstJsonSchema', () => {
  it('should validate', () => {
    const value = {
      a: 'Hello',
      b: 'test'
    };

    expect(validateAgainstJsonSchema(schema)({ value } as any)).toEqual([
      ["b", "B is not of a type(s) integer"],
      ["c", "C is required"],
      ["d", "D is required"],
      ["e", "E is required"]
    ]);
  });
});
