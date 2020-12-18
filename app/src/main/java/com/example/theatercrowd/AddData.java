package com.example.theatercrowd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddData extends AppCompatActivity {

    String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] spinnerValues = new String[]{"Person", "Movie", "Movie Review",
                "Movie Credit", "Award"};
        spinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, spinnerValues));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String oldType = selectedType;
                selectedType = parent.getSelectedItem().toString();
                if(!selectedType.equals(oldType)) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    if(fragmentManager.getFragments().size() != 0) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.remove(fragmentManager.getFragments().get(0));
                        transaction.commitNow();
                    }
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    switch(selectedType) {
                        case("Person") :
                            transaction.add(R.id.fragment_container_add_view, NewPersonFragment.class, null);
                            break;
                        case("Movie") :
                            transaction.add(R.id.fragment_container_add_view, NewMovieFragment.class, null);
                            break;
                        case("Movie Review") :
                            transaction.add(R.id.fragment_container_add_view, NewReview.class, null);
                            break;
                        case("Movie Credit") :
                            transaction.add(R.id.fragment_container_add_view, NewMovieCredit.class, null);
                            break;
                        case("Award") :
                            transaction.add(R.id.fragment_container_add_view, NewAwardFragment.class, null);
                            break;
                    }
                    transaction.commitNow();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sendData(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments =  fragmentManager.getFragments();
        if(fragments.size() == 0) {
            Toast.makeText(this, "You haven't added any information. Please fill out the forms below"
                    , Toast.LENGTH_SHORT).show();
        } else {
            switch(selectedType) {
                case("Person") :
                    NewPersonFragment person = (NewPersonFragment) fragments.get(0);
                    //TODO: Pull data from fragment
                    //TODO: Send data to the SQL Service
                    break;
                case("Movie") :
                    NewMovieFragment movie = (NewMovieFragment) fragments.get(0);
                    //
                    //
                    break;
                case("Movie Review") :
                    NewReview review = (NewReview) fragments.get(0);
                    //
                    //
                    break;
                case("Movie Credit") :
                    NewMovieCredit credit = (NewMovieCredit) fragments.get(0);
                    //
                    //
                    break;
                case("Award") :
                    NewAwardFragment award = (NewAwardFragment) fragments.get(0);
                    //
                    //
                    break;
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}