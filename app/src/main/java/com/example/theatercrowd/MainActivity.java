package com.example.theatercrowd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String statement = createSQLStatement(fragmentManager.getFragments());
        if(statement == null) {
            return;
        } else {
            //TODO: Move on to search activity and send this query to the SQLService.
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for(Fragment fragment : manager.getFragments()) {
                transaction.remove(fragment);
            }
            transaction.commitNow();
            Intent intent = new Intent(this, Search.class);
            intent.putExtra("Query", statement);
            startActivity(intent);
        }
    }

    public void onAddFragment(View view) {
        Fragment fragment = SQLQuery.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, "Query_" + query_length);
        transaction.commitNow();
        query_length++;
    }

    public void addFilter(View view) {
        String tag = view.getTag().toString();
        SQLQuery fragment = (SQLQuery) fragmentManager.findFragmentByTag(tag);
        if(fragment == null) {
            try {
                throw new Exception("addFilter is broken. oops");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fragment.addFilter(view);
        }
    }

    public String createSQLStatement(List<Fragment> fragments) {
        String statement = "SELECT ";
        String otherHalfOfStatement = "FROM ";
        Set<String> objects = new HashSet<>();
        ArrayList<String> attributeStrings = new ArrayList<>();
        for(int i = 0; i < fragments.size(); i++) {
            SQLQuery query = (SQLQuery) fragments.get(i);
            if(!query.checkIfReady()) {
                Toast.makeText(this, "Not all fields have been filled yet.", Toast.LENGTH_SHORT).show();
            } else {
                objects.add(query.getSelectedObjectType());
                String attributeString = query.getAttributeStrings();
                if(attributeString != null) {
                    attributeStrings.add(attributeString);
                } else {
                    return null;
                }
            }
        }
        if(objects.size() == 0) {
            return null;
        } else {
            String[] objectArr = (String[]) objects.toArray();
            for(int i = 0; i < objectArr.length; i++) {
                switch(objectArr[i]) {
                    case("Person") :
                        statement += "p.Name";
                        otherHalfOfStatement += "Person p";
                    case("Movie") :
                        statement += "m.Title";
                        otherHalfOfStatement += "Movie m";
                    case("Award") :
                        statement += "a.AwardName";
                        otherHalfOfStatement += "Award a";
                }
                if (i != objectArr.length - 1) {
                    statement += ",";
                    otherHalfOfStatement += ",";
                }
                statement += " ";
                otherHalfOfStatement += " ";
            }
            statement += otherHalfOfStatement + " WHERE ";
            for(String attribute : attributeStrings) {
                statement += attribute + " ";
            }
        }
        statement += ";";
        return statement;
    }
}