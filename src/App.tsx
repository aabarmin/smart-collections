import React from 'react';
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Home from './page/home';
import Library from './page/library';
import PageSingle from './page/single';
import PageEdit from './page/edit';
import { GoogleOAuthProvider } from '@react-oauth/google';

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Home />
    },
    {
      path: "/library/:id/edit",
      element: <PageEdit />
    },
    {
      path: "/library/:id",
      element: <PageSingle />
    },
    {
      path: "/library",
      element: <Library />
    }
  ]);

  const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID as string;

  return (
    <GoogleOAuthProvider clientId={clientId}>
      <RouterProvider router={router} />
    </GoogleOAuthProvider>
  );
}

export default App;
