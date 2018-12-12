import { JSONSchema4 } from 'json-schema';
import React from 'react';
import { JsonSchemaForm } from '~components/json-schema-form/json-schema-form';

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

interface TestingPageProps {
}

export const TestingPage: React.FunctionComponent<TestingPageProps> = () => (
  <JsonSchemaForm schema={schema}/>
);
