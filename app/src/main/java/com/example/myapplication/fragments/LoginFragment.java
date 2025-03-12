package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.utils.SessionManager;
import com.example.myapplication.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private UserViewModel userViewModel;
    private FragmentNavigationListener navigationListener;
    private SessionManager sessionManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigationListener) {
            navigationListener = (FragmentNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentNavigationListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        buttonLogin = view.findViewById(R.id.button_login);


        sessionManager = new SessionManager(requireContext());
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonLogin.setOnClickListener(v -> loginUser());


    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userViewModel.login(email, password);

        if (user != null) {
            // Save user session
            sessionManager.createLoginSession(user.getId(), user.getEmail(), user.getRole(), user.getStudentId());

            // Navigate based on role
            if (user.isAdmin()) {
                navigationListener.navigateToFragment(new AdminDashboardFragment(), false);
            } else {
                navigationListener.navigateToFragment(new StudentDashboardFragment(), false);
            }
            
            // Refresh options menu to show logout option
            requireActivity().invalidateOptionsMenu();
        } else {
            Toast.makeText(requireContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}

