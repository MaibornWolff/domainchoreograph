import { createField } from 'clean-forms';
import { InlineDatePicker } from 'material-ui-pickers';
import React from 'react';
import { JsonSchemaFormFieldProps } from '~components/json-schema-form/models/json-schema-form-field-props';

type DatePickerMsFieldProps = JsonSchemaFormFieldProps;

export const DatePickerMsField = createField<number, DatePickerMsFieldProps>(({
  input,
  custom: {
    label
  }
}) => {
  const showError = input.touched && input.inValid;
  return <InlineDatePicker
    name={input.name}
    value={input.value == null ? null : input.value}
    onFocus={input.onFocus}
    onBlur={input.onBlur}
    onChange={(date: string) => {
      input.onChange(new Date(date).valueOf());
    }}
    error={showError}
    label={showError ? input.error : label}
  />
});
