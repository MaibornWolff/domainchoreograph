import { Button, FormLabel } from '@material-ui/core';
import { FieldArray, FieldArrayItems, FieldGroup } from 'clean-forms';
import { JSONSchema4 } from 'json-schema';
import React, { memo } from 'react';
import { createDefaultValue } from '~components/json-schema-form/utils/create-default-value';
import { FormItems } from '~components/json-schema-form/utils/form-items';
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

const GroupWrapper = styled(FormItems)`
    padding: 0 1rem;
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
      <FieldGroupWrapper>
        <FieldGroup name={name}>
          <JsonSchemaFields schema={schema}/>
        </FieldGroup>
      </FieldGroupWrapper>
    );
  }
  if (schema.type === 'array') {
    const itemSchema = schema.items instanceof Array ? schema.items[0] : schema.items!;
    return (
      <FieldArray name={name} render={({ addItem }) => (
        <>
          {schema.title && <FormLabel>{schema.title}</FormLabel>}
          <FieldArrayItems render={({ remove }) => (
            <FieldGroupWrapper>
              <FormItems>
                <Button color="secondary" onClick={remove}>Delete</Button>
                <JsonSchemaFields schema={itemSchema}/>
              </FormItems>
            </FieldGroupWrapper>
          )}/>
          <Button color="primary" onClick={() => addItem(createDefaultValue(itemSchema))}>Add Item</Button>
        </>
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

const FieldGroupWrapper = styled.div`
    border-radius: 4px;
    border: solid 1px rgba(0, 0, 0, 0.17);
    padding: 1rem;
    margin: .5rem;
`;
