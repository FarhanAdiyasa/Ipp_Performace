package com.polytechnic.astra.ac.id.ippperformance.Fragment;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ChartVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardDataVO;
import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private TextView mPengajaran,mPenelitian,mPengabdian,mLainnya, mTextViewNama;
    private BarChart mBarChart1,mBarChart2;
    private DashboardViewModel mDashboardViewModel;
    private ImageView mLogout;
    private LoginDatabaseHelper loginDatabaseHelper;
    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dashboard, container, false);
        CircularImageView logoImageView = v.findViewById(R.id.circularImageView);
        mPengajaran = v.findViewById(R.id.txtPersenPengajaran);
        mPengajaran = v.findViewById(R.id.txtPersenPengajaran);
        mPenelitian = v.findViewById(R.id.txtPersenPenelitian);
        mPengabdian = v.findViewById(R.id.txtPersenPengabdian);
        mLainnya = v.findViewById(R.id.txtPersenLainnya);
        mBarChart1 = v.findViewById(R.id.chart);
        mBarChart2 = v.findViewById(R.id.chart2);
        mTextViewNama = v.findViewById(R.id.textView2);
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
        mDashboardViewModel.getDashboardData().observe(getViewLifecycleOwner(), new Observer<DashboardDataVO>() {
            @Override
            public void onChanged(DashboardDataVO dashboardDataVO) {
                if (dashboardDataVO != null){
                    mPengajaran.setText(dashboardDataVO.getDashboardResultVo().getcPengajaran());
                    mPenelitian.setText(dashboardDataVO.getDashboardResultVo().getCpenelitian());
                    mPengabdian.setText(dashboardDataVO.getDashboardResultVo().getCpengabdian());
                    mLainnya.setText(dashboardDataVO.getDashboardResultVo().getClainnya());

                    //CHART 1
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    List<String> labels = new ArrayList<>();

                    for (int i = 0; i < dashboardDataVO.getResults().size(); i++) {
                        ChartVO item = dashboardDataVO.getResults().get(i);
                        entries.add(new BarEntry(i, Float.parseFloat(item.getValue())));
                        labels.add(item.getData());
                    }
                    if (dashboardDataVO.getUser().getKry_foto() != null && !dashboardDataVO.getUser().getKry_foto().isEmpty()) {
                        Glide.with(DashboardFragment.this)
                                .load(ApiUtils.API_URL +"SSO/uploads/foto/"+dashboardDataVO.getUser().getKry_foto())
                                .into(logoImageView);

                        mTextViewNama.setText(dashboardDataVO.getUser().getKry_nama());
                    }
                    BarDataSet dataSet = new BarDataSet(entries, "Proporsional Pekerjaan (Jam)");
                    dataSet.setColor(Color.rgb(129,24,24));
                    BarData barData = new BarData( dataSet);

                    mBarChart1.setData(barData);
                    mBarChart1.getAxisRight().setEnabled(false); // Disable the right axis
                    mBarChart1.getXAxis().setDrawLabels(false); // Disable X-axis labels
                    mBarChart1.invalidate(); // Refresh the chart

                    //CHART2
                    ArrayList<BarEntry> entries2 = new ArrayList<>();
                    List<String> labels2 = new ArrayList<>();

                    for (int i = 0; i < dashboardDataVO.getResults().size(); i++) {
                        ChartVO item = dashboardDataVO.getResults().get(i);
                        entries2.add(new BarEntry(i, Float.parseFloat(item.getValue())));
                        labels2.add(item.getData());
                    }

                    BarDataSet dataSet2 = new BarDataSet(entries2, "Bobot Kerja Karyawan (Jam)");
                    dataSet2.setColor(Color.rgb(129,24,24));
                    BarData barData2 = new BarData( dataSet2);

                    mBarChart2.setData(barData2);
                    mBarChart2.getXAxis().setDrawLabels(false); // Disable X-axis labels
                    mBarChart2.getAxisRight().setEnabled(false); // Disable the right axis
                    mBarChart2.getAxisRight().setEnabled(false); // Disable the right axis

                    mBarChart2.invalidate(); // Refresh the chart
                }
            }
        });
        return v;
    }
}
