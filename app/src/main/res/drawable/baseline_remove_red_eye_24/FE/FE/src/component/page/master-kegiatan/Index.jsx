import { useEffect, useRef, useState } from "react";
import { API_LINK } from "../../util/Constants";
import SweetAlert from "../../util/SweetAlert";
import Button from "../../part/Button";
import Input from "../../part/Input";
import Table from "../../part/Table";
import Filter from "../../part/Filter";
import DropDown from "../../part/Dropdown";
import Alert from "../../part/Alert";
import Loading from "../../part/Loading";
import axios from "axios";

// untuk di filter dropdown status
const dataFilterStatus = [
  { Value: "Aktif", Text: "Aktif" },
  { Value: "Tidak Aktif", Text: "Tidak Aktif" },
];

export default function KegiatanIndex({ onChangePage }) {
  const [isError, setIsError] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [originalData, setOriginalData] = useState([]);
  const [currentData, setCurrentData] = useState([]);
  // filter awal hanya menampilkan status yang Aktif
  const [currentFilter, setCurrentFilter] = useState({
    status: "Aktif",
    query: "",
  });

  const searchQuery = useRef();
  const searchFilterStatus = useRef();

  // handle ketika icon Search di klik
  function handleSearch() {
    setCurrentFilter((prevFilter) => ({
      ...prevFilter,
      status: searchFilterStatus.current.value,
      query: searchQuery.current.value,
    }));
  }

  // fungsi untuk memanggil API getLayanan
  const getListKegaiatans = async () => {
    setIsError(false);
    setIsLoading(true);

    try {
      // Sesuaikan dengan RequestMapping/PostMapping di Rest Backend
      let response = await axios.get(API_LINK + "kegiatans/getAllKegiatan");

      // Jika eror status dari axios
      if (response.status === 500 || response.status === 404) {
        throw new Error("Terjadi kesalahan: Gagal mengambil data.");
      } else if (response.status === 200 && response.data.data !== null) {
        // Data di format agar tabel yang tampil rapih
        // Format dari MIS mengambil nama kolom sesuai dengan nama atribut yang kita punya
        const formattedData = response.data.data.map((value) => {
          return {
            Key: value.id, // Key tidak akan menjadi kolom, karena ini berfungsi sebagai ID tiap baris
            ID: value.id, // custom nama kolom jadi ID, gua gamau nama kolomnya idLayanan
            "Nama Kegiatan": value.nama, // custom nama kolom
            Tanggal: value.tanggal, // custom nama kolom
            Harga: new Intl.NumberFormat("id-ID", {
              // custom nama kolom dan format harga IDR
              style: "currency",
              currency: "IDR",
            }).format(value.harga),
            //Stock: value.stock,
            Status: value.status == 1? "Aktif" : "Tidak Aktif",
            Aksi: ["Toggle", "Edit"], // Buat generate icon tiap baris
            Transaksi: "Show", // Buat bikin button show transaksi
          };
        });
        setOriginalData(formattedData);
        setCurrentData(formattedData);
      }
      setIsLoading(false);
    } catch (e) {
      setIsLoading(false);
      console.log(e.message);
      setIsError({
        error: true,
        message: e.message,
      });
    }
  };

  useEffect(() => {
    getListKegaiatans();
  }, []);

  // useEffect(() => {
  //   // Filter data berdasarkan status atau input search
  //   const filteredData = originalData.filter((value) => {
  //     return (
  //       value.Status === currentFilter.status &&
  //       value["Nama Kegiatan"]
  //         .toLowerCase()
  //         .includes(currentFilter.query.toLowerCase())
  //     );
  //   });

  //   setCurrentData(filteredData);
  //   // useEffect dengan array berisi seperti dibawah, artinya useEffect ini akan dijalankan jika ada perubahan value di currentfilter atau originaldata
  // }, [currentFilter, originalData]);

  // Berfungsi untuk menonaktifkan data
  function handleSetStatus(id) {
    setIsError(false);

    // sweetalert2 (custom by reva bukan MIS)
    SweetAlert(
      "Konfirmasi Hapus",
      "Anda yakin ingin menonaktifkan data ini?",
      "warning",
      "Hapus"
    ).then((confirm) => {
      if (confirm) {
        setIsLoading(true);

        axios
          .post(API_LINK + `kegiatans/updateStatusKegiatan/${id}`)
          .then((response) => {
            if (response.status === 500 || response.status === 404)
              setIsError(true);
            else {
              location.reload();
            }
          })
          .then(() => setIsLoading(false));
      }
      setIsLoading(false);
    });
  }

  return (
    <>
      <div className="d-flex flex-column">
        {isError && (
          <div className="flex-fill">
            <Alert
              type="warning"
              message="Terjadi kesalahan: Gagal mengambil data kegiatan."
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
            <Input
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
                ref={searchFilterStatus}
                forInput="ddStatus"
                label="Status"
                type="none"
                arrData={dataFilterStatus}
                defaultValue="Aktif"
              />
            </Filter>
          </div>
        </div>
        <div className="mt-3">
          {isLoading ? (
            <Loading />
          ) : currentData.length === 0 ? (
            <p className="text-center">Tidak ada data</p> // validasi kalo datanya 0 atau kosong
          ) : (
            <div className="d-flex flex-column">
              <Table
                data={currentData}
                onToggle={handleSetStatus}
                onDetail={onChangePage}
                onEdit={onChangePage}
              />
            </div>
          )}
        </div>
      </div>
    </>
  );
}
