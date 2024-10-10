import { useEffect, useRef, useState } from "react";
import { object, string, number } from "yup";
import { API_LINK } from "../../util/Constants";
import { validateAllInputs, validateInput } from "../../util/ValidateForm";
import SweetAlert from "../../util/SweetAlert";
import Button from "../../part/Button";
import DropDown from "../../part/Dropdown";
import Input from "../../part/Input";
import Loading from "../../part/Loading";
import Alert from "../../part/Alert";
import axios from "axios";
import Label from "../../part/Label";

export default function MasterAlatMesinAdd({ onChangePage }) {
  const [errors, setErrors] = useState({});
  const [isError, setIsError] = useState({ error: false, message: "" });
  const [isLoading, setIsLoading] = useState(false);
  const [listProduct, setlistProduct] = useState([]);
  const [listBranch, setlistBranch] = useState([]);
  const [dropdownProduct, setdropdownProduct] = useState([]);
  const [harga, setHarga] = useState("");
  const [total, setTotal] = useState("");

  const formDataRef = useRef({
    tanggalPemesanan: "",
    idKegiatan: "",
    jumlahPemesanan: "",
  });

  const userSchema = object({
    tanggalPemesanan: string().required("harus diisi"),
    idKegiatan: string().required("harus dipilih"),
    jumlahPemesanan: number().min(1, "minimal adalah 1").required("harus diisi"),
  });

  const getListProduct = async () => {
    setIsError(false);
    setIsLoading(true);

    try {
      let response = await axios.get(API_LINK + "kegiatans/getAllKegiatan");

      if (response.status === 500 || response.status === 404) {
        throw new Error("Terjadi kesalahan: Gagal mengambil data.");
      } else if (response.status === 200 && response.data.data !== null) {
        setlistProduct(response.data.data);
        const data = response.data.data
          .filter((data) => data.status === 1)
          .map((data) => ({
            Value: data.id,
            Text: data.nama,
          }));
        setdropdownProduct(data);
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

  // const getListBranch = async () => {
  //   setIsError(false);
  //   setIsLoading(true);

  //   try {
  //     let response = await axios.get(API_LINK + "branches/getAllBranch");

  //     if (response.status === 500 || response.status === 404) {
  //       throw new Error("Terjadi kesalahan: Gagal mengambil data.");
  //     } else if (response.status === 200 && response.data.data !== null) {
  //       const data = response.data.data.map((data) => ({
  //         Value: data.branchId,
  //         Text: data.branchName,
  //       }));
  //       setlistBranch(data);
  //     }
  //     setIsLoading(false);
  //   } catch (e) {
  //     setIsLoading(false);
  //     console.log(e.message);
  //     setIsError({
  //       error: true,
  //       message: e.message,
  //     });
  //   }
  // };

  useEffect(() => {
    getListProduct();
   // getListBranch();
  }, []);

  useEffect(() => {
    const selectedLayanan = listProduct.find(
      (value) => value.id === parseInt(formDataRef.current.idKegiatan)
    );

    if (selectedLayanan) {
      setHarga(selectedLayanan.harga);
    } else {
      setHarga("");
    }
  }, [listProduct, formDataRef.current.idKegiatan]);

  const handleInputChange = async (e) => {
    const { name, value } = e.target;
    const validationError = await validateInput(name, value, userSchema);
    formDataRef.current[name] = value;

    if (name === "idKegiatan" && formDataRef.current.jumlahPemesanan !== "") {
      formDataRef.current.jumlahPemesanan = "";
      setTotal("");
    }

    if (name === "jumlahPemesanan" && value !== "") {
      setTotal(
        new Intl.NumberFormat("id-ID", {
          style: "currency",
          currency: "IDR",
        }).format(parseInt(formDataRef.current.jumlahPemesanan) * harga)
      );
    } else if (name === "jumlahPemesanan" && value === "") setTotal("");

    setErrors((prevErrors) => ({
      ...prevErrors,
      [validationError.name]: validationError.error,
    }));
  };

  const handleAdd = async (e) => {
    e.preventDefault();

    console.log(formDataRef.current);

    const validationErrors = await validateAllInputs(
      formDataRef.current,
      userSchema,
      setErrors
    );

    if (Object.values(validationErrors).every((error) => !error)) {
      setIsLoading(true);
      setIsError((prevError) => {
        return { ...prevError, error: false };
      });
      setErrors({});

      axios
        .post(API_LINK + "transaksis/saveTransaksi", formDataRef.current)
        .then((data) => {
          if (data === "ERROR") {
            setIsError((prevError) => {
              return {
                ...prevError,
                error: true,
                message: "Terjadi kesalahan: Gagal menyimpan transaksi.",
              };
            });
          } else {
            SweetAlert("Sukses", "Transaksi berhasil disimpan", "success");
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
            Tambah Transaksi
          </div>
          <div className="card-body p-4">
            <div className="row">
              <div className="col-lg-6">
                <DropDown
                  forInput="idKegiatan"
                  label="Nama Kegiatan"
                  arrData={dropdownProduct}
                  isRequired
                  value={formDataRef.current.idKegiatan}
                  onChange={handleInputChange}
                  errorMessage={errors.idKegiatan}
                />
              </div>
              <div className="col-lg-3">
                <Label
                  title="Harga Kegiatan"
                  data={new Intl.NumberFormat("id-ID", {
                    style: "currency",
                    currency: "IDR",
                  }).format(harga)}
                />
              </div>
              <div className="col-lg-3">
                <Label title="Total" data={total} />
              </div>
              <div className="col-lg-6">
                <Input
                  type="number"
                  forInput="jumlahPemesanan"
                  label="Jumlah"
                  value={formDataRef.current.jumlahPemesanan}
                  onChange={handleInputChange}
                  errorMessage={errors.jumlahPemesanan}
                />
              </div>
              {/* <div className="col-lg-6">
                <DropDown
                  forInput="transcBranchId"
                  label="Branch"
                  arrData={listBranch}
                  isRequired
                  value={formDataRef.current.transcBranchId}
                  onChange={handleInputChange}
                  errorMessage={errors.transcBranchId}
                />
              </div> */}
              <div className="col-lg-6">
                <Input
                  type="date"
                  forInput="tanggalPemesanan"
                  label="Tanggal"
                  value={formDataRef.current.tanggalPemesanan}
                  onChange={handleInputChange}
                  errorMessage={errors.tanggalPemesanan}
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
