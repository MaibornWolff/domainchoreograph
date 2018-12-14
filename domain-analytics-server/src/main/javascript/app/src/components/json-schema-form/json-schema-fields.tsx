import { Button, FormLabel } from '@material-ui/core';
import { FieldArray, FieldArrayItems, FieldGroup } from 'clean-forms';
import { JSONSchema4 } from 'json-schema';
import React, { memo } from 'react';
import { createDefaultValue } from '~components/json-schema-form/utils/create-default-value';
import { getComponentFromSchema } from '~components/json-schema-form/utils/get-component-from-schema';
import { styled } from '~utils/styled';

export interface JsonSchemaFieldsProps {
  schema: JSONSchema4;
}

export const JsonSchemaFields = memo(function _JsonSchemaField({
  schema
}: JsonSchemaFieldsProps) {
  const fields = Object.entries(schema.properties!)
    .map(([key, property]) => <JsonSchemaField key={key} name={key} schema={property}/>);

  return (
    <GroupWrapper>
      {schema.title && <FormLabel>{schema.title}</FormLabel>}
      {fields}
    </GroupWrapper>
  );
});

const GroupWrapper = styled('div')`
    padding: 0 1rem;
    max-width: 30rem;
`;


interface JsonSchemaFieldProps {
  name: string;
  schema: JSONSchema4;
}

const JsonSchemaField = memo(function _JsonSchemaField({
  name,
  schema
}: JsonSchemaFieldProps) {
  if (schema.type === 'object') {
    return (
      <FieldGroup name={name}>
        <JsonSchemaFields schema={schema}/>
      </FieldGroup>
    );
  }
  if (schema.type === 'array') {
    const itemSchema = schema.items instanceof Array ? schema.items[0] : schema.items!;
    return (
      <FieldArray name={name} render={({ addItem }) => (
        <div>
          <FieldArrayItems render={({ remove }) => (
            <div>
              <Button onClick={remove}>Delete</Button>
              <JsonSchemaFields schema={itemSchema}/>
            </div>
          )}/>
          <Button onClick={() => addItem(createDefaultValue(itemSchema))}>Add Item</Button>
        </div>
      )}/>
    );
  }
  const Field = getComponentFromSchema(schema);
  return <Field
    label={schema.title || name}
    key={name}
    name={name}
    schema={schema}
  />
});
