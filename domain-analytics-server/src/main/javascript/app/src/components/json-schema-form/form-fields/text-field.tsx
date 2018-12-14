import MatTextField, { TextFieldProps as MatTextFieldProps } from '@material-ui/core/TextField';
import { createField } from 'clean-forms';
import * as React from 'react';
import { JsonSchemaFormFieldProps } from '~components/json-schema-form/models/json-schema-form-field-props';

export type TextFieldProps = MatTextFieldProps & JsonSchemaFormFieldProps;

const createTextField = <T extends {}>(
  mapEventToValue: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => T | undefined,
  mapValueToDisplayedValue: (value: T | undefined) => string | number | boolean
) => createField<T | undefined, TextFieldProps>(({
  input,
  custom: {
    label,
    ...rest
  }
}) => {
  const showError = input.touched && input.inValid;
  return <MatTextField
    variant="standard"
    name={input.name}
    value={mapValueToDisplayedValue(input.value)}
    onFocus={input.onFocus}
    onBlur={input.onBlur}
    onChange={event => input.onChange(mapEventToValue(event))}
    error={showError}
    label={showError ? input.error : label}
    fullWidth={true}
    {...rest}
  />
});

export const TextField = createTextField(
  event => event.target.value || undefined,
  value => value || ''
);

const numberRegex = /^[1-9]\d*(\.\d+)?$/;

export const NumberTextField = createTextField<number>(
  event => {
    const valueAsString = event.target.value;
    if (valueAsString == '') {
      return undefined;
    }
    const valueAsNumber = +valueAsString;
    if (numberRegex.test(valueAsString) && !isNaN(valueAsNumber)) {
      return +valueAsString;
    } else {
      return valueAsString as any;
    }
  },
  value => value || ''
);

