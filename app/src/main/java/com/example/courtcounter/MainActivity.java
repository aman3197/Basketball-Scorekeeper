package com.example.courtcounter;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courtcounter.data.MatchdbHelper;
import com.example.courtcounter.data.MatchContract.MatchEntry;


public class MainActivity extends AppCompatActivity {
    int scoreforTeamA=0;
    int B=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        MatchdbHelper mDbHelper = new MatchdbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
    }

    public void next(View view){
        EditText first_name = (EditText)findViewById(R.id.get_first_name_view);
        EditText second_name = (EditText)findViewById(R.id.get_second_name_view);
        String first_str = first_name.getText().toString().trim();
        String second_str = second_name.getText().toString().trim();
        if(TextUtils.isEmpty(first_str)|| TextUtils.isEmpty(second_str))
        {
            Toast.makeText(this,"Enter Team Name",Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, FirstPage.class);
            intent.putExtra("Name_of_First_team", first_str);
            intent.putExtra("Name_of_second_team", second_str);
            startActivity(intent);
        }
    }

    public boolean isEmpty(){
        MatchdbHelper mDbHelper = new MatchdbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int NoOfRows = (int) DatabaseUtils.queryNumEntries(db,MatchEntry.TABLE_NAME);

        if (NoOfRows == 0){
            return true;
        }else {
            return false;
        }
    }

    public void previous(View view)
    {
        if(isEmpty())
        {
            Toast.makeText(this,"Nothing to Display! Play Match First",Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this,ViewMatchs.class);
            startActivity(intent);
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setMessage("are you sure to exit?");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //if you want to kill app . from other then your main avtivity.(Launcher)
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

                //if you want to finish just current activity

                MainActivity.this.finish();
            }
        });
        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

}
