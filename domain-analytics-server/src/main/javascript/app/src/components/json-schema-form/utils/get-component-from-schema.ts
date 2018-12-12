import { FieldComponent } from 'clean-forms';
import { JSONSchema4, JSONSchema4TypeName } from 'json-schema';
import { DatePickerMsField } from '~components/json-schema-form/form-fields/date-picker-ms-field';
import { NumberTextField, TextField } from '~components/json-schema-form/form-fields/text-field';
import { JsonSchemaFormFieldProps } from '~components/json-schema-form/models/json-schema-form-field-props';

export interface JsonSchemaComponentMap {
  matcher?: { type: JSONSchema4TypeName, format?: string };
  field: FieldComponent<any, JsonSchemaFormFieldProps>;
}

const defaultMapping: JsonSchemaComponentMap[] = [
  { matcher: { type: 'integer', format: 'utc-millisec' }, field: DatePickerMsField },
  { matcher: { type: 'integer' }, field: NumberTextField },
  { matcher: { type: 'number' }, field: NumberTextField },
  { field: TextField }
];

export function getComponentFromSchema(
  schema: JSONSchema4,
  mapping: JsonSchemaComponentMap[] = defaultMapping
): FieldComponent<any, JsonSchemaFormFieldProps> {
  for (const map of mapping) {
    if (map.matcher == null) {
      return map.field;
    }
    const typeIsMatching = map.matcher.type === schema.type;
    const formatIsMatching = map.matcher.format == null || map.matcher.format === schema.format;
    if (typeIsMatching && formatIsMatching) {
      return map.field;
    }
  }
  throw new Error('Couldn\'t match the schema');
}
