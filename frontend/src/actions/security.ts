import axios from "axios";
import { BackendResponseSingle } from "./response";

const baseUrl = process.env.REACT_APP_BACKED_URL;

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

export function exchangeCode(
  codeResponse: GoogleCodeResponse
): Promise<CodeExchangeResult> {
  const url = `${baseUrl}/oauth/google`;
  return new Promise((resolve) => {
    axios
      .post<BackendResponseSingle<CodeExchangeResult>>(url, codeResponse)
      .then((response) => {
        resolve(response.data.data);
      });
  });
}

export function refreshToken(): Promise<CodeExchangeResult> {
  return new Promise((resolve) => {
    const url = `${baseUrl}/oauth/google/refresh`;
    return new Promise((resolve) => {
      axios
        .get<BackendResponseSingle<CodeExchangeResult>>(url)
        .then((response) => {
          resolve(response.data.data);
        });
    });
  });
}
