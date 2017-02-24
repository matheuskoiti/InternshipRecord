package com.internrecord.android.mkoyama;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.internrecord.android.mkoyama.db.RecordContract;
import com.internrecord.android.mkoyama.db.RecordDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkoyama on 23/02/17.
 */

public class Record implements Serializable {
    protected long id;
    protected String summary;
    protected String description;
    protected String week;

    public Record(long id, String summary, String description, String week) {
        setId(id);
        setSummary(summary);
        setDescription(description);
        setWeek(week);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String toString() {
        return getSummary();
    }

}
