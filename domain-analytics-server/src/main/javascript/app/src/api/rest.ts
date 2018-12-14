import axios from 'axios';
import { JSONSchema4 } from 'json-schema';
import { restUrl } from '~config';
import { ChoreoGraph } from '~types/choreo-graph';

export namespace RestApi {
  export function getSchema(target: string): Promise<JSONSchema4> {
    const url = buildUrl(restUrl)`/schema/${target}`;
    return axios.get(url).then(({ data }) => data);
  }

  export function getDomainDefinitions(): Promise<string[]> {
    const url = buildUrl(restUrl)`/domain-definitions`;
    return axios.get(url).then(({ data }) => data);
  }

  export function getGraph(target: string): Promise<ChoreoGraph> {
    const url = buildUrl(restUrl)`/graph/${target}`;
    return axios.get(url).then(({ data }) => data);
  }

  export function run(request: RunChoreographyRequest): Promise<ChoreoGraph> {
    const url = buildUrl(restUrl)`/run`;
    return axios.post(url, request).then(({ data }) => data);
  }
}

export interface JsonEncodedClass {
  canonicalName: string,
  json: string
}

export interface RunChoreographyRequest {
  targetCanonicalName: string,
  inputs: JsonEncodedClass[]
}


export type UrlTemplateFunction = (strings: TemplateStringsArray, ...interpolations: string[]) => string;

export function buildUrl(baseUrl: string = ''): UrlTemplateFunction {
  return (strings, ...interpolations) => strings.reduce(
    (result, nextString, i) => result + nextString + encodeURIComponent(interpolations[i] || ''),
    baseUrl
  );
}
