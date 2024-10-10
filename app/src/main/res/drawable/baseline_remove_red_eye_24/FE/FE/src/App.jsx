import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Container from "./component/backbone/Container";
import Header from "./component/backbone/Header";
import SideBar from "./component/backbone/SideBar";

import Beranda from "./component/page/beranda/Root";

import MasterProduct from "./component/page/master-kegiatan/Root";
import Transaksi from "./component/page/transaksi/Root";

export default function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Beranda />,
    },
    {
      path: "/kegiatan",
      element: <MasterProduct />,
    },
    {
      path: "/transaksi",
      element: <Transaksi />,
    },
  ]);

  return (
    <>
      <Header />
      <div style={{ marginTop: "70px" }}></div>
      <div className="d-flex flex-row">
        <SideBar />
        <Container>
          <RouterProvider router={router} />
        </Container>
      </div>
    </>
  );
}
