import { FormLabel } from '@material-ui/core';
import { FieldGroup } from 'clean-forms';
import { JSONSchema4 } from 'json-schema';
import React, { memo } from 'react';
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
  const Field = getComponentFromSchema(schema);
  return <Field
    label={schema.title || name}
    key={name}
    name={name}
    schema={schema}
  />
});
