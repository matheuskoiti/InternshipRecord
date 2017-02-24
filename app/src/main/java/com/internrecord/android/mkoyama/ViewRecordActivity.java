package com.internrecord.android.mkoyama;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

public class ViewRecordActivity extends AppCompatActivity {

    private Record recordItem;
    private TextView tv_summay_view;
    private TextView tv_desc_view;
    private TextView tv_week_view;
    private SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        Intent intent = getIntent();
        recordItem = (Record)intent.getSerializableExtra("recordObject");

        tv_summay_view = (TextView) findViewById(R.id.tv_summary_view);
        tv_desc_view = (TextView) findViewById(R.id.tv_desc_view);
        tv_week_view = (TextView) findViewById(R.id.tv_week_view);

        tv_summay_view.setText(recordItem.getSummary());
        tv_desc_view.setText(recordItem.getDescription());
        tv_week_view.setText(recordItem.getWeek());
    }

    // Remove a Record from database
    public void onClickBtnDelete(View view) {
        if (removeRecord()) {
            Toast.makeText(getApplicationContext(), "Registro removido com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Não foi possível remover o registro", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean removeRecord() {
        RecordDbHelper dbHelper = new RecordDbHelper(this);
        mdb = dbHelper.getReadableDatabase();
        long id = recordItem.getId();
        return mdb.delete(RecordContract.RecordEntry.TABLE_NAME, RecordContract.RecordEntry._ID + "=" + id, null) > 0;
    }
}
