package com.example.courtcounter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.courtcounter.data.MatchContract.MatchEntry;

/**
 is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class MatchCursorAdapter extends CursorAdapter {


    public MatchCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of pet attributes that we're interested in
        int firstnameColumnIndex = cursor.getColumnIndex(MatchEntry.COLUMN_TEAM_NAME_FIRST);
        int secondnameColumnIndex = cursor.getColumnIndex(MatchEntry.COLUMN_TEAM_NAME_SECOND);
        int firstscoreColumnIndex = cursor.getColumnIndex(MatchEntry.COLUMN_TEAM_SCORE_FIRST);
        int secondscoreColumnIndex = cursor.getColumnIndex(MatchEntry.COLUMN_TEAM_SCORE_SECOND);

        // Read the pet attributes from the Cursor for the current pet
        String FirstteamName = cursor.getString(firstnameColumnIndex);
        String SecondteamName = cursor.getString(secondnameColumnIndex);
        String FirstteamScore = cursor.getString(firstscoreColumnIndex);
        String SecondteamScore = cursor.getString(secondscoreColumnIndex);

        String ss = FirstteamName+" vs "+SecondteamName;
        String sss = "SCORE  "+FirstteamScore+" - "+SecondteamScore;
        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(ss);
        summaryTextView.setText(sss);
    }
}

