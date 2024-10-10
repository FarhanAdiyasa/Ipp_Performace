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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ViewDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DetailPekerjaanListViewModel;

import java.util.Collections;
import java.util.List;
public class DetailPekerjaanListFragment extends Fragment{
    //private MyListViewModel mMyListViewModel;
    private DetailPekerjaanListViewModel myDetail;
    private RecyclerView mRecyclerView;
    private MyModelAdapter mAdapter;
    private DashboardViewModel mDashboardViewModel;
    private TextView mTextViewNama;
    private ImageView mLogout;
    private LoginDatabaseHelper loginDatabaseHelper;
    private TextView mDataNotFoundTextView;
    private void updateUI(List<ViewDetailPekerjaanVO> datas) {
        if (datas == null || datas.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mDataNotFoundTextView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mDataNotFoundTextView.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new MyModelAdapter(datas);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.mPekerjaanVOList = datas;
                mAdapter.notifyDataSetChanged();
            }
        }
    }
    public static DetailPekerjaanListFragment newInstance(String crimeId) {
        Bundle args = new Bundle();
        args.putString("detailId", crimeId);
        DetailPekerjaanListFragment fragment = new DetailPekerjaanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myDetail = new ViewModelProvider(this).get(DetailPekerjaanListViewModel.class);
        myDetail = new ViewModelProvider(this).get(DetailPekerjaanListViewModel.class);
        mAdapter = new MyModelAdapter(Collections.emptyList());
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());

    }
    private void showDeleteConfirmationDialog(ViewDetailPekerjaanVO data) {
        AlertDialog konfirmasiHapus = new AlertDialog.Builder(getContext())
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus item ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Hapus item setelah konfirmasi
                        myDetail.hapus(data.getDtpId()).observe(getViewLifecycleOwner(), new Observer<String>() {
                            @Override
                            public void onChanged(String result) {
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static DetailPekerjaanListFragment newInstance() {
        return new DetailPekerjaanListFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_pekerjaan_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerViewDetail);
        mDataNotFoundTextView = view.findViewById(R.id.dataNotFoundTextView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnTambah = view.findViewById(R.id.btnTambahDetail);

        CircularImageView logoImageView = view.findViewById(R.id.logoImageView);
        mTextViewNama = view.findViewById(R.id.textViewNama);
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(DetailPekerjaanListFragment.this)
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
        String id = getArguments() != null ? getArguments().getString("detailId") : null;
        if (id != null && !id.isEmpty()) {
            myDetail.getDetailByPkjId(id).observe(getViewLifecycleOwner(), new Observer<List<ViewDetailPekerjaanVO>>() {
                @Override
                public void onChanged(List<ViewDetailPekerjaanVO> pekerjaanVOList) {
                    updateUI(pekerjaanVOList);
                    Log.d("TEST", "Called: " + pekerjaanVOList.size());
                }
            });
            btnTambah.setVisibility(View.GONE);
        }else{
            myDetail.getListModel().observe(getViewLifecycleOwner(), new Observer<List<ViewDetailPekerjaanVO>>() {
                @Override
                public void onChanged(List<ViewDetailPekerjaanVO> pekerjaanVOList) {
                    updateUI(pekerjaanVOList);
                    Log.d("TEST", "Called: " + pekerjaanVOList.size());
                }
            });
        }


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new TambahDetailPekerjaanFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }
    private class MyModelHolder extends RecyclerView.ViewHolder {

        private TextView tvNo, tvDetail, tvPekerjaan, tvKategori, tvSemester, tvTahunAjaran, tvStatus;
        private ImageView btnAction1, btnAction2, btnAction3, btnAction4;

        public MyModelHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recylerview_detailpekerjaanlist, parent, false));
            //tvNo = itemView.findViewById(R.id.tv_no);
            tvDetail = itemView.findViewById(R.id.txtNamaDetail);
            tvPekerjaan = itemView.findViewById(R.id.txtNamaPekerjaan);
            tvKategori = itemView.findViewById(R.id.txtKategori);
            tvSemester = itemView.findViewById(R.id.txtSemester);
            //tvTahunAjaran = itemView.findViewById(R.id.tv_tahun_ajaran);
            //tvStatus = itemView.findViewById(R.id.tv_status);
            btnAction1 = itemView.findViewById(R.id.btnPostDetail);
            btnAction2 = itemView.findViewById(R.id.btnHapusDetail);
            btnAction3 = itemView.findViewById(R.id.btnSelesaiDetail);
            btnAction4 = itemView.findViewById(R.id.btnInfoDetail);
        }

        public void bind(ViewDetailPekerjaanVO data, int position) {
            if (data != null) {
//                tvNo.setText(String.valueOf(position + 1));
                tvDetail.setText(data.getDtpNama());
                tvPekerjaan.setText("Nama Pekerjaan: "+data.getPkjNama());
                tvKategori.setText("Nama Kategori: "+data.getKtgNama());
                tvSemester.setText("Semeseter: "+ data.getItvSemester() + " - " + data.getItvTahunAjaran());
//                tvStatus.setText(data.getDtpStatus());
            }
            String id = getArguments() != null ? getArguments().getString("detailId") : null;
            if (id != null && !id.isEmpty()) {
                btnAction3.setVisibility(View.GONE);
                btnAction2.setVisibility(View.GONE);
                btnAction1.setVisibility(View.GONE);
                btnAction4.setVisibility(View.GONE);


            }else{
                if ("Progres".equals(data.getDtpStatus())) {
                    btnAction3.setVisibility(View.VISIBLE);
                    btnAction2.setVisibility(View.GONE);
                    btnAction1.setVisibility(View.GONE);
                    btnAction4.setVisibility(View.GONE);
                } else if("Draft".equals(data.getDtpStatus())){
                    btnAction3.setVisibility(View.GONE);
                    btnAction2.setVisibility(View.VISIBLE);
                    btnAction1.setVisibility(View.VISIBLE);
                    btnAction4.setVisibility(View.GONE);
                }else{
                    btnAction3.setVisibility(View.GONE);
                    btnAction2.setVisibility(View.GONE);
                    btnAction1.setVisibility(View.GONE);
                    btnAction4.setVisibility(View.VISIBLE);
                }
            }

            btnAction1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDetail.post(data.getDtpId()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String result) {
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }
            });
            btnAction2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog(data);
                }
            });
            btnAction3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDetail.selesai(data.getDtpId()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String result) {
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, DetailPekerjaanListFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }
            });
            btnAction4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("detailId", data.getDtpId()); // Mengirim ID ke FragmentDetail

                    DetailDetailPekerjaanFragment fragmentDetail = new DetailDetailPekerjaanFragment();
                    fragmentDetail.setArguments(bundle);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragmentDetail)
                            .addToBackStack(null) // Add this line to add the transaction to the back stack
                            .commit();
                }
            });
        }



    }

    private class MyModelAdapter extends RecyclerView.Adapter<MyModelHolder> {
        private List<ViewDetailPekerjaanVO> mPekerjaanVOList;

        public MyModelAdapter(List<ViewDetailPekerjaanVO> datas) {
            mPekerjaanVOList = datas;
        }

        @NonNull
        @Override
        public MyModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MyModelHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MyModelHolder holder, int position) {
            ViewDetailPekerjaanVO data = mPekerjaanVOList.get(position);
            holder.bind(data, position);
        }

        @Override
        public int getItemCount() {
            return mPekerjaanVOList.size();
        }
    }
}

