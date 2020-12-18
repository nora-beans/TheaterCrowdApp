package com.example.theatercrowd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
            if(objectType == null) {
                Log.e("RHINO", "object type is null");
                objectType = "Person";
            }

        }

    }

    /**
     * Sets the spinner values based off of the type of searchable object that has been passed.
     * @param
     */
    private void setSpinnerValues(View view) {
        Spinner logicalOp = view.findViewById(R.id.spinner_logic);
        String[] logicalValues = new String[]{"AND", "OR", "ANDMORE", "ORMORE", "ANDLESS", "ORLESS"};
        logicalOp.setAdapter(new ArrayAdapter<>(view.getContext(),
                R.layout.support_simple_spinner_dropdown_item, logicalValues));
        Spinner attributes = view.findViewById(R.id.spinner_attribute);
        String[] attributeList = new String[]{};
        switch(objectType) {
            case("Person") :
                attributeList = new String[]{"p.Name", "p.Birthdate", "p.Nationality"};
                break;
            case("Movie") :
                attributeList = new String[]{"m.Title", "m.ReleaseYear", "m.Genre"};
                break;
            case("Award") :
                attributeList = new String[]{"a.AwardName", "a.AwardYear", "a.MovieTitle",
                        "a.MovieReleaseYear", "a.AwardWinner"};
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                R.layout.support_simple_spinner_dropdown_item, attributeList);
        attributes.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attribute, container, false);
        if(objectType == null) {
            objectType = "Person";
        }
        setSpinnerValues(view);
        return view;
    }

    public void updateObjectType(String newType) {
        objectType = newType;
        setSpinnerValues(getView());
    }

    /** Checks to see if all values have been assigned yet.
     * This MUST be run before running retrieve values
     * @return true if all the values have a selected field. False otherwise.
     */
    public boolean areValuesReady() {
        Spinner logicalOp = getView().findViewById(R.id.spinner_logic);
        if(logicalOp.getSelectedItem() == null) {
            return false;
        }
        Spinner attribute = getView().findViewById(R.id.spinner_attribute);
        if(attribute.getSelectedItem() == null) {
            return false;
        }
        EditText value = getView().findViewById(R.id.edit_text_attribute);
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
        String logicalOperation = (String) logicalOp.getSelectedItem();
        String chosenAttribute = (String) attribute.getSelectedItem();
        String chosenValue = value.getText().toString();
        String clause = createClause(logicalOperation, chosenAttribute, chosenValue);
        return clause;
    }

    /**
     * Formats and creates the string to be returned in retrieveValues
     * @param logicalOperator is the logical operator that determines how the string is formatted
     *                        anything that starts with AND will start with AND, anything that starts
     *                        with OR will start with OR. If that's the end, it's an equals string.
     *                        If it is followed by MORE or LESS, then it will have > or < respectively
     * @param chosenAttribute is the attribute that is being compared
     * @param chosenValue is the value the user input. It can be either text (ie. ABCDE) or a number.
     *                    It remains as a string and is not evaluated in this method.
     * @return the fully formatted WHERE clause for a SQL string.
     */
    private String createClause(String logicalOperator, String chosenAttribute, String chosenValue) {
        String clause;
        switch(logicalOperator) {
            case("IS") :
                return chosenAttribute + " = " + chosenValue;
            case("ISNOT") :
                return "NOT " + chosenAttribute + " = " + chosenValue;
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