package com.internrecord.android.mkoyama;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

public class UpdateRecordActivity extends AppCompatActivity {

    private Record recordItem;
    private EditText et_summary_edit;
    private EditText et_desc_edit;
    private EditText et_week_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        Intent intent = getIntent();
        recordItem = (Record)intent.getSerializableExtra("recordObject");

        et_summary_edit = (EditText) findViewById(R.id.et_summay_edit);
        et_desc_edit = (EditText) findViewById(R.id.et_description_edit);
        et_week_edit = (EditText) findViewById(R.id.et_week_edit);

        loadRecordData();
    }

    /**
     * Update/Edit button click event
     *
     * @param view
     */
    public void onClickUpdateButton(View view) {
        if (updateRecord()) {
            Toast.makeText(getApplicationContext(), "Registro atualizado com sucesso", Toast.LENGTH_SHORT).show();

            // Return to parent activity with the updated record
            Intent resultData = new Intent();
            resultData.putExtra("updatedRecord", recordItem);
            setResult(UpdateRecordActivity.RESULT_OK, resultData);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Não foi possível atualizar o registro", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Puts all record attributes (from form) inside a content value and update a record from database
     *
     * @return true if the update was succeed
     */
    private boolean updateRecord() {
        RecordDbHelper dbHelper = new RecordDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        getValuesFromForm();

        // New values
        ContentValues values = new ContentValues();
        values.put(RecordContract.RecordEntry.COLUMN_SUMMARY, recordItem.getSummary());
        values.put(RecordContract.RecordEntry.COLUMN_DESCRIPTION, recordItem.getDescription());
        values.put(RecordContract.RecordEntry.COLUMN_WEEK, recordItem.getWeek());

        int count = db.update(
                RecordContract.RecordEntry.TABLE_NAME,
                values,
                "_id="+recordItem.getId(),
                null);

        return count > 0;
    }

    /**
     * Set the new values (from form) into the record object
     */
    private void getValuesFromForm() {
        recordItem.setSummary(et_summary_edit.getText().toString());
        recordItem.setDescription(et_desc_edit.getText().toString());
        recordItem.setWeek(et_week_edit.getText().toString());
    }

    /**
     * Making the edit texts show the object values
     */
    private void loadRecordData() {
        et_summary_edit.setText(recordItem.getSummary());
        et_desc_edit.setText(recordItem.getDescription());
        et_week_edit.setText(recordItem.getWeek());
    }
}
