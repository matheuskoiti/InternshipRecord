package com.internrecord.android.mkoyama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRecordActivity extends AppCompatActivity {

    private Record recordItem;
    private TextView tv_summay_view;
    private TextView tv_desc_view;
    private TextView tv_week_view;

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


}
