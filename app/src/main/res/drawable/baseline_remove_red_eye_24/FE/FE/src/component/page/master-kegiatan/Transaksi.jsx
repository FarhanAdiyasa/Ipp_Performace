import { useEffect, useRef, useState } from "react";
import { API_LINK } from "../../util/Constants";
import Loading from "../../part/Loading";
import Alert from "../../part/Alert";
import axios from "axios";
import Table from "../../part/Table";
import Button from "../../part/Button";

export default function MasterAlatMesinEdit({ onChangePage, withID }) {
  const [errors, setErrors] = useState({});
  const [isError, setIsError] = useState({ error: false, message: "" });
  const [isLoading, setIsLoading] = useState(true);
  const [currentData, setCurrentData] = useState([]);
  const [totalTransaksi, setTotalTransaksi] = useState("");

  const getListTransaksiByLayanan = async () => {
    setIsError(false);
    setIsLoading(true);

    try {
      let response = await axios.get(
        API_LINK + `transaksis/getSummaryTransaksiByKegiatan/${withID}`
      );

      if (response.status === 500 || response.status === 404) {
        throw new Error("Terjadi kesalahan: Gagal mengambil data.");
      } else if (response.status === 200 && response.data.data !== null) {
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

        console.log(currentData);
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
    getListTransaksiByLayanan();
  }, []);

  if (isLoading) return <Loading />;

  return (
    <>
      {isError.error && (
        <div className="flex-fill">
          <Alert type="danger" message={isError.message} />
        </div>
      )}
      <div className="mt-1">
        {isLoading ? (
          <Loading />
        ) : currentData.length === 0 ? (
          <div>
            <p>Tidak ada data.</p>
            <div className="text-start">
              <Button
                classType="secondary me-2 px-4 py-2"
                label="KEMBALI"
                onClick={() => onChangePage("index")}
              />
            </div>
          </div>
        ) : (
          <div className="d-flex flex-column">
            <h5 className="mb-4 text-center">
              Transaksi berdasarkan Kegiatan{" "}
              <span className="text-primary">
                {currentData[0]["Nama Kegiatan"]}
              </span>
            </h5>
            <Table data={currentData} />
            <h5 className="text-end mb-3">
              Total Pendapatan:{" "}
              {new Intl.NumberFormat("id-ID", {
                style: "currency",
                currency: "IDR",
              }).format(totalTransaksi)}
            </h5>
            <div className="text-end">
              <Button
                classType="secondary me-2 px-4 py-2"
                label="KEMBALI"
                onClick={() => onChangePage("index")}
              />
            </div>
          </div>
        )}
      </div>
    </>
  );
}
