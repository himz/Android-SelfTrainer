package com.himz.selftrainer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.himz.databases.RoutineAdapter;
import com.himz.databases.DashboardManager;
import com.himz.entities.RoutineDataForAdapter;
import com.himz.entities.RoutineDetail;
import com.himz.helpers.App;

import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends ActionBarActivity {
    static App app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        List<String> data = new ArrayList<String>();
        List<RoutineDetail> routineDetailList = new ArrayList<RoutineDetail>();
        routineDetailList = DashboardManager.getAllRoutineForToday(this.getApplication());
        List<RoutineDataForAdapter> routineDataForAdapterList = new ArrayList<RoutineDataForAdapter>();
        routineDataForAdapterList = DashboardManager.getRoutineDataForAdapter(this.getApplication(),routineDetailList);
        for (int i = 1; i <= 10; i++) {
            data.add(String.format("Item %d", i));
        }
        //CustomAdapter adapter = new CustomAdapter( this, data, phraseList);
        RoutineAdapter adapter = new RoutineAdapter(this, routineDataForAdapterList);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int pos, long id) {

                RoutineDataForAdapter p = (RoutineDataForAdapter)listView.getAdapter().getItem(pos);
                /*int phraseID = p.getId();
                Intent myintent=new Intent(ListViewActivity.this, MainActivity.class).putExtra("phraseID", phraseID);
                startActivity(myintent);*/
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                toast((String) textView.getText());
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(ListViewActivity.this,
                String.format("Item clicked: %s", text), Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
