import { useEffect, useRef, useState } from "react";
import { PAGE_SIZE, API_LINK } from "../../util/Constants";
import SweetAlert from "../../util/SweetAlert";
import UseFetch from "../../util/UseFetch";
import Button from "../../part/Button";
import Input from "../../part/Input";
import Table from "../../part/Table";
import Paging from "../../part/Paging";
import Filter from "../../part/Filter";
import DropDown from "../../part/Dropdown";
import Alert from "../../part/Alert";
import Loading from "../../part/Loading";
import axios from "axios";

const dataFilterSort = [
  { Value: "asc", Text: "Nama Layanan [↑]" },
  { Value: "desc", Text: "Nama Layanan [↓]" },
];

const dataFilterStatus = [
  { Value: "Aktif", Text: "Aktif" },
  { Value: "Tidak Aktif", Text: "Tidak Aktif" },
];

export default function MasterAlatMesinIndex({ onChangePage }) {
  const [isError, setIsError] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [currentData, setCurrentData] = useState([]);
  const [totalTransaksi, setTotalTransaksi] = useState("");
  const [currentFilter, setCurrentFilter] = useState({
    page: 1,
    query: "",
    sort: "[Nama Alat/Mesin] asc",
    status: "Aktif",
    jenis: "",
  });

  const searchQuery = useRef();
  const searchFilterSort = useRef();
  const searchFilterStatus = useRef();
  const searchFilterJenis = useRef();

  function handleSetCurrentPage(newCurrentPage) {
    setIsLoading(true);
    setCurrentFilter((prevFilter) => {
      return {
        ...prevFilter,
        page: newCurrentPage,
      };
    });
  }

  function handleSearch() {
    setIsLoading(true);
    setCurrentFilter((prevFilter) => {
      return {
        ...prevFilter,
        page: 1,
        query: searchQuery.current.value,
        sort: searchFilterSort.current.value,
        status: searchFilterStatus.current.value,
        jenis: searchFilterJenis.current.value,
      };
    });
  }

  const getListTransaksi = async () => {
    setIsError(false);
    setIsLoading(true);

    try {
      let response = await axios.get(API_LINK + "transaksis/getAllSummaryTransaksi");

      if (response.status === "500" || response.status === "404") {
        throw new Error("Terjadi kesalahan: Gagal mengambil data.");
      } else if (
        response.data.status === "200" &&
        response.data.data === null
      ) {
        setCurrentData(data);
      } else {
        const totalPendapatan = response.data.data.reduce(
          (total, value) => total + value.totalHarga,
          0
        );
        setTotalTransaksi(totalPendapatan);
        const formattedData = response.data.data.map((value) => {
          return {
            Key: value.idPemesanan,
            ID: value.idPemesanan,
            // Tanggal2: value.tanggal,
            Tanggal: new Date(value.tanggal).toLocaleDateString("id-ID", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit",
            }),
            "Nama Kegiatan": value.namaKegiatan,
            Harga: new Intl.NumberFormat("id-ID", {
              style: "currency",
              currency: "IDR",
            }).format(value.hargaKegiatan),
            Jumlah: value.jumlah,
            Total: new Intl.NumberFormat("id-ID", {
              style: "currency",
              currency: "IDR",
            }).format(value.totalHarga),
          };
        });
        setCurrentData(formattedData);
        setIsLoading(false);
      }
    } catch (e) {
      setIsLoading(true);
      console.log(e.message);
      setIsError((prevError) => ({
        ...prevError,
        error: true,
        message: e.message,
      }));
    }
  };

  useEffect(() => {
    getListTransaksi();
  }, []);

  return (
    <>
      <div className="d-flex flex-column">
        {isError && (
          <div className="flex-fill">
            <Alert
              type="warning"
              message="Terjadi kesalahan: Gagal mengambil data alat/mesin."
            />
          </div>
        )}
        <div className="flex-fill">
          <div className="input-group">
            <Button
              iconName="add"
              classType="success"
              label="Tambah"
              onClick={() => onChangePage("add")}
            />
            {/* <Input
              ref={searchQuery}
              forInput="pencarianAlatMesin"
              placeholder="Cari"
            />
            <Button
              iconName="search"
              classType="primary px-4"
              title="Cari"
              onClick={handleSearch}
            />
            <Filter>
              <DropDown
                ref={searchFilterSort}
                forInput="ddUrut"
                label="Urut Berdasarkan"
                type="none"
                arrData={dataFilterSort}
                defaultValue="asc"
              />
              <DropDown
                ref={searchFilterStatus}
                forInput="ddStatus"
                label="Status"
                type="none"
                arrData={dataFilterStatus}
                defaultValue="Aktif"
              />
            </Filter> */}
          </div>
        </div>
        <div className="mt-3">
          {isLoading ? (
            <Loading />
          ) : currentData.length === 0 ? (
            <p className="text-center">Tidak ada data</p>
          ) : (
            <div className="d-flex flex-column">
              <Table data={currentData} />
              <h5 className="text-end">
                Total Pendapatan:{" "}
                {new Intl.NumberFormat("id-ID", {
                  style: "currency",
                  currency: "IDR",
                }).format(totalTransaksi)}
              </h5>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
