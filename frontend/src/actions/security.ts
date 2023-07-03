import axios from "axios";

export type GoogleCodeResponse = {
  authuser?: string;
  code: string;
  prompt?: string;
  scope?: string;
};

export type CodeExchangeResult = {
  token: string;
  token_type: string;
  expires_at: number;
};

export type BackendResponseSingle<T> = {
  data: T;
};

export function exchangeCode(
  codeResponse: GoogleCodeResponse
): Promise<CodeExchangeResult> {
  const baseUrl = process.env.REACT_APP_BACKED_URL;
  const url = `${baseUrl}/oauth/google`;
  return new Promise((resolve) => {
    axios
      .post<BackendResponseSingle<CodeExchangeResult>>(url, codeResponse)
      .then((response) => {
        const result = response.data;
        resolve(result.data);
      });
  });
}
