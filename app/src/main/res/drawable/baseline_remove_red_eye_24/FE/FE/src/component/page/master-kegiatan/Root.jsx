import { useState } from "react";
import KegiatanIndex from "./Index";
import KegiatanAdd from "./Add";
import KegiatanEdit from "./Edit";
import MasterInventoryTransaksi from "./Transaksi";

// Root ini tuh kaya buat ngarahin kita di Menu Layanan
export default function MasterInventory() {
  const [pageMode, setPageMode] = useState("index");
  const [dataID, setDataID] = useState();

  function getPageMode() {
    switch (pageMode) {
      // misal klo onChangePage nya berisi string "index", dia bakal manggil index.jsx
      case "index":
        return <KegiatanIndex onChangePage={handleSetPageMode} />;
      case "add":
        return <KegiatanAdd onChangePage={handleSetPageMode} />;
      // case "detail":
      //   return (
      //     <MasterInventoryDetail
      //       onChangePage={handleSetPageMode}
      //       withID={dataID}
      //     />
      //   );
      case "edit":
        return (
          <KegiatanEdit
            onChangePage={handleSetPageMode}
            withID={dataID}
          />
        );
      case "transaksi":
        return (
          <MasterInventoryTransaksi
            onChangePage={handleSetPageMode}
            withID={dataID}
          />
        );
    }
  }

  function handleSetPageMode(mode) {
    setPageMode(mode);
  }

  function handleSetPageMode(mode, withID) {
    setDataID(withID);
    setPageMode(mode);
  }

  return <div>{getPageMode()}</div>;
}
