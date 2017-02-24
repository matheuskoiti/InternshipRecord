package com.internrecord.android.mkoyama;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Loading and printing all records
        ListView lv_records = (ListView) findViewById(R.id.lv_records);
        List<Record> recordList = loadRecordsData();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordList);
        lv_records.setAdapter(adapter);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Click event for floating button
    public void onClickFloatingButton(View view) {
        Intent addRecordActivity = new Intent(this, AddRecord.class);
        startActivity(addRecordActivity);
    }

    // Load all records from db and return a list of this records
    private List<Record> loadRecordsData() {
        RecordDbHelper dbHelper = new RecordDbHelper(this);
        mdb = dbHelper.getReadableDatabase();

        Cursor cursor = mdb.query(
                RecordContract.RecordEntry.TABLE_NAME,  // The table to query
                null,                                   // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );

        List<Record> recordList = new ArrayList<Record>();
        while(cursor.moveToNext()) {
            String summ = cursor.getString(cursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_SUMMARY));
            String desc = cursor.getString(cursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_DESCRIPTION));
            String week = cursor.getString(cursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_WEEK));
            long id = cursor.getLong(cursor.getColumnIndex(RecordContract.RecordEntry._ID));

            Record record = new Record(id, summ, desc, week);

            recordList.add(record);
        }
        cursor.close();

        return recordList;
    }
}
