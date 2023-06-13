import React from 'react';
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Home from './page/home';
import Library from './page/library';
import PageSingle from './page/single';
import PageEdit from './page/edit';

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

  return (
    <RouterProvider router={router} />
  );
}

export default App;
