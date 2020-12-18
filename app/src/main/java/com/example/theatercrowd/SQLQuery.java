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
import android.widget.Button;
import android.widget.LinearLayout;
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
    private String selectedObjectType = "Person";
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_s_q_l_query, container, false);
        objectType = (Spinner) view.findViewById(R.id.spinner_object);
        String[] objectTypeValues = new String[]{"Person", "Movie", "Award"};
        setSpinnerValues(objectType, objectTypeValues);
        objectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String oldSelection = selectedObjectType;
                selectedObjectType = (String) objectType.getSelectedItem();
                if(!oldSelection.equals(selectedObjectType)) {
                    FragmentManager manager = getChildFragmentManager();
                    List<Fragment> fragments = manager.getFragments();
                    for(int i = 0; i < fragments.size(); i++) {
                        ((Attribute) fragments.get(i)).updateObjectType(selectedObjectType);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Button button = view.findViewById(R.id.filter_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFilter();
            }
        });
        return view;
    }

    private void setSpinnerValues(Spinner spinner, String[] values) {
        spinner.setAdapter(new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, values));
    }

    public void addFilter() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("Object_Type",selectedObjectType);
        transaction.add(R.id.linear_layout_query, Attribute.class, bundle);
        transaction.commitNow();
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