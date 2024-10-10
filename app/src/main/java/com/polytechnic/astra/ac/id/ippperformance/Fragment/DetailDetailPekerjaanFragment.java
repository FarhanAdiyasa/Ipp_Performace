package com.polytechnic.astra.ac.id.ippperformance.Fragment;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ViewDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DetailPekerjaanListViewModel;

public class DetailDetailPekerjaanFragment extends Fragment {

    private EditText tvKategori, tvPekerjaan, tvNama, tvTanggal, tvRencanaJamAwal, tvRencanaJamAkhir,
            tvAktualJamAwal, tvAktualJamAkhir;
    private ImageView mBuktiFoto;
    private TextView mTextViewNama;
    private ImageView mLogout;
    private DetailPekerjaanListViewModel myDetail;
    private DashboardViewModel mDashboardViewModel;
    private LoginDatabaseHelper loginDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDetail = new ViewModelProvider(this).get(DetailPekerjaanListViewModel.class);
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());
    }

    public static DetailDetailPekerjaanFragment newInstance(String crimeId) {
        Bundle args = new Bundle();
        args.putString("detailId", crimeId); // Mengirim ID ke FragmentDetail
        DetailDetailPekerjaanFragment fragment = new DetailDetailPekerjaanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_detail_pekerjaan_fragment, container, false);

        tvKategori = view.findViewById(R.id.tv_kategori_value);
        tvPekerjaan = view.findViewById(R.id.tv_pekerjaan_value);
        tvNama = view.findViewById(R.id.tv_nama_value);
        tvTanggal = view.findViewById(R.id.tv_tanggal_value);
        tvRencanaJamAwal = view.findViewById(R.id.tv_rencana_jam_awal_value);
        tvRencanaJamAkhir = view.findViewById(R.id.tv_rencana_jam_akhir_value);
        tvAktualJamAwal = view.findViewById(R.id.tv_aktual_jam_awal_value);
        tvAktualJamAkhir = view.findViewById(R.id.tv_aktual_jam_akhir_value);
        mBuktiFoto = view.findViewById(R.id.iv_bukti_foto);
        mTextViewNama = view.findViewById(R.id.textViewNama);
        mLogout = view.findViewById(R.id.btnLogout);
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
        CircularImageView logoImageView = view.findViewById(R.id.logoImageView);
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(DetailDetailPekerjaanFragment.this)
                            .load(ApiUtils.API_URL+ "SSO/uploads/foto/" + userModel.getKry_foto())
                            .into(logoImageView);
                    mTextViewNama.setText(userModel.getKry_nama());
                }
            }
        });
        tvKategori.setEnabled(false);
        tvPekerjaan.setEnabled(false);
        tvNama.setEnabled(false);
        tvTanggal.setEnabled(false);
        tvRencanaJamAwal.setEnabled(false);
        tvRencanaJamAkhir.setEnabled(false);
        tvAktualJamAwal.setEnabled(false);
        tvAktualJamAkhir.setEnabled(false);

        // Mendapatkan ID yang dikirim dari PekerjaanListFragment
        String id = getArguments() != null ? getArguments().getString("detailId") : null;
        // Observing data dari ViewModel berdasarkan ID
        if (id != null) {
            myDetail.getDetailDetail(id).observe(getViewLifecycleOwner(), new Observer<ViewDetailPekerjaanVO>() {
                @Override
                public void onChanged(ViewDetailPekerjaanVO detail) {
                    if (detail != null) {
                        tvKategori.setText(" "+detail.getKtgNama());
                        tvPekerjaan.setText("  "+detail.getPkjNama());
                        tvNama.setText("  "+detail.getDtpNama());
                        tvTanggal.setText("  "+detail.getDtpTanggal());
                        tvRencanaJamAwal.setText("  "+detail.getDtpRencanaJamAwal());
                        tvRencanaJamAkhir.setText("  "+detail.getDtpRencanaJamAkhir());
                        tvAktualJamAwal.setText("  "+detail.getDtpAktualJamAwal());
                        tvAktualJamAkhir.setText("  "+detail.getDtpAktualJamAkhir());
                        if(detail.getDtpBuktiFoto() != null){
                            Glide.with(DetailDetailPekerjaanFragment.this)
                                    .load(ApiUtils.API_URL+"IPP/uploads/foto/" + detail.getDtpBuktiFoto())
                                    .into(mBuktiFoto);
                        }
                    }
                }
            });
        }

        return view;
    }
}