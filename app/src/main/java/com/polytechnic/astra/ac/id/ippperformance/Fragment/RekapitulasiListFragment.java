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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiViewVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.MainActivity;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.LoginViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.MyListViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class RekapitulasiListFragment extends Fragment {

    private MyListViewModel mRekapitulasiViewModel;
    private RecyclerView mRecyclerView;
    private RekapitulasiAdapter mAdapter;
    private DashboardViewModel mDashboardViewModel;
    private TextView mTextViewNama;
    private LoginViewModel myPekerjaan;
    private ImageView mLogout;

    private LoginDatabaseHelper loginDatabaseHelper;
    private TextView mDataNotFoundTextView; // Tambahkan TextView untuk pesan "Data Not Found"

    public static RekapitulasiListFragment newInstance() {
        return new RekapitulasiListFragment();
    }

    private class RekapitulasiHolder extends RecyclerView.ViewHolder {
        private TextView mKategoriTextView;
        private TextView mPekerjaanTextView;
        private TextView mPersentaseTextView;
        private TextView mKeteranganTextView;
        public RekapitulasiHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recylerview_rekapitulasi, parent, false));
            mKategoriTextView = itemView.findViewById(R.id.tvKategori);
            mPekerjaanTextView = itemView.findViewById(R.id.tvPekerjaan);

            mPersentaseTextView = itemView.findViewById(R.id.tvPresentase);
            mKeteranganTextView = itemView.findViewById(R.id.tvKeterangan);
        }

        public void bind(RekapitulasiViewVO data) {
            mKategoriTextView.setText(data.getKategori());
            mPekerjaanTextView.setText("Pekerjaan : " + data.getPekerjaan());
            // Mengambil nilai persentase dari data dan mengubahnya menjadi double
            String persentaseString = data.getPersentase(); // Misalnya "0.44"
            try {
                double persentaseValue = Double.parseDouble(persentaseString);

                // Mengubah nilai persentase menjadi format dengan tanda persen
                String formattedPersentase = String.format(Locale.getDefault(), "%.0f%%", persentaseValue * 100);

                // Menampilkan hasil format pada TextView
                mPersentaseTextView.setText("Persentase: " + formattedPersentase);
            } catch (NumberFormatException e) {
                // Menangani kemungkinan error jika format string tidak valid
                mPersentaseTextView.setText("Persentase: Invalid format");
            }
            mKeteranganTextView.setText("Keterangan : "  +data.getKeterangan());

        }
    }

    private class RekapitulasiAdapter extends RecyclerView.Adapter<RekapitulasiHolder> {
        private List<RekapitulasiViewVO> mRekapitulasiList;

        public RekapitulasiAdapter(List<RekapitulasiViewVO> rekapitulasiList) {
            mRekapitulasiList = rekapitulasiList;
        }

        @NonNull
        @Override
        public RekapitulasiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RekapitulasiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RekapitulasiHolder holder, int position) {
            RekapitulasiViewVO data = mRekapitulasiList.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return mRekapitulasiList.size();
        }

        public void setRekapitulasiList(List<RekapitulasiViewVO> rekapitulasiList) {
            mRekapitulasiList = rekapitulasiList;
            notifyDataSetChanged();
        }
    }

    private void updateUI(RekapitulasiVO rekapitulasiVO) {
        if (rekapitulasiVO != null) {
            List<RekapitulasiViewVO> rekapitulasiList = rekapitulasiVO.getPekerjaan();
            if (rekapitulasiList == null || rekapitulasiList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                mDataNotFoundTextView.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mDataNotFoundTextView.setVisibility(View.GONE);
                mAdapter.setRekapitulasiList(rekapitulasiList);
            }
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mDataNotFoundTextView.setVisibility(View.VISIBLE);
           }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRekapitulasiViewModel = new ViewModelProvider(this).get(MyListViewModel.class);
        mAdapter = new RekapitulasiAdapter(Collections.emptyList());
        myPekerjaan = new ViewModelProvider(this).get(LoginViewModel.class);
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rekapitulasi, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mDataNotFoundTextView = view.findViewById(R.id.dataNotFoundTextView); // Inisialisasi TextView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mTextViewNama = view.findViewById(R.id.textViewNama);

        CircularImageView logoImageView = view.findViewById(R.id.logoImageView);
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(RekapitulasiListFragment.this)
                            .load(ApiUtils.API_URL+ "SSO/uploads/foto/" + userModel.getKry_foto())
                            .into(logoImageView);
                    mTextViewNama.setText(userModel.getKry_nama());
                }
            }
        });
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

                                // Get the ViewModel
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



        mRekapitulasiViewModel.getRekapitulasi().observe(getViewLifecycleOwner(), new Observer<RekapitulasiVO>() {
            @Override
            public void onChanged(@Nullable RekapitulasiVO rekapitulasiVO) {
                updateUI(rekapitulasiVO);
            }
        });

        return view;
    }
}
