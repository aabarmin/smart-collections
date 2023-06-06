import React from 'react';
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Home from './page/home';
import Library from './page/library';
import Single from './page/single';

function App() {
  const router = createBrowserRouter([
    {
      path: "/", 
      element: <Home />
    },
    {
      path: "/library/:id", 
      element: <Single />
    },
    {
      path: "/library",
      element: <Library />
    }
  ]);

  return (
    <RouterProvider router={router} />
  );
}

export default App;
