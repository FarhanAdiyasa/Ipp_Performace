package com.polytechnic.astra.ac.id.ippperformance.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.polytechnic.astra.ac.id.ippperformance.DBHelper.LoginDatabaseHelper;
import com.polytechnic.astra.ac.id.ippperformance.MainActivity;
import com.polytechnic.astra.ac.id.ippperformance.Model.Login;
import com.polytechnic.astra.ac.id.ippperformance.R;
import com.polytechnic.astra.ac.id.ippperformance.ViewModel.LoginViewModel;

import java.util.List;

public class LoginFragment extends Fragment {
    private Button loginButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private LoginViewModel myPekerjaan;
    private LoginDatabaseHelper loginDatabaseHelper;
    private boolean isPasswordVisible = false;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPekerjaan = new ViewModelProvider(this).get(LoginViewModel.class);
        loginDatabaseHelper = new LoginDatabaseHelper(getContext());

        List<Login> loginData = loginDatabaseHelper.getAllLogin();
        if (!loginData.isEmpty()) {
            Login login = loginData.get(0); // Assuming you have stored only one login entry
            String username = login.getUsername();
            String password = login.getPassword();

            myPekerjaan.login(username, password).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String result) {
                    if (result.equals("Wrong")) {
                        Toast.makeText(getContext(), "Username atau Password Salah!", Toast.LENGTH_SHORT).show();
                    } else {

                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.showBottomNavigationView();
                        }
                        navigateToDashboard();
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        loginButton = v.findViewById(R.id.loginButton);
        mUsernameEditText = v.findViewById(R.id.usernameEditText);
        mPasswordEditText = v.findViewById(R.id.passwordEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(mUsernameEditText, mPasswordEditText)) {
                    String username = mUsernameEditText.getText().toString();
                    String password = mPasswordEditText.getText().toString();

                    myPekerjaan.login(username, password).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String result) {
                            if (result.equals("Wrong")) {
                                Toast.makeText(getContext(), "Username atau Password Salah!", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("salah")) {
                                Toast.makeText(getContext(), "Hanya Karyawan Yang Bisa Login!", Toast.LENGTH_SHORT).show();
                            } else {
                                loginDatabaseHelper.addLogin(username, password);


                                if (getActivity() instanceof MainActivity) {
                                    MainActivity mainActivity = (MainActivity) getActivity();
                                    mainActivity.showBottomNavigationView();
                                }
                                navigateToDashboard();
                            }
                        }
                    });
                }
            }
        });

        return v;
    }

    private boolean validate(EditText usernameEditText, EditText mmPasswordEditText) {
        boolean valid = true;

        if (usernameEditText.getText().toString().length() == 0) {
            usernameEditText.setError("Username must be filled in!");
            usernameEditText.requestFocus();
            valid = false;
        }
        if (mmPasswordEditText.getText().toString().length() == 0) {
            mmPasswordEditText.setError("Password must be filled in!");
            mmPasswordEditText.requestFocus();
            valid = false;
        }

        return valid;
    }

    private void navigateToDashboard() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
