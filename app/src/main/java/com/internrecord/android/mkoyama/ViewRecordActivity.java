package com.internrecord.android.mkoyama;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

import static java.security.AccessController.getContext;

public class ViewRecordActivity extends AppCompatActivity {

    private Record recordItem;
    private TextView tv_summay_view;
    private TextView tv_desc_view;
    private TextView tv_week_view;
    private SQLiteDatabase mdb;
    private final int CODE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        Intent intent = getIntent();
        recordItem = (Record)intent.getSerializableExtra("recordObject");

        tv_summay_view = (TextView) findViewById(R.id.tv_summary_view);
        tv_desc_view = (TextView) findViewById(R.id.tv_desc_view);
        tv_week_view = (TextView) findViewById(R.id.tv_week_view);

        loadRecordView(recordItem);
    }

    // onClick event for delete button
    public void onClickBtnDelete(View view) {
        if (removeRecord()) {
            Toast.makeText(getApplicationContext(), "Registro removido com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Não foi possível remover o registro", Toast.LENGTH_SHORT).show();
        }
    }

    // Remove a Record from database
    private boolean removeRecord() {
        RecordDbHelper dbHelper = new RecordDbHelper(this);
        mdb = dbHelper.getReadableDatabase();
        long id = recordItem.getId();
        return mdb.delete(RecordContract.RecordEntry.TABLE_NAME, RecordContract.RecordEntry._ID + "=" + id, null) > 0;
    }

    // onClick event for upgrade button
    public void onClickBtnUpdate(View view) {
        Intent updateRecordActivity = new Intent(this, UpdateRecordActivity.class);
        updateRecordActivity.putExtra("recordObject", recordItem);
        startActivityForResult(updateRecordActivity, CODE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == UpdateRecordActivity.RESULT_OK) {
            Record updatedRecord = (Record)data.getSerializableExtra("updatedRecord");
            loadRecordView(updatedRecord);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadRecordView(Record record) {
        tv_summay_view.setText(record.getSummary());
        tv_desc_view.setText(record.getDescription());
        tv_week_view.setText(record.getWeek());
    }

}
