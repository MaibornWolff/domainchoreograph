import { JSONSchema4 } from 'json-schema';
import React from 'react';
import { JsonSchemaFields } from '~components/json-schema-form/json-schema-fields';
import { styled } from '~utils/styled';

interface JsonSchemaFormProps {
  schema: JSONSchema4;
}

export const JsonSchemaForm: React.FunctionComponent<JsonSchemaFormProps> = ({ schema }) => {
  return (
    <FormWrapper>
      <JsonSchemaFields schema={schema}/>
    </FormWrapper>
  );
};

const FormWrapper = styled('div')`
    background: white;
`;

