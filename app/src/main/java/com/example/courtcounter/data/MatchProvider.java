package com.example.courtcounter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.courtcounter.data.MatchContract;

import java.security.Provider;

/**
 */
public class MatchProvider extends ContentProvider {

    private static final int Match = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int Match_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        sUriMatcher.addURI(MatchContract.CONTENT_AUTHORITY,MatchContract.PATH_Match,Match);
        sUriMatcher.addURI(MatchContract.CONTENT_AUTHORITY,MatchContract.PATH_Match+"/#",Match_ID);
    }

    public static final String LOG_TAG = MatchProvider.class.getSimpleName();
    private MatchdbHelper help;


    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        help = new MatchdbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = help.getReadableDatabase();
        Cursor cursor;
        int mm = sUriMatcher.match(uri);
        switch (mm) {
            case Match:
                cursor = db.query(MatchContract.MatchEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case Match_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = MatchContract.MatchEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = db.query(MatchContract.MatchEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int mm = sUriMatcher.match(uri);
        switch (mm) {
            case Match:
                return insertMatch(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMatch(Uri uri, ContentValues values) {

        SQLiteDatabase db = help.getWritableDatabase();

        long id = db.insert(MatchContract.MatchEntry.TABLE_NAME,null,values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int mm = sUriMatcher.match(uri);
        switch (mm) {
            case Match:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case Match_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MatchContract.MatchEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = help.getWritableDatabase();
        int rowupdated =  db.update(MatchContract.MatchEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowupdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowupdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = help.getWritableDatabase();
        int rowsDeleted;
        final int mm = sUriMatcher.match(uri);
        switch (mm) {
            case Match:
                // Delete all rows that match the selection and selection args
                rowsDeleted =  db.delete(MatchContract.MatchEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case Match_ID:
                // Delete a single row given by the ID in the URI
                selection = MatchContract.MatchEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = db.delete(MatchContract.MatchEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}