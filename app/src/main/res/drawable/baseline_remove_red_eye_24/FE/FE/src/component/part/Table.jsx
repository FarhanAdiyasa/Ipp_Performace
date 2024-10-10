import React from "react";
import DataTable from "react-data-table-component";
import Icon from "./Icon";
import Button from "./Button";

export default function Table({
  data,
  onToggle = () => {},
  onCancel = () => {},
  onDelete = () => {},
  onDetail = () => {},
  onEdit = () => {},
  onApprove = () => {},
  onReject = () => {},
  onSent = () => {},
}) {
  if (data.length === 0) return <div>No data available</div>;

  const columns = Object.keys(data[0])
    .map((key) => {
      if (key === "Key") return null;
      if (key === "Aksi") {
        return {
          name: key,
          selector: (row) => row[key],
          cell: (row) => generateActionButton(row[key], row.Key, row.Status),
          center: true,
        };
      }
      if (key === "Transaksi") {
        return {
          name: key,
          selector: (row) => row[key],
          cell: (row) => (
            <Button
              classType="primary btn-sm"
              label="Show"
              onClick={() => onEdit("transaksi", row.Key)}
            />
          ),
          center: true,
        };
      }
      return {
        name: key,
        selector: (row) => row[key],
        sortable: true,
        center: key !== "Description" && key !== "Product Name",
      };
    })
    .filter((column) => column !== null);

  function generateActionButton(actions, id, status) {
    return actions.map((action) => {
      switch (action) {
        case "Toggle":
          return status === "Aktif" ? (
            <Icon
              key={id + action}
              name="toggle-on"
              type="Bold"
              cssClass="btn px-1 py-0 text-primary"
              title="Nonaktifkan"
              onClick={() => onToggle(id)}
            />
          ) : (
            <Icon
              key={id + action}
              name="toggle-off"
              type="Bold"
              cssClass="btn px-1 py-0 text-secondary"
              title="Aktifkan"
              onClick={() => onToggle(id)}
            />
          );
        case "Cancel":
          return (
            <Icon
              key={id + action}
              name="delete-document"
              type="Bold"
              cssClass="btn px-1 py-0 text-danger"
              title="Batalkan"
              onClick={onCancel}
            />
          );
        case "Delete":
          return (
            <Icon
              key={id + action}
              name="trash"
              type="Bold"
              cssClass="btn px-1 py-0 text-danger"
              title="Hapus"
              onClick={onDelete}
            />
          );
        case "Detail":
          return (
            <Icon
              key={id + action}
              name="overview"
              type="Bold"
              cssClass="btn px-1 py-0 text-primary"
              title="Lihat Detail"
              onClick={() => onDetail("detail", id)}
            />
          );
        case "Edit":
          return (
            <Icon
              key={id + action}
              name="edit"
              type="Bold"
              cssClass="btn px-1 py-0 text-primary"
              title="Ubah"
              onClick={() => onEdit("edit", id)}
            />
          );
        case "Approve":
          return (
            <Icon
              key={id + action}
              name="check"
              type="Bold"
              cssClass="btn px-1 py-0 text-success"
              title="Setujui Pengajuan"
              onClick={onApprove}
            />
          );
        case "Reject":
          return (
            <Icon
              key={id + action}
              name="cross"
              type="Bold"
              cssClass="btn px-1 py-0 text-danger"
              title="Tolak Pengajuan"
              onClick={onReject}
            />
          );
        case "Sent":
          return (
            <Icon
              key={id + action}
              name="paper-plane"
              type="Bold"
              cssClass="btn px-1 py-0 text-primary"
              title="Kirim Pengajuan"
              onClick={onSent}
            />
          );
        default:
          return null;
      }
    });
  }

  return (
    <div className="flex-fill">
      <DataTable
        columns={columns}
        data={data}
        pagination
        paginationPerPage={5}
        paginationRowsPerPageOptions={[5, 10, 15]}
        highlightOnHover
        striped
        noHeader
      />
    </div>
  );
}
