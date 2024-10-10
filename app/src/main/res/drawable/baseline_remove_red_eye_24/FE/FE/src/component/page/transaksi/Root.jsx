import { useState } from "react";
import MasterInventoryIndex from "./Index";
import MasterInventoryAdd from "./Add";

export default function MasterInventory() {
  const [pageMode, setPageMode] = useState("index");
  const [dataID, setDataID] = useState();

  function getPageMode() {
    switch (pageMode) {
      case "index":
        return <MasterInventoryIndex onChangePage={handleSetPageMode} />;
      case "add":
        return <MasterInventoryAdd onChangePage={handleSetPageMode} />;
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
