package com.example.theatercrowd;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResult extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String GENRE_OR_NATIONALITY = "genre";
    private static final String BIO = "bio";

    // TODO: Rename and change types of parameters
    private String type;
    private String name;
    private String date;
    private String genre;
    private String bio;

    public SearchResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchResult.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResult newInstance(String type, String name, String date, String genre, String bio) {
        SearchResult fragment = new SearchResult();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(NAME, name);
        args.putString(DATE, date);
        args.putString(GENRE_OR_NATIONALITY, genre);
        args.putString(BIO, bio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
            name = getArguments().getString(NAME);
            date = getArguments().getString(DATE);
            genre = getArguments().getString(GENRE_OR_NATIONALITY);
            bio = getArguments().getString(BIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        TextView typeView = view.findViewById(R.id.text_view_type);
        typeView.setText(type);
        TextView nameView = view.findViewById(R.id.textView_name);
        nameView.setText(name);
        TextView dateView = view.findViewById(R.id.textView_birthdate);
        dateView.setText(date);
        TextView genreView = view.findViewById(R.id.textView_nationality);
        genreView.setText(genre);
        TextView bioView = view.findViewById(R.id.textView_bio);
        bioView.setText(bio);
        return view;
    }

}