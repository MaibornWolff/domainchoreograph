export function escapeJavaClass(javaClass: string) {
  return javaClass.replace(/\./g, '-');
}

export function reverseJavaClassEscape(javaClass: string) {
  return javaClass.replace(/-/g, '.');
}
