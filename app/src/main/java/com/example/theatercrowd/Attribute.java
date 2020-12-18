package com.example.theatercrowd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Attribute#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Attribute extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String OBJECT_TYPE = "Object_Type";
    private String objectType;
    private String logicalOperator;
    private String attribute;

    public Attribute() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param objectType is the SQL Object to have attributes listed for.
     *                   The types of Objects here are 'Award', 'Person', or 'Movie'
     * @return A new instance of fragment Attribute.
     */
    public static Attribute newInstance(String objectType) {
        Attribute fragment = new Attribute();
        Bundle args = new Bundle();
        args.putString(OBJECT_TYPE, objectType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            objectType = getArguments().getString(OBJECT_TYPE);
        }
        Spinner logic = getActivity().findViewById(R.id.spinner_logic);
        logic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attribute, container, false);
    }

    public String retireveValues() {
        Spinner logicalOp = getActivity().findViewById(R.id.spinner_logic);
        Spinner attribute = getActivity().findViewById(R.id.spinner_attribute);
        EditText value = getActivity().findViewById(R.id.edit_text_attribute);
        String clause = "";

        return null;
    }
}