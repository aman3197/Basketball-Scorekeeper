package com.example.courtcounter.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MatchContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.courtcounter";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_Match = "MATCHES";

    public static final class MatchEntry implements BaseColumns{
        public final static String TABLE_NAME = "MATCHES";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TEAM_NAME_FIRST = "FirstTeam";
        public final static String COLUMN_TEAM_NAME_SECOND = "SecondTeam";
        public final static String COLUMN_TEAM_SCORE_FIRST = "FirstTeamScore";
        public final static String COLUMN_TEAM_SCORE_SECOND = "SecondTeamScore";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Match);
    }

}
