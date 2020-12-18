package com.example.theatercrowd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    int query_length = 1;
    SQLService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SQLService.class);
        Log.d("Rhino", "Inside Main Activity onCreate");
        startService(intent);
        Log.d("Rhino", "Intent to start service sent");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container_view, SQLQuery.class, null);
        transaction.setReorderingAllowed(true);
        transaction.commitNow();
        query_length++;
    }

    public void onSubmit(View view) {
        String statement = createSQLStatement(fragmentManager.getFragments());
        if (statement != null) {
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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container_view, SQLQuery.class, null);
        transaction.setReorderingAllowed(true);
        transaction.commitNow();
        query_length++;
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
            Iterator<String> iterator = objects.iterator();
            for(int i = 0; i < objects.size(); i++) {
                String object = iterator.next();
                switch(object) {
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
                if (i != objects.size() - 1) {
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