package com.example.theatercrowd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    int query_length = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = SQLQuery.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, "Query_" + query_length);
        transaction.commitNow();
        query_length++;
    }

    public void onSubmit(View view) {
        fragmentManager.getFragments();
    }

    public void onAddFragment(View view) {
        Fragment fragment = SQLQuery.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, "Query_" + query_length);
        transaction.commitNow();
        query_length++;
    }
}