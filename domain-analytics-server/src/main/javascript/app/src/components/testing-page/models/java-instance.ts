import { JSONSchema4 } from 'json-schema';

export interface JavaInstance {
  javaClass: string;
  schema: JSONSchema4;
  value: {};
}
