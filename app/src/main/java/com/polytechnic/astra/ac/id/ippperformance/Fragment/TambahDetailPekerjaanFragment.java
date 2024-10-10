package com.polytechnic.astra.ac.id.ippperformance.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreateDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.Model.DetailPekerjaan;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DetailPekerjaanListViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahDetailPekerjaanFragment extends Fragment {
    private DetailPekerjaan mDetailPekerjaan;
    private EditText dtpNama, dtpTanggal, dtpRencanaJamAwal, dtpRencanaJamAkhir;
    private ImageView mBtnTanggal,mBtnWaktuAwal,mBtnWaktuAkhir;
    private Spinner ktgId, pkjId;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_GALLERY = 3;
    private Button submitButton, cancelButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private TextView mTextViewNama;
    private DetailPekerjaanListViewModel myDetail;
    private Map<String, String> kategoriMap, pekerjaanMap;
    private Uri mPhotoUri;
    private File mPhotoFile;
    private ArrayAdapter<String> pekerjaanAdapter;
    private DashboardViewModel mDashboardViewModel;
    private ImageView mLogout;
    private LoginDatabaseHelper loginDatabaseHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDetail = new ViewModelProvider(this).get(DetailPekerjaanListViewModel.class);
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        mDetailPekerjaan = new DetailPekerjaan();
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());

        // Request permission for camera and storage if not granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tambah_detailpekerjaan, container, false);

        dtpNama = v.findViewById(R.id.txtDetailPekerjaan);
        dtpTanggal = v.findViewById(R.id.txtTanggalPekerjaan);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set EditText to current date
        dtpTanggal.setText(String.format("%d-%02d-%02d", year, month + 1, day));
        dtpRencanaJamAwal = v.findViewById(R.id.txtWaktu09Mulai);
        dtpRencanaJamAkhir = v.findViewById(R.id.txtWaktuSelesai);
        ktgId = v.findViewById(R.id.cmbKategori);
        pkjId = v.findViewById(R.id.cmbPekerjaan);
        submitButton = v.findViewById(R.id.btnSimpan);
        cancelButton = v.findViewById(R.id.btnBatal);
        mPhotoButton = v.findViewById(R.id.crime_camera);
        mPhotoView = v.findViewById(R.id.crime_photo);
        mTextViewNama = v.findViewById(R.id.textViewNama);
        mBtnTanggal = v.findViewById(R.id.imgTanggal);
        mBtnWaktuAwal = v.findViewById(R.id.imgWaktuAwal);
        mBtnWaktuAkhir = v.findViewById(R.id.imgWaktuAkhir);

        kategoriMap = new HashMap<>();
        pekerjaanMap = new HashMap<>();
        mLogout = v.findViewById(R.id.btnLogout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan dialog konfirmasi logout
                new AlertDialog.Builder(getContext())
                        .setTitle("Konfirmasi Logout")
                        .setMessage("Apakah Anda yakin ingin logout? Aplikasi akan ditutup dan Anda perlu membukanya kembali untuk login.")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Clear all login data from the database
                                loginDatabaseHelper.deleteAllLogins();

                                Intent restartIntent = getActivity().getPackageManager()
                                        .getLaunchIntentForPackage(getActivity().getPackageName());
                                if (restartIntent != null) {
                                    restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    int pendingIntentId = 123456;
                                    PendingIntent pendingIntent = PendingIntent.getActivity(
                                            getContext(), pendingIntentId, restartIntent,
                                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE); // Specify mutability flag
                                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);
                                    System.exit(0);
                                }
                            }
                        })
                        .setNegativeButton("Batal", null) // Button untuk membatalkan logout
                        .show();
            }
        });
        CircularImageView logoImageView = v.findViewById(R.id.logoImageView);
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(TambahDetailPekerjaanFragment.this)
                            .load(ApiUtils.API_URL+"SSO/uploads/foto/"+ userModel.getKry_foto())
                            .into(logoImageView);
                    mTextViewNama.setText(userModel.getKry_nama());
                }

            }
        });
        // Initialize photo URI
        mPhotoUri = Uri.fromFile(new File(requireActivity().getExternalFilesDir(null), "IMG_" + System.currentTimeMillis() + ".jpg"));

        // Capture image button click listener
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });

        // Observe data from ViewModel
        myDetail.getCreateDetail().observe(getViewLifecycleOwner(), new Observer<List<CreateDetailPekerjaanVO>>() {
            @Override
            public void onChanged(List<CreateDetailPekerjaanVO> createDetailPekerjaanVOList) {
                if (createDetailPekerjaanVOList != null && !createDetailPekerjaanVOList.isEmpty()) {
                    CreateDetailPekerjaanVO createDetail = createDetailPekerjaanVOList.get(0);

                    // Populate kategori spinner
                    List<String> kategoriList = new ArrayList<>();
                    //kategoriList.add("-- Pilih Kategori --");
                    for (KategoriModel kategori : createDetail.getKategori()) {
                        kategoriMap.put(kategori.getKtg_nama(), kategori.getKtg_id());
                        kategoriList.add(kategori.getKtg_nama());
                    }
                    ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, kategoriList);
                    kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ktgId.setAdapter(kategoriAdapter);

                    List<String> pekerjaanList = new ArrayList<>();
                    //pekerjaanList.add("-- Pilih Pekerjaan --");
                    for (PekerjaanModel pekerjaan : createDetail.getPekerjaan()) {
                        pekerjaanMap.put(pekerjaan.getPkjNama(), pekerjaan.getPkjId());
                        pekerjaanList.add(pekerjaan.getPkjNama());
                    }
                    pekerjaanAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pekerjaanList);
                    pekerjaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pkjId.setAdapter(pekerjaanAdapter);
                }
            }
        });
        mBtnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        mBtnWaktuAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(dtpRencanaJamAwal);
            }
        });
        mBtnWaktuAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(dtpRencanaJamAkhir);
            }
        });

        // Set kategori spinner item selected listener
        ktgId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKategori = (String) parent.getItemAtPosition(position);

                if (!selectedKategori.equals("-- Pilih Kategori --")) {
                    String kategoriNama = ktgId.getSelectedItem().toString();
                    String kategoriId = kategoriMap.get(kategoriNama);
                    Log.e("KTG", kategoriId);
                    if (kategoriId != null) {
                        myDetail.getPkjByKategori(kategoriId).observe(getViewLifecycleOwner(), new Observer<List<PekerjaanModel>>() {
                            @Override
                            public void onChanged(List<PekerjaanModel> pekerjaanList) {
                                if (pekerjaanList != null && !pekerjaanList.isEmpty()) {
                                    pekerjaanMap.clear();
                                    List<String> pekerjaanNameList = new ArrayList<>();
                                    //pekerjaanNameList.add("-- Pilih Pekerjaan --");
                                    for (PekerjaanModel pekerjaan : pekerjaanList) {
                                        pekerjaanMap.put(pekerjaan.getPkjNama(), pekerjaan.getPkjId());
                                        pekerjaanNameList.add(pekerjaan.getPkjNama());
                                    }
                                    ArrayAdapter<String> pekerjaanAdapter3 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pekerjaanNameList);
                                    pekerjaanAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    pkjId.setAdapter(pekerjaanAdapter3);
                                } else {
                                    pkjId.setAdapter(null);
                                    Toast.makeText(requireContext(), "No pekerjaan available for selected kategori", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    pkjId.setAdapter(pekerjaanAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pkjId.setAdapter(pekerjaanAdapter);
            }
        });

        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return v;
    }
    private void validateAndSubmit() {
        String nama = dtpNama.getText().toString().trim();
        String tanggal = dtpTanggal.getText().toString().trim();
        String jamAwal = dtpRencanaJamAwal.getText().toString().trim();
        String jamAkhir = dtpRencanaJamAkhir.getText().toString().trim();
        String kategoriNama = ktgId.getSelectedItem().toString();
        String pekerjaanNama = pkjId.getSelectedItem().toString();
        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        String datePattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        dateFormat.setLenient(false); // Set lenient to false to ensure strict parsing

        if (nama.isEmpty()) {
            dtpNama.setError("Nama detail pekerjaan tidak boleh kosong");
            dtpNama.requestFocus();
        } else if (tanggal.isEmpty()) {
            dtpTanggal.setError("Tanggal tidak boleh kosong");
            dtpTanggal.requestFocus();
        } else {
            try {
                dateFormat.parse(tanggal);
            } catch (ParseException e) {
                dtpTanggal.setError("Format tanggal tidak valid. Gunakan format yyyy-MM-dd.");
                dtpTanggal.requestFocus();
                return; // Exit the method early if the date format is incorrect
            }

            if (jamAkhir.isEmpty()) {
                dtpRencanaJamAkhir.setError("Jam akhir tidak boleh kosong");
                dtpRencanaJamAkhir.requestFocus();
            } else if (jamAwal.isEmpty()) {
                dtpRencanaJamAwal.setError("Jam awal tidak boleh kosong");
                dtpRencanaJamAwal.requestFocus();
            } else if (!jamAwal.matches(timePattern)) {
                dtpRencanaJamAwal.setError("Format jam awal tidak valid. Gunakan format HH:mm.");
                dtpRencanaJamAwal.requestFocus();
            } else if (!jamAkhir.matches(timePattern)) {
                dtpRencanaJamAkhir.setError("Format jam akhir tidak valid. Gunakan format HH:mm.");
                dtpRencanaJamAkhir.requestFocus();
            } else if (mPhotoFile == null || !mPhotoFile.exists()) {
                // Photo not selected or file does not exist
                Toast.makeText(requireContext(), "Foto harus diinput", Toast.LENGTH_SHORT).show();
                mPhotoButton.requestFocus(); // Optional: Focus on the photo button to prompt user action
            } else {
                String kategoriId = kategoriMap.get(kategoriNama);
                String pekerjaanId = pekerjaanMap.get(pekerjaanNama);
                AddDetailPekerjaanVO request = new AddDetailPekerjaanVO(
                        nama,
                        kategoriId,
                        pekerjaanId,
                        tanggal,
                        jamAwal,
                        jamAkhir,
                        mPhotoUri.getPath()
                );
                // Call ViewModel method to insert data
                myDetail.insert(request, encodeFileToBase64(mPhotoFile)).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String result) {
                        if(result.equals("error: Wrong")){
                            Toast.makeText(requireContext(), "Waktu Selesai harus lebih besar dari Waktu Mulai.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }
        }
    }



    // Method to show dialog for choosing image capture method
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Image Source");
        String[] options = {"Take Photo", "Choose from Gallery"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Take Photo
                    takePhoto();
                    break;
                case 1:
                    // Choose from Gallery
                    chooseFromGallery();
                    break;
            }
        });
        builder.show();
    }

    // Method to handle taking a photo
    private void takePhoto() {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureImage.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create a new File object to store the image
            mPhotoFile = new File(requireActivity().getExternalFilesDir(null), "IMG_" + System.currentTimeMillis() + ".jpg");

            // Get a URI for this file using FileProvider
            mPhotoUri = FileProvider.getUriForFile(requireContext(),
                    "com.polytechnic.astra.ac.id.ippperformance.fileprovider",
                    mPhotoFile);

            // Add the URI as an extra to the captureImage intent
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);

            // Grant permissions to the camera app to write to this URI
            List<ResolveInfo> cameraActivities = requireActivity().getPackageManager()
                    .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo activity : cameraActivities) {
                requireActivity().grantUriPermission(activity.activityInfo.packageName,
                        mPhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            // Start the camera activity
            startActivityForResult(captureImage, REQUEST_PHOTO);
        }
    }



    // Method to handle choosing from gallery
    private void chooseFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    // Handle activity results
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_PHOTO:
                requireActivity().revokeUriPermission(mPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
                break;
            case REQUEST_GALLERY:
                if (data != null) {
                    mPhotoUri = data.getData();
                    try {
                        mPhotoFile = getFileFromUri(mPhotoUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updatePhotoView();
                }
                break;
        }
    }
    private File getFileFromUri(Uri uri) throws IOException {
        File file = new File(requireContext().getCacheDir(), "temp_image");
        try (FileInputStream inputStream = (FileInputStream) requireContext().getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return file;
    }

    private String encodeFileToBase64(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Update photo view
    private void updatePhotoView() {
        if (mPhotoUri != null) {
            mPhotoView.setImageURI(mPhotoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHOTO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, do initialization again
                initializeViews();
            } else {
                Toast.makeText(requireContext(), "Permissions required to use camera and storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Initialize views after permissions are granted
    private void initializeViews() {
        // Initialize views here
    }
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dtpTanggal.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(EditText timeField) {
        // Mendapatkan waktu saat ini
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Membuat instance TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minuteOfHour) -> {
                    // Menggunakan waktu yang dipilih
                    String time;
                    if (minuteOfHour<10){
                         time = hourOfDay + ":0" + minuteOfHour;
                    }else {
                        time = hourOfDay + ":" + minuteOfHour;
                    }

                    timeField.setText(time);
                },
                hour,
                minute,
                true
        );

        // Menampilkan TimePickerDialog
        timePickerDialog.show();
    }

}
