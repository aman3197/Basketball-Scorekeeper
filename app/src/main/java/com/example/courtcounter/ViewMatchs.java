package com.example.courtcounter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.example.courtcounter.data.MatchContract.MatchEntry;


public class ViewMatchs extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int Match_Loader = 0;
    MatchCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matchs);

        ListView MatchListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        mCursorAdapter = new MatchCursorAdapter(this, null);

        // Attach the adapter to the ListView.
        MatchListView.setAdapter(mCursorAdapter);

        getSupportLoaderManager().initLoader(Match_Loader,null,this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }
    private void deleteAllMatches() {
        int rowsDeleted = getContentResolver().delete(MatchEntry.CONTENT_URI, null, null);
        Toast.makeText(this, "Delete! Successfully", Toast.LENGTH_LONG).show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteAllMatches();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog diaBox = AskOption();
        diaBox.show();
        return true;
    }



    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MatchEntry._ID,
                MatchEntry.COLUMN_TEAM_NAME_FIRST,
                MatchEntry.COLUMN_TEAM_NAME_SECOND,
                MatchEntry.COLUMN_TEAM_SCORE_FIRST,
                MatchEntry.COLUMN_TEAM_SCORE_SECOND};

        return new CursorLoader(this,MatchEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mCursorAdapter.swapCursor(null);
    }
}
