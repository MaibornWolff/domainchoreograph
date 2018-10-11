export interface SelectedOptions {
  color?: string;
}

export function isSelectedOptions(value: unknown): value is SelectedOptions {
  if (typeof value !== 'object') {
    return false;
  }
  return 'color' in (value as any);
}

export function getSelectedOptionsOrUndefined(value: unknown): SelectedOptions | undefined {
   if (isSelectedOptions(value)) {
     return value;
   }
}
