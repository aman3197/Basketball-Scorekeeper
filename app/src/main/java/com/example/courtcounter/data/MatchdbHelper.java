package com.example.courtcounter.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.courtcounter.data.MatchContract.MatchEntry;

public class MatchdbHelper extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "MatchesHappened.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public MatchdbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + MatchEntry.TABLE_NAME + "("
                + MatchEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MatchEntry.COLUMN_TEAM_NAME_FIRST + " TEXT NOT NULL, "
                + MatchEntry.COLUMN_TEAM_NAME_SECOND + " TEXT NOT NULL ,"
                + MatchEntry.COLUMN_TEAM_SCORE_FIRST + " INTEGER NOT NULL, "
                +MatchEntry.COLUMN_TEAM_SCORE_SECOND + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
