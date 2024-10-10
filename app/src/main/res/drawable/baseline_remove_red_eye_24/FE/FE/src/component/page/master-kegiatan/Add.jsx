import { useRef, useState } from "react";
import { object, string, number, date } from "yup";
import { API_LINK } from "../../util/Constants";
import { validateAllInputs, validateInput } from "../../util/ValidateForm";
import SweetAlert from "../../util/SweetAlert";
import Button from "../../part/Button";
import Input from "../../part/Input";
import Loading from "../../part/Loading";
import Alert from "../../part/Alert";
import axios from "axios";

export default function KegiatanAdd({ onChangePage }) {
  const [errors, setErrors] = useState({});
  const [isError, setIsError] = useState({ error: false, message: "" });
  const [isLoading, setIsLoading] = useState(false);

  // Sesuaikan dengan atribut model Layanan di Backend
  // ambil atribut yang perlu di input saja, jika ID auto increment gausah dibawa disini
  const formDataRef = useRef({
    namaKegiatan: "",
    tanggalKegiatan: "",
    hargaKegiatan: ""
    //statusKegiatan: 1,
  });

  // Samakan dengan userSchema
  // berfungsi sebagai validasi setiap input
  const userSchema = object({
    namaKegiatan: string()
      .max(100, "maksimum 50 karakter")
      .required("harus diisi"),
    tanggalKegiatan: date().required("harus diisi"),
    hargaKegiatan: number().required("harus diisi"),
    //statusKegiatan: number().min(1, "minimal adalah 1").required("harus diisi"),
  });

  // Untuk menyimpan value dari html ke formDataRef
  const handleInputChange = async (e) => {
    const { name, value } = e.target;
    const validationError = await validateInput(name, value, userSchema);
    formDataRef.current[name] = value;
    setErrors((prevErrors) => ({
      ...prevErrors,
      [validationError.name]: validationError.error,
    }));
  };

  // Fungsi untuk menyimpan ke Backend
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
        .post(API_LINK + "kegiatans/saveKegiatan", formDataRef.current)
        .then((response) => {
          // Jika eror status dari axios
          if (response.status === 500 || response.status === 404) {
            setIsError((prevError) => {
              return {
                ...prevError,
                error: true,
                message: "Terjadi kesalahan: Gagal menyimpan data.",
              };
            });
          } else {
            SweetAlert("Sukses", "Data berhasil disimpan", "success");
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
      {/* Ketika form di click / submit, fungsi handleAdd akan terpanggil */}
      <form onSubmit={handleAdd}>
        <div className="card">
          <div className="card-header bg-primary fw-medium text-white">
            Tambah Data
          </div>
          <div className="card-body p-4">
            <div className="row">
              <div className="col-lg-6">
                <Input
                  type="text"
                  forInput="namaKegiatan" // harus sama dengan formDataRef
                  label="Nama Kegiatan"
                  isRequired
                  value={formDataRef.current.namaKegiatan} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.namaKegiatan} // harus sama dengan formDataRef
                />
              </div>
              <div className="col-lg-6">
                <Input
                  type="date"
                  forInput="tanggalKegiatan" // harus sama dengan formDataRef
                  label="Tanggal Kegiatan"
                  isRequired
                  value={formDataRef.current.tanggalKegiatan} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.tanggalKegiatan} // harus sama dengan formDataRef
                />
              </div>
              <div className="col-lg-6">
                <Input
                  type="number"
                  forInput="hargaKegiatan" // harus sama dengan formDataRef
                  label="Harga (IDR)"
                  isRequired
                  value={formDataRef.current.hargaKegiatan} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.hargaKegiatan} // harus sama dengan formDataRef
                />
              </div>
              {/* <div className="col-lg-6">
                <Input
                  type="number"
                  forInput="productStock" // harus sama dengan formDataRef
                  label="Stock"
                  isRequired
                  value={formDataRef.current.productStock} // harus sama dengan formDataRef
                  onChange={handleInputChange}
                  errorMessage={errors.productStock} // harus sama dengan formDataRef
                />
              </div> */}
            </div>
          </div>
        </div>
        <div className="float-end my-4 mx-1">
          <Button
            classType="secondary me-2 px-4 py-2"
            label="BATAL"
            onClick={() => onChangePage("index")} // Untuk kembali ke halaman Index.jsx
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
