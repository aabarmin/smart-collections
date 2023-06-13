import { TokenResponse } from "@react-oauth/google";
import { createContext } from "react";

const LoginContext = createContext<TokenResponse | null>(null);

export default LoginContext;