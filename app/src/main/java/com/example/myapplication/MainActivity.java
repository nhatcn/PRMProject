package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.fragments.AdminDashboardFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.StudentDashboardFragment;
import com.example.myapplication.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements FragmentNavigationListener {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        // Check if user is logged in
        if (sessionManager.isLoggedIn()) {
            // Navigate to appropriate dashboard based on role
            if (sessionManager.isAdmin()) {
                loadFragment(new AdminDashboardFragment());
            } else {
                loadFragment(new StudentDashboardFragment());
            }
        } else {
            // Navigate to login fragment
            loadFragment(new LoginFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show menu if user is logged in
        if (sessionManager.isLoggedIn()) {
            getMenuInflater().inflate(R.menu.menu_dashboard, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sessionManager.logout();
        loadFragment(new LoginFragment());
        invalidateOptionsMenu(); // Refresh the options menu
    }

    @Override
    public void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        loadFragment(fragment, addToBackStack);
    }

    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, false);
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        } else {
            // Clear back stack when loading a main fragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

