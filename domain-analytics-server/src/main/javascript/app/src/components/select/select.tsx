import { FormControl, InputLabel, MenuItem, Select as MatSelect } from '@material-ui/core';
import React, { FocusEvent, useCallback } from 'react';

export interface SelectProps {
  value?: string;
  options: string[];
  onChange?: (newValue: string) => void;
  onFocus?: (event: FocusEvent) => void;
  label: string;
  error?: string;
}

export const Select: React.FunctionComponent<SelectProps> = ({
  value,
  options,
  onChange: handleChange,
  onFocus: handleFocus,
  label,
  error
}) => {
  return (
    <FormControl
      error={!!error}
      fullWidth={true}
    >
      <InputLabel>{error || label}</InputLabel>
      <MatSelect
        value={value || ''}
        key={value || ''}
        onChange={useCallback(
          event => handleChange && handleChange(event.target.value || undefined),
          [handleChange]
        )}
        onFocus={handleFocus}
      >
        {options.map(option => <MenuItem value={option}>{option}</MenuItem>)}
      </MatSelect>
    </FormControl>
  );
};
