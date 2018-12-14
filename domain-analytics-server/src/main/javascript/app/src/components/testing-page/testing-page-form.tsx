import { Button } from '@material-ui/core';
import { AxiosError } from 'axios';
import { FieldGroup, Form, FormState, ValidationMapping } from 'clean-forms';
import { push } from 'connected-react-router';
import { JSONSchema4 } from 'json-schema';
import RefParser from 'json-schema-ref-parser';
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { RestApi, RunChoreographyRequest } from '~api/rest';
import { GraphInputNodeSelection } from '~components/graph-input-node-selection/graph-input-node-selection';
import { JsonSchemaForm } from '~components/json-schema-form/json-schema-form';
import { createDefaultValue } from '~components/json-schema-form/utils/create-default-value';
import { validateAgainstJsonSchema } from '~components/json-schema-form/utils/validate-against-json-schema';
import { SelectField } from '~components/select/select-field';
import { JavaInstance } from '~components/testing-page/models/java-instance';
import { escapeJavaClass, reverseJavaClassEscape } from '~components/testing-page/utils';
import { ActionCreators } from '~ducks';
import { ChoreoGraph } from '~types/choreo-graph';
import { useRedux } from '~utils/redux.utils';
import { styled } from '~utils/styled';

interface AsyncState<T> {
  value: T | null;
  error: Error | null;
  loading: boolean;
}

export function useAsync<T, A extends any[]>(getData: (...args: A) => Promise<T>, args: A): AsyncState<T> {
  const [state, setState] = useState<AsyncState<T>>({
    value: null,
    error: null,
    loading: true
  });

  useEffect(() => {
    setState({ value: state.value, error: state.error, loading: true });
    getData(...args)
      .then(value => setState({ value, error: null, loading: false }))
      .catch((error: AxiosError) => {
        console.error(error, JSON.stringify(error, null, 2));
        setState({ value: null, error, loading: false })
      })
  }, []);

  return state;
}


export const TestPageForm: React.FunctionComponent<{}> = ({}) => {
  const asyncTarget = useAsync(RestApi.getDomainDefinitions, []);

  if (asyncTarget.error) {
    return <div>{asyncTarget.error.message}</div>;
  }

  if (asyncTarget.loading) {
    return <div>Loading...</div>;
  }

  return <InnerTestingPageForm
    targets={asyncTarget.value!}
  />
};

interface _TestingPageFormProps {
  targets: string[];
}

export interface TestingPageFormModel {
  target: string | undefined;
  inputs: Record<string, JavaInstance | null>;
}

export const InnerTestingPageForm: React.FunctionComponent<_TestingPageFormProps> = ({ targets = [] }) => {
  const [formState, setFormState] = useState<FormState<TestingPageFormModel>>({
    model: {
      target: undefined,
      inputs: {}
    }
  });
  const [validationState, setValidationState] = useState<ValidationMapping<TestingPageFormModel>>({
    target: ({value}) => value ? null : 'Required',
  });
  const { model } = formState;

  const inputJavaClasses = useMemo(() => {
    return Object.keys(model.inputs).map(reverseJavaClassEscape);
  }, [model.inputs]);

  const handleOnInputAdd = useCallback(async (javaClass: string) => {
    javaClass = escapeJavaClass(javaClass);
    setFormState({
      ...formState,
      model: {
        ...model,
        inputs: {
          ...model.inputs,
          [javaClass]: null
        }
      }
    });
    let schema = await RestApi.getSchema(reverseJavaClassEscape(javaClass));
    schema = await RefParser.dereference(schema) as JSONSchema4;
    console.log(schema);
    const newInstance = {
      javaClass,
      schema,
      value: createDefaultValue(schema)
    };
    setValidationState({
      ...validationState,
      inputs: {
        [javaClass]: {
          value: validateAgainstJsonSchema(schema)
        }
      }
    });
    setFormState({
      ...formState,
      model: {
        ...model,
        inputs: {
          ...model.inputs,
          [javaClass]: newInstance
        }
      }
    });
  }, [formState, model, setFormState, validationState]);

  const handleOnInputRemove = useCallback((javaClass: string) => {
    javaClass = escapeJavaClass(javaClass);
    const newInputs = { ...model.inputs };
    delete newInputs[javaClass];
    setFormState({
      ...formState,
      model: {
        ...model,
        inputs: newInputs
      }
    })
  }, [formState, model, setFormState]);


  const [graph, setGraph] = useState<ChoreoGraph | null>(null);
  useEffect(() => {
    const setGraphAndResetForm = (graph: ChoreoGraph | null) => {
      setFormState({
        ...formState,
        model: {
          ...model,
          inputs: {}
        }
      });
      setGraph(graph);
    };

    if (formState.model.target) {
      RestApi.getGraph(formState.model.target)
        .then(setGraphAndResetForm)
    } else {
      setGraphAndResetForm(null);
    }
  }, [formState.model.target]);

  const [_, dispatch] = useRedux();
  const submitForm = useCallback(() => {
    const request = mapFormModelToRequest(model);
    RestApi.run(request).then((graph) => {
      dispatch(ActionCreators.fetchGraphSuccess({ graph }));
      dispatch(push('/'));
    })
  }, [model, dispatch]);

  return (
    <Form
      state={formState}
      onChange={setFormState}
      validation={validationState}
      onValidSubmit={submitForm}
    >
      <Wrapper>
        <div>
          <SelectField
            name="target"
            label="Target"
            options={targets}
          />
        </div>
        <div>
          {graph &&
          <GraphInputNodeSelection
            graph={graph}
            selectedJavaClasses={inputJavaClasses}
            onAdd={handleOnInputAdd}
            onRemove={handleOnInputRemove}
          />
          }
        </div>
        <div>
          <FieldGroup name="inputs">
            {Object.entries(model.inputs)
              .map(([javaClass, value]) => {
                if (value == null) {
                  return null;
                }
                return (
                  <FieldGroup key={javaClass} name={`${javaClass}.value`}>
                    <JsonSchemaForm
                      schema={value.schema}
                    />
                  </FieldGroup>
                );
              })}
          </FieldGroup>
        </div>
        <SubmitWrapper>
          <Button color="primary" variant="contained" type="submit">Submit</Button>
        </SubmitWrapper>
      </Wrapper>
    </Form>
  );
};

function mapFormModelToRequest(model: TestingPageFormModel): RunChoreographyRequest {
  return {
    targetCanonicalName: reverseJavaClassEscape(model.target!),
    inputs: Object.values(model.inputs).map((input) => {
      return {
        canonicalName: reverseJavaClassEscape(input!.javaClass),
        json: JSON.stringify(input!.value)
      }
    })
  }
}

const Wrapper = styled.div`
    width: 800px;
    margin: auto;

    & > * {
      margin-bottom: 2rem;
    }
`;

const SubmitWrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    justify-items: center;
`;
