package com.internrecord.android.mkoyama;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

public class AddRecordActivity extends AppCompatActivity {

    private SQLiteDatabase mdb;
    private EditText et_summary;
    private EditText et_week;
    private EditText et_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        et_summary = (EditText) findViewById(R.id.et_summay);
        et_description = (EditText) findViewById(R.id.et_description);
        et_week = (EditText) findViewById(R.id.et_week);

        RecordDbHelper dbHelper = new RecordDbHelper(this);
        mdb = dbHelper.getWritableDatabase();
    }

    public void onClickAddButton(View view) {
        String summary = et_summary.getText().toString();
        String description = et_description.getText().toString();
        String week = et_week.getText().toString();

        if (addRecord(summary, description, week)) {
            Toast.makeText(getApplicationContext(), "Registro adicionado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Não foi possível adicionar o registro", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean addRecord(String summary, String description, String week) {
        ContentValues values = new ContentValues();
        values.put(RecordContract.RecordEntry.COLUMN_SUMMARY, summary);
        values.put(RecordContract.RecordEntry.COLUMN_DESCRIPTION, description);
        values.put(RecordContract.RecordEntry.COLUMN_WEEK, week);

        long newRowId = mdb.insert(RecordContract.RecordEntry.TABLE_NAME, null, values);

        return newRowId > 0;
    }
}
