package com.example.theatercrowd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attribute, container, false);
    }

    public boolean areValuesReady() {
        Spinner logicalOp = getActivity().findViewById(R.id.spinner_logic);
        if(logicalOp.getSelectedItem() == null) {
            return false;
        }
        Spinner attribute = getActivity().findViewById(R.id.spinner_attribute);
        if(attribute.getSelectedItem() == null) {
            return false;
        }
        EditText value = getActivity().findViewById(R.id.edit_text_attribute);
        if(value.getText().toString().equals("") || value.getText().toString() == null) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the values of the attributes and returns them in the correct SQL format.
     * NOTE: THIS DOES *NOT* CHECK if there are values selected/available. Please run areValuesReady() first.
     * This will return NULL if values were not ready.
     * @return the values formatted as a SQL string
     */
    public String retrieveValues() {
        Spinner logicalOp = getActivity().findViewById(R.id.spinner_logic);
        Spinner attribute = getActivity().findViewById(R.id.spinner_attribute);
        EditText value = getActivity().findViewById(R.id.edit_text_attribute);
        String logicalOperation = (String) ((TextView)logicalOp.getSelectedItem()).getText();
        String chosenAttribute = (String) ((TextView)attribute.getSelectedItem()).getText();
        String chosenValue = value.getText().toString();
        String clause = createClause(logicalOperation, chosenAttribute, chosenValue);
        return clause;
    }

    private String createClause(String logicalOperator, String chosenAttribute, String chosenValue) {
        String clause;
        switch(logicalOperator) {
            case("AND") :
            case("OR") :
                return logicalOperator + " " + chosenAttribute + " = " + chosenValue;
            case("ANDLESS") :
                return "AND " + chosenAttribute + " < " + chosenValue;
            case("ORLESS") :
                return "OR " + chosenAttribute + " < " + chosenValue;
            case("ANDMORE") :
                return "AND " + chosenAttribute + " > " + chosenValue;
            case("ORMORE") :
                return "OR " + chosenAttribute + " > " + chosenValue;
            default:
                return null;
        }
    }
}