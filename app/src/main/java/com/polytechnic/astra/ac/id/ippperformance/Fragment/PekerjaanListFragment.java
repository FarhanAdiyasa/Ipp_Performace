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
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanViewVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.MyListViewModel;

import java.util.Collections;
import java.util.List;

public class PekerjaanListFragment extends Fragment {

    private MyListViewModel mMyListViewModel;
    private RecyclerView mRecyclerView;
    private MyModelAdapter mAdapter;
    private DashboardViewModel mDashboardViewModel;
    private TextView mTextViewNama, mTextViewSemester;
    private ImageView mLogout;
    private TextView mDataNotFoundTextView;
    private LoginDatabaseHelper loginDatabaseHelper;

    public static PekerjaanListFragment newInstance() {
        return new PekerjaanListFragment();
    }

    private class MyModelHolder extends RecyclerView.ViewHolder {
        ImageView btnEditPekerjaan;
        ImageView btnHapusPekerjaan;
        ImageView btnDetailPekerjaan;
        PekerjaanViewVO mPekerjaanModel;

        private TextView mNamaPekerjaanTextView;
        private TextView mKeteranganPekerjaanTextView;

        public MyModelHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recylerview_mymodellist, parent, false));
            mNamaPekerjaanTextView = itemView.findViewById(R.id.txtNamaPekerjaan);
            mTextViewSemester = itemView.findViewById(R.id.txtSemesterPekerjaan);
            mKeteranganPekerjaanTextView = itemView.findViewById(R.id.txtKeteranganPekerjaan);
            btnEditPekerjaan = itemView.findViewById(R.id.btnEditPekerjaan);
            btnHapusPekerjaan = itemView.findViewById(R.id.btnHapusPekerjaan);
            btnDetailPekerjaan = itemView.findViewById(R.id.btnDetailPekerjaan);
        }

        public void bind(PekerjaanViewVO data) {
            mPekerjaanModel = data;
            if (data != null) {
                mNamaPekerjaanTextView.setText(data.getPkjNama());
                mKeteranganPekerjaanTextView.setText("Keterangan : "+ data.getPkjKeterangan());
                mTextViewSemester.setText("Semester : "+ data.getitvSemester() + " - " +  data.getTahunAjaran());
            }
            if (data.getPkjStatus().equals("2")) {
                btnHapusPekerjaan.setImageResource(R.drawable.toogle_off); // Set the icon to toggle_on
            } else {
                btnHapusPekerjaan.setImageResource(R.drawable.toggle_on); // Set the icon to delete
            }
            btnEditPekerjaan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("detailId", data.getPkjId());
                    EditPekerjaanFragment fragmentDetail = new EditPekerjaanFragment();
                    fragmentDetail.setArguments(bundle);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragmentDetail)
                            .addToBackStack(null) // Add this line to add the transaction to the back stack
                            .commit();
                }
            });

            btnHapusPekerjaan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyListViewModel.setStatus(data.getPkjId(), data.getPkjStatus()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String result) {
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, PekerjaanListFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }
            });
            btnDetailPekerjaan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("detailId", data.getPkjId());
                    //Toast.makeText(getContext(), data.getPkjId(), Toast.LENGTH_SHORT).show();
                    DetailPekerjaanListFragment fragmentDetail = DetailPekerjaanListFragment.newInstance(data.getPkjId());
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
        private List<PekerjaanViewVO> mPekerjaanVOList;

        public MyModelAdapter(List<PekerjaanViewVO> datas) {
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
            PekerjaanViewVO data = mPekerjaanVOList.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return mPekerjaanVOList.size();
        }
    }

    private void updateUI(List<PekerjaanViewVO> datas) {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyListViewModel = new ViewModelProvider(this).get(MyListViewModel.class);
        mAdapter = new MyModelAdapter(Collections.emptyList());
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());

//        mRecyclerView = mRecyclerView.findViewById(R.id.recyclerView);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Menginflate layout pekerjaan.xml
        View view = inflater.inflate(R.layout.pekerjaan, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mDataNotFoundTextView = view.findViewById(R.id.dataNotFoundTextView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CircularImageView logoImageView = view.findViewById(R.id.logoImageView);
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
        mDashboardViewModel.getKryFoto().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    Glide.with(PekerjaanListFragment.this)
                            .load(ApiUtils.API_URL+ "SSO/uploads/foto/" + userModel.getKry_foto())
                            .into(logoImageView);
                    mTextViewNama.setText(userModel.getKry_nama());
                }
            }
        });
        mMyListViewModel.getListModel().observe(getViewLifecycleOwner(), new Observer<List<PekerjaanViewVO>>() {
            @Override
            public void onChanged(List<PekerjaanViewVO> pekerjaanVOList) {
                updateUI(pekerjaanVOList);
                Log.d("TEST", "Called: " + pekerjaanVOList.size());
            }
        });
        Button mTambahPekerjaan = view.findViewById(R.id.btnTambahPekerjaan);
        mTambahPekerjaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new TambahPekerjaanFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
            }
        });

    }
}