import { createField } from 'clean-forms';
import * as React from 'react';
import { Select, SelectProps } from '~components/select/select';

export const SelectField = createField<string | undefined, SelectProps>(({
  input,
  custom
}) => {
  const showError = input.touched && input.error;
  return <Select
    value={input.value}
    onChange={input.onChange}
    onFocus={input.onFocus}
    error={showError ? input.error : undefined}
    {...custom}
  />
});
