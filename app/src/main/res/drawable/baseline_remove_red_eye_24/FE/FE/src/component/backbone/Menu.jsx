import { ROOT_LINK } from "../util/Constants";
import Icon from "../part/Icon";

let active_menu;
let active_collapse;

const activeURL = (
  location.protocol +
  "//" +
  location.host +
  location.pathname
).replace(/\/$/, "");

// INI SEMENTARA --- BEGIN
const arrMenu = [
  { head: "Logout", headkey: "logout", link: ROOT_LINK + "/logout", sub: [] },
  { head: "Beranda", headkey: "beranda", link: ROOT_LINK, sub: [] },
  {
    head: "Kegiatan",
    headkey: "kegiatans",
    link: ROOT_LINK + "/kegiatan",
    sub: [],
  },
  {
    head: "Transaksi",
    headkey: "transaksis",
    link: ROOT_LINK + "/transaksi",
    sub: [],
  },
  // {
  //   head: "Master Data",
  //   headkey: 1,
  //   link: "#",
  //   sub: [
  //     { title: "Master Pelanggan", link: ROOT_LINK + "/master_pelanggan" },
  //     { title: "Master Produk", link: ROOT_LINK + "/master_produk" },
  //     { title: "Master Proses", link: ROOT_LINK + "/master_proses" },
  //     {
  //       title: "Master Kurs Proses",
  //       link: ROOT_LINK + "/master_kurs_proses",
  //     },
  //     {
  //       title: "Master Alat/Mesin",
  //       link: ROOT_LINK + "/master_alat_mesin",
  //     },
  //     { title: "Master Operator", link: ROOT_LINK + "/master_operator" },
  //   ],
  // },
  // {
  //   head: "Transaksi",
  //   headkey: 2,
  //   link: "#",
  //   sub: [
  //     {
  //       title: "Permintaan Pelanggan",
  //       link: ROOT_LINK + "/permintaan_pelanggan",
  //     },
  //     {
  //       title: "Rencana Anggaran Kerja",
  //       link: ROOT_LINK + "/rencana_anggaran_kerja",
  //     },
  //     { title: "Surat Penawaran", link: ROOT_LINK + "/surat_penawaran" },
  //     { title: "Purchase Order", link: ROOT_LINK + "/purchase_order" },
  //     {
  //       title: "Surat Perintah Kerja",
  //       link: ROOT_LINK + "/surat_perintah_kerja",
  //     },
  //   ],
  // },
];
// INI SEMENTARA --- END

function checkIcon(menu) {
  let menuIcon = "angle-down";

  switch (menu) {
    case "Logout":
      menuIcon = "sign-out-alt";
      break;
    case "Beranda":
      menuIcon = "home";
      break;
    case "Kegiatan":
      menuIcon = "bullet";
      break;
    case "Transaksi":
      menuIcon = "bullet";
      break;
  }

  return menuIcon;
}

function setActiveMenu(menu) {
  active_menu = menu;
}

function setActiveCollapse(id) {
  active_collapse = id;
}

window.addEventListener("load", () => {
  document.getElementById("spanMenu").innerHTML = active_menu;
  if (active_collapse)
    document.getElementById(active_collapse).classList.add("show");
});

export default function Menu() {
  return (
    <nav>
      {arrMenu.map((menu) => {
        if (activeURL === menu["link"]) setActiveMenu(menu["head"]);
        return (
          <div key={"#menucollapse" + menu["headkey"]}>
            <a
              className="text-decoration-none text-black fw-bold"
              data-bs-toggle={menu["link"] === "#" ? "collapse" : ""}
              href={
                menu["link"] === "#"
                  ? "#menucollapse" + menu["headkey"]
                  : menu["link"]
              }
            >
              <div
                className={
                  "w-100 px-3 py-2 d-flex" +
                  (activeURL === menu["link"] ? " bg-primary text-white" : "")
                }
              >
                <Icon
                  type="Bold"
                  name={checkIcon(menu["head"])}
                  cssClass="me-2"
                  style={{ marginTop: "2px" }}
                />
                <span>{menu["head"]}</span>
              </div>
            </a>
            <div className="collapse" id={"menucollapse" + menu["headkey"]}>
              {menu["sub"].map((sub) => {
                if (activeURL === sub["link"]) {
                  setActiveMenu(menu["head"] + " - " + sub["title"]);
                  setActiveCollapse("menucollapse" + menu["headkey"]);
                }
                return (
                  <a
                    className="text-decoration-none text-black"
                    href={sub["link"]}
                    key={"#menucollapse" + menu["headkey"] + sub["link"]}
                  >
                    <div
                      className={
                        "w-100 ps-4 pe-3 py-1 d-flex fw-medium" +
                        (activeURL === sub["link"]
                          ? " bg-primary text-white"
                          : "")
                      }
                    >
                      <Icon name="minus-small" cssClass="me-2 mt-1" />
                      <span>{sub["title"]}</span>
                    </div>
                  </a>
                );
              })}
            </div>
          </div>
        );
      })}
    </nav>
  );
}
