import { Paper } from '@material-ui/core';
import { JSONSchema4 } from 'json-schema';
import React from 'react';
import { JsonSchemaFields } from '~components/json-schema-form/json-schema-fields';
import { FormItems } from '~components/json-schema-form/utils/form-items';
import { styled } from '~utils/styled';

interface JsonSchemaFormProps {
  schema: JSONSchema4;
}

export const JsonSchemaForm: React.FunctionComponent<JsonSchemaFormProps> = ({ schema }) => {
  return (
    <Paper>
      <FormWrapper>
        <JsonSchemaFields schema={schema}/>
      </FormWrapper>
    </Paper>
  );
};

const FormWrapper = styled(FormItems)`
  padding: 1rem;
`;

