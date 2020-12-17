package com.example.theatercrowd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SQLQuery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SQLQuery extends Fragment {

    private Spinner objectType;

    public SQLQuery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SQLQuery.
     */
    // TODO: Rename and change types and number of parameters
    public static SQLQuery newInstance() {
        SQLQuery fragment = new SQLQuery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectType = getActivity().findViewById(R.id.spinner_object);
        String[] objectTypeValues = new String[]{"Person", "Movie", "Award"};
        setSpinnerValues(objectType, objectTypeValues );
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
}