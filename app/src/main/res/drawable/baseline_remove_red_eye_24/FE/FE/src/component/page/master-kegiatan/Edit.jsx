import { useEffect, useRef, useState } from "react";
import { number, object, string, date } from "yup";
import { API_LINK } from "../../util/Constants";
import { validateAllInputs, validateInput } from "../../util/ValidateForm";
import SweetAlert from "../../util/SweetAlert";
import Button from "../../part/Button";
import Input from "../../part/Input";
import Loading from "../../part/Loading";
import Alert from "../../part/Alert";
import axios from "axios";

export default function KegiatanEdit({ onChangePage, withID }) {
  const [errors, setErrors] = useState({});
  const [isError, setIsError] = useState({ error: false, message: "" });
  const [isLoading, setIsLoading] = useState(true);

  // Sesuaikan dengan layananVO di backend
  // karena formDataRef akan menjadi tampungan dari API getLayananById
  const formDataRef = useRef({
    id: "",
    nama  : "",
    tanggal: "",
    harga: "",
    status: "",
  });

  // Samakan dengan userSchema
  // berfungsi sebagai validasi setiap input
  const userSchema = object({
    id: number(),
    nama: string().max(100, "maksimum 50 karakter").required("harus diisi"),
    tanggal: date().required("harus diisi"),
    harga: number().required("harus diisi"),
    status: string(),
  });

  // useEffect dengan a khiran array kosong [] adalah kode yang dijalankan ketika
  // halaman pertama kali dimuat. Fungsi ini digunakan untuk mengambil data layanan
  // berdasarkan ID Layanan yang didapatkan dari withID
  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsError({ error: false, message: "" });
        setIsLoading(true);
  
        const response = await axios.get(API_LINK + `kegiatans/getKegiatanById/${withID}`);
        
        if (response.status === 200) {
          const data = response.data.data;
  
          formDataRef.current = {
            id: data.id,
            nama: data.nama,
            tanggal: data.tanggal.substring(0, 10), // Extract date in YYYY-MM-DD format
            harga: data.harga,
            status: data.status,
          };
        } else {
          setIsError({ error: true, message: "Terjadi kesalahan: Gagal mengambil data." });
        }
      } catch (error) {
        setIsError({ error: true, message: "Terjadi kesalahan: Gagal mengambil data." });
      } finally {
        setIsLoading(false);
      }
    };
  
    fetchData();
  }, [withID]);

  const handleInputChange = async (e) => {
    const { name, value } = e.target;
    const validationError = await validateInput(name, value, userSchema);
    formDataRef.current[name] = value;
    setErrors((prevErrors) => ({
      ...prevErrors,
      [validationError.name]: validationError.error,
    }));
    console.log(errors);
  };

  // Fungsi untuk mengupdate ke Backend
  // Disini adalah logika memanggil API Backend
  const handleAdd = async (e) => {
    e.preventDefault();

    // Pengecekan validasi UserSchema
    const validationErrors = await validateAllInputs(
      formDataRef.current,
      userSchema,
      setErrors
    );

    // Jika validasi lolos
    if (Object.values(validationErrors).every((error) => !error)) {
      setIsLoading(true);
      setIsError((prevError) => {
        return { ...prevError, error: false };
      });
      setErrors({});

      // Sesuaikan dengan RequestMapping/PostMapping di Rest Backend
      axios
        .post(API_LINK + "kegiatans/updateKegiatan", {
          // kenapa ga langsung formDataRef.current yang dilempar ke API?
          // karena nama di formDataRef berbeda dengan di model Layanan
          // di formDataRef namanya idLayanan sementara di Model Backend namanya id aja
          // Jadi pastikan ketika send parameter ke Backend, samakan nama parameternya
          // dengan Backend Model Layanan
          idKegiatan: formDataRef.current.id,
          namaKegiatan: formDataRef.current.nama,
          tanggalKegiatan: formDataRef.current.tanggal,
          hargaKegiatan: formDataRef.current.harga,
          statusKegiatan: formDataRef.current.status,
        })
        .then((response) => {
          if (response.status === 500 || response.status === 404) {
            setIsError((prevError) => {
              return {
                ...prevError,
                error: true,
                message: "Terjadi kesalahan: Gagal menyimpan data.",
              };
            });
          } else {
            SweetAlert("Sukses", "Data berhasil diubah", "success");
            onChangePage("index");
          }
        })
        .then(() => setIsLoading(false));
    }
  };

  if (isLoading) return <Loading />;

  return (
    <>
      {isError.error && (
        <div className="flex-fill">
          <Alert type="danger" message={isError.message} />
        </div>
      )}
      <form onSubmit={handleAdd}>
        <div className="card">
          <div className="card-header bg-primary fw-medium text-white">
            Ubah Data
          </div>
          <div className="card-body p-4">
            <div className="row">
              <div className="col-lg-6">
                <Input
                  type="text"
                  forInput="nama" // harus sama dengan formDataRef
                  label="Nama Kegiatan"
                  isRequired
                  value={formDataRef.current.nama} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.nama} // harus sama dengan formDataRef
                />
              </div>
              <div className="col-lg-6">
                <Input
                  type="date"
                  forInput="tanggal" // harus sama dengan formDataRef
                  label="Tanggal Kegiatan"
                  isRequired
                  value={formDataRef.current.tanggal} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.tanggal} // harus sama dengan formDataRef
                />
              </div>
              <div className="col-lg-6">
                <Input
                  type="number"
                  forInput="harga" // harus sama dengan formDataRef
                  label="Harga (IDR)"
                  isRequired
                  value={formDataRef.current.harga} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.harga} // harus sama dengan formDataRef
                />
              </div>
              <div className="col-lg-6">
                <Input
                  type="string"
                  forInput="status" // harus sama dengan formDataRef
                 // label="Stock"
                  isRequired
                  value={formDataRef.current.status} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.status}
                  hidden // harus sama dengan formDataRef
                />
              </div>
            </div>
          </div>
        </div>
        <div className="float-end my-4 mx-1">
          <Button
            classType="secondary me-2 px-4 py-2"
            label="BATAL"
            onClick={() => onChangePage("index")}
          />
          <Button
            classType="primary ms-2 px-4 py-2"
            type="submit"
            label="SIMPAN"
          />
        </div>
      </form>
    </>
  );
}
