package com.polytechnic.astra.ac.id.ippperformance.Fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreatePekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.Model.IntervalModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.MyListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPekerjaanFragment extends Fragment {

    private PekerjaanModel mPekerjaanModel;
    private EditText pkjNama, pkjKeterangan;
    private Spinner ktgId, itvId;
    private Button submitButton, batalButton;
    private MyListViewModel myPekerjaan;
    private Map<String, String> kategoriMap, intervalMap;
    private DashboardViewModel mDashboardViewModel;
    private TextView mTextViewNama;
    private ImageView mLogout;
    private LoginDatabaseHelper loginDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPekerjaanModel = new PekerjaanModel();
        myPekerjaan = new ViewModelProvider(this).get(MyListViewModel.class);
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());

        if (getArguments() != null && getArguments().containsKey("detailId")) {
            String crimeId = (String) getArguments().getSerializable("detailId");
            myPekerjaan.getPekerjaanById(crimeId);
        }
    }

    public static EditPekerjaanFragment newInstance(String crimeId) {
        Bundle args = new Bundle();
        args.putString("detailId", crimeId);
        EditPekerjaanFragment fragment = new EditPekerjaanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_pekerjaan, container, false);

        pkjNama = v.findViewById(R.id.txtPekerjaan);
        pkjKeterangan = v.findViewById(R.id.txtKeterangan);
        ktgId = v.findViewById(R.id.cmbKategori);
        itvId = v.findViewById(R.id.cmbSemester);
        submitButton = v.findViewById(R.id.btnSimpan);
        batalButton = v.findViewById(R.id.btnBatal);
        mTextViewNama = v.findViewById(R.id.textViewNama);
        kategoriMap = new HashMap<>();
        intervalMap = new HashMap<>();
        CircularImageView logoImageView = v.findViewById(R.id.logoImageView);
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
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(EditPekerjaanFragment.this)
                            .load(ApiUtils.API_URL+ "SSO/uploads/foto/" + userModel.getKry_foto())
                            .into(logoImageView);
                    mTextViewNama.setText(userModel.getKry_nama());
                }
            }
        });

        myPekerjaan.getCreatePekerjaan().observe(getViewLifecycleOwner(), new Observer<List<CreatePekerjaanVO>>() {
            @Override
            public void onChanged(List<CreatePekerjaanVO> createPekerjaanVOList) {
                if (createPekerjaanVOList != null && !createPekerjaanVOList.isEmpty()) {
                    CreatePekerjaanVO createPekerjaan = createPekerjaanVOList.get(0);

                    List<String> kategoriList = new ArrayList<>();
                    //kategoriList.add("-- Pilih Kategori --");
                    for (KategoriModel kategori : createPekerjaan.getKategori()) {
                        kategoriMap.put(kategori.getKtg_nama(), kategori.getKtg_id());
                        kategoriList.add(kategori.getKtg_nama());
                    }
                    ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, kategoriList);
                    kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ktgId.setAdapter(kategoriAdapter);

                    List<String> pekerjaanList = new ArrayList<>();
                    //pekerjaanList.add("-- Pilih Semester - Tahun Ajaran --");
                    for (IntervalModel interval : createPekerjaan.getInterval()) {
                        String displayText = interval.getItv_semester() + " - " + interval.getItv_tahun_ajaran();
                        intervalMap.put(displayText, interval.getItv_id());
                        pekerjaanList.add(displayText);
                    }
                    ArrayAdapter<String> pekerjaanAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pekerjaanList);
                    pekerjaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    itvId.setAdapter(pekerjaanAdapter);
                }
            }
        });

        String id = getArguments() != null ? getArguments().getString("detailId") : null;
        if (id != null) {
            myPekerjaan.getPekerjaanById(id).observe(getViewLifecycleOwner(), new Observer<List<PekerjaanModel>>() {
                @Override
                public void onChanged(List<PekerjaanModel> detail) {
                    if (detail != null) {
                        updateUI(detail.get(0));
                    }
                }
            });
        }

        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PekerjaanListFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });

        return v;
    }

    private void validateAndSubmit() {
        String nama = pkjNama.getText().toString().trim();
        String keterangan = pkjKeterangan.getText().toString().trim();
        String kategoriNama = ktgId.getSelectedItem().toString();
        String interval = itvId.getSelectedItem().toString();

        if (nama.isEmpty()) {
            pkjNama.setError("Nama pekerjaan tidak boleh kosong");
            pkjNama.requestFocus();
        } else if (keterangan.isEmpty()) {
            pkjKeterangan.setError("Keterangan tidak boleh kosong");
            pkjKeterangan.requestFocus();
        } else if (kategoriNama.equals("-- Pilih Kategori --")) {
            //ktgId.setError("Silakan pilih kategori");
            //Toast.makeText(getContext(), "Silakan pilih kategori", Toast.LENGTH_SHORT).show();
        } else if (interval.equals("-- Pilih Semester - Tahun Ajaran --")) {
            //Toast.makeText(getContext(), "Silakan pilih semester", Toast.LENGTH_SHORT).show();
        } else {
            String kategoriId = kategoriMap.get(kategoriNama);
            String pekerjaanId = intervalMap.get(interval);
            AddPekerjaanVO request = new AddPekerjaanVO(
                    null,
                    nama,
                    kategoriId,
                    pekerjaanId,
                    keterangan
            );
            String id = getArguments() != null ? getArguments().getString("detailId") : null;
            if (id != null && !id.isEmpty()) {
                request.setPkj_id(id);
                myPekerjaan.update(request).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String result) {
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        navigateToMyListFragment();
                    }
                });
            } else {
                myPekerjaan.insert(request).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String result) {
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        navigateToMyListFragment();
                    }
                });
            }
        }
    }

    private void navigateToMyListFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PekerjaanListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void updateUI(PekerjaanModel pekerjaanModel) {
        pkjNama.setText(pekerjaanModel.getPkjNama());
        pkjKeterangan.setText(pekerjaanModel.getPkjKeterangan());

        // Cari index untuk kategori dan interval
        String kategoriNama = null;

        for (Map.Entry<String, String> entry : kategoriMap.entrySet()) {
            if (entry.getValue().equals(pekerjaanModel.getKtgId())) {

                kategoriNama = entry.getKey();
                Log.e("EDIT", kategoriNama);
                break;
            }
        }
        if (kategoriNama != null) {
            for (int i = 0; i < ktgId.getCount(); i++) {
                if (ktgId.getItemAtPosition(i).toString().equals(kategoriNama)) {
                    ktgId.setSelection(i);
                    break;
                }
            }
        }
        String intervalNama = null;
        for (Map.Entry<String, String> entry : intervalMap.entrySet()) {
            if (entry.getValue().equals(pekerjaanModel.getItvId())) {
                intervalNama = entry.getKey();
                Log.e("EDIT", intervalNama);
                break;
            }
        }
        if (intervalNama != null) {
            for (int i = 0; i < itvId.getCount(); i++) {
                if (itvId.getItemAtPosition(i).toString().equals(intervalNama)) {
                    itvId.setSelection(i);
                    break;
                }
            }
        }
    }
}
