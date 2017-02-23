package com.internrecord.android.mkoyama;

import android.provider.BaseColumns;

/**
 * Created by mkoyama on 23/02/17.
 */

public class RecordContract {

    public static final class RecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "record";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_week = "week";
    }
}
