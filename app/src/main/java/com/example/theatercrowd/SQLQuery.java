package com.example.theatercrowd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SQLQuery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SQLQuery extends Fragment {

    private Spinner objectType;
    private String selectedObjectType;
    private int filterNumber = 0;

    public SQLQuery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SQLQuery.
     */
    public static SQLQuery newInstance() {
        SQLQuery fragment = new SQLQuery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectType = getActivity().findViewById(R.id.spinner_object);
        String[] objectTypeValues = new String[]{"Person", "Movie", "Award"};
        setSpinnerValues(objectType, objectTypeValues);
        objectType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedObjectType = (String) objectType.getSelectedItem();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s_q_l_query, container, false);
    }

    private void setSpinnerValues(Spinner spinner, String[] values) {
        spinner.setAdapter(new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, values));
    }

    public void addFilter(View view) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(Attribute.newInstance(selectedObjectType), "" + filterNumber);
        filterNumber++;
    }

    public boolean checkIfReady() {
        if(selectedObjectType == null) {
            return false;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(int i = 0; i < fragments.size(); i++) {
            if(!((Attribute)fragments.get(i)).areValuesReady()) {
                return false;
            }
        }
        return true;
    }

    public String getSelectedObjectType() {
        return selectedObjectType;
    }

    public String getAttributeStrings() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        String filters = "";
        for(int i = 0; i < fragments.size(); i++) {
            Attribute attribute = (Attribute) fragments.get(i);
            if(!attribute.areValuesReady()) {
                Toast.makeText(getActivity().getBaseContext(),
                        "Some values have been left unfilled. Please fill them.",
                        Toast.LENGTH_SHORT).show();
                return null;
            } else {
                filters += attribute.retrieveValues() + " ";
            }
        }
        return filters;
    }


}