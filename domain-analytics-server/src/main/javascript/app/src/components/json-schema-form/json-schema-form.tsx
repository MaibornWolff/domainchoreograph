import { Form, FormState } from 'clean-forms';
import { JSONSchema4 } from 'json-schema';
import RefParser from 'json-schema-ref-parser';
import React, { useEffect, useState } from 'react';
import { JsonSchemaFields } from '~components/json-schema-form/json-schema-fields';
import { createDefaultValue } from '~components/json-schema-form/utils/create-default-value';
import { validateAgainstJsonSchema } from '~components/json-schema-form/utils/validate-against-json-schema';
import { styled } from '~utils/styled';

interface JsonSchemaFormProps {
  schema: JSONSchema4;
}

export const JsonSchemaForm: React.FunctionComponent<JsonSchemaFormProps> = (props) => {
  const [schema, setSchema] = useState<JSONSchema4 | null>(null);

  useEffect(() => {
      RefParser.dereference(props.schema)
        .then(derefencedSchema => setSchema(derefencedSchema as JSONSchema4))
    }, []
  );

  if (schema === null) {
    return <div/>;
  }

  return <InnerJsonSchemaForm schema={schema}/>;
};

const InnerJsonSchemaForm: React.FunctionComponent<JsonSchemaFormProps> = ({ schema }) => {
  const [formState, setFormState] = useState<FormState<any>>({ model: createDefaultValue(schema) });

  return (
    <FormWrapper>
      <Form
        state={formState}
        onChange={setFormState}
        validation={validateAgainstJsonSchema(schema)}
      >
        <JsonSchemaFields schema={schema}/>
      </Form>
      <pre>
        {JSON.stringify(formState.model, null, 2)}
      </pre>
    </FormWrapper>
  );
};

const FormWrapper = styled('div')`
    background: white;
`;

