package com.internrecord.android.mkoyama;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mdb;
    private ListView lv_records;
    private ArrayAdapter adapter;
    private List<Record> recordList;
    static final int CODE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        lv_records = (ListView) findViewById(R.id.lv_records);
        loadAndPrintAllRecords();

        // Making a record`s view page load when an item is clicked
        lv_records.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer recordId = (int) (long) id;
                Intent viewRecordActivity = new Intent(getApplicationContext(), ViewRecordActivity.class);
                viewRecordActivity.putExtra("recordObject", recordList.get(recordId));
                startActivityForResult(viewRecordActivity, CODE_REQUEST);
            }
        });

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadAndPrintAllRecords();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Click event for floating button
     *
     * @param view
     */
    public void onClickFloatingButton(View view) {
        Intent addRecordActivity = new Intent(this, AddRecordActivity.class);
        startActivityForResult(addRecordActivity, CODE_REQUEST);
    }


    /**
     * Load all records from db and return them
     *
     * @return a list of all record from database
     */
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

    /**
     * Update the record's list. this method is called when creating the page and when an insert
     * operation is finished
     */
    private void loadAndPrintAllRecords() {
        recordList = loadRecordsData();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordList);
        lv_records.setAdapter(adapter);
    }

}
