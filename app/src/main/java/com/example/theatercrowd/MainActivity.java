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
        String[] statement = createSQLStatement(fragmentManager.getFragments());
        if (statement != null) {
            service = SQLService.getInstance();
            service.insertMovies("Top Gun", 1992, "Action", "Fun movie with Tom. yay.");

            List<String>[] values = service.selectRows(statement);
            for(List<String> list : values) {
                for (String column : list) {
                    Log.d("Rhino", column);
                }
            }
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for(Fragment fragment : manager.getFragments()) {
                transaction.remove(fragment);
            }
            transaction.commitNow();
            Intent intent = new Intent(this, Search.class);
            intent.putExtra("QueryResults", "Coming soon to an app near you.");
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

    public String[] createSQLStatement(List<Fragment> fragments) {
        //I need the following things
        //String of comma separated tables
        //String of comma separated columns
        //String of comma separated COLUMNS for where clause
        //String of comma separated VALUES for where clause
        //Groupby
        //Having
        //Sort order
        String statement = "";
        String otherHalfOfStatement = "";
        String filterColumns = "";
        String filterValues = "";
        Set<String> objects = new HashSet<>();
        ArrayList<String> attributeStrings = new ArrayList<>();
        for(int i = 0; i < fragments.size(); i++) {
            SQLQuery query = (SQLQuery) fragments.get(i);
            if(!query.checkIfReady()) {
                Toast.makeText(this, "Not all fields have been filled yet.", Toast.LENGTH_SHORT).show();
            } else {
                objects.add(query.getSelectedObjectType());
                String[] attributeString = query.getAttributeStrings();
                for(int j = 0; j < attributeString.length; j++) {
                    if(j % 2 == 0) {
                        filterColumns += attributeString[j];
                        if(j != attributeString.length - 1) {
                        }
                    } else {
                        filterValues += attributeString[j];
                        if(j != attributeString.length - 1) {
                            filterValues += ", ";
                        }
                    }
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
                        statement += "Name, ";
                        otherHalfOfStatement += "Person ";
                        break;
                    case("Movie") :
                        statement += "Title, ";
                        otherHalfOfStatement += "Movie ";
                        break;
                    case("Award") :
                        statement += "AwardName, ";
                        otherHalfOfStatement += "Award ";
                        break;
                }
                if (i != objects.size() - 1) {
                    statement += ",";
                    otherHalfOfStatement += ",";
                }
                statement += " ";
                otherHalfOfStatement += " ";
            }
            String[] query = new String[]{otherHalfOfStatement, statement, filterColumns, filterValues, null, null, null};
            Log.d("Rhino", statement);
            Log.d("Rhino", otherHalfOfStatement);
            Log.d("Rhino", filterColumns);
            Log.d("Rhino", filterValues);
            return query;
        }
    }
}