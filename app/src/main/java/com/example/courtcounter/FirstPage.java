package com.example.courtcounter;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courtcounter.data.MatchdbHelper;
import com.example.courtcounter.data.MatchContract.MatchEntry;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.w3c.dom.Text;

public class FirstPage extends AppCompatActivity {
    private MatchdbHelper help;
    String str_first;
    String str_second;
    int scoreforTeamA=0;
    int B=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView received_team_name_first = (TextView)findViewById(R.id.First_name_view);
        TextView received_team_name_second = (TextView)findViewById(R.id.second_name_view);

        Intent intent = getIntent();

        str_first = intent.getStringExtra("Name_of_First_team");
        str_second = intent.getStringExtra("Name_of_second_team");

        received_team_name_first.setText(str_first);
        received_team_name_second.setText(str_second);

        help = new MatchdbHelper(this);
    }
    public void ThreeA(View view){
        scoreforTeamA+=3;
        displayForTeamA(scoreforTeamA);
    }

    public void TwoA(View view){
        scoreforTeamA+=2;
        displayForTeamA(scoreforTeamA);
    }

    public void oneA(View view){
        scoreforTeamA++;
        displayForTeamA(scoreforTeamA);
    }

    public void Reset(View view){
        scoreforTeamA=0;
        B=0;
        displayForTeamA(scoreforTeamA);
        displayForTeamB(B);
    }

    public void finish(View view){
        String message="";
        if(scoreforTeamA>B)
            message+= str_first+" won the match";
        else if(scoreforTeamA<B)
            message+=str_second+" won the match";
        else if(scoreforTeamA==B)
            message+="Match tie";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public void ThreeB(View view){
        B+=3;
        displayForTeamB(B);
    }

    public void TwoB(View view){
        B+=2;
        displayForTeamB(B);
    }

    public void oneB(View view){
        B++;
        displayForTeamB(B);
    }
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView)findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    private void insertMatch()
    {
        ContentValues values = new ContentValues();

        values.put(MatchEntry.COLUMN_TEAM_NAME_FIRST,str_first);
        values.put(MatchEntry.COLUMN_TEAM_NAME_SECOND,str_second);
        values.put(MatchEntry.COLUMN_TEAM_SCORE_FIRST,scoreforTeamA);
        values.put(MatchEntry.COLUMN_TEAM_SCORE_SECOND,B);

        Uri newUri = getContentResolver().insert(MatchEntry.CONTENT_URI, values);

        Intent intent = new Intent(this,ViewMatchs.class);
        startActivity(intent);
        scoreforTeamA=0;
        B=0;
        displayForTeamA(scoreforTeamA);
        displayForTeamB(B);
        Toast.makeText(this,"Saved! Successfully",Toast.LENGTH_LONG).show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("SAVED")
                .setMessage("Do you want to Save this match")

                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        insertMatch();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        AlertDialog diaBox = AskOption();
        diaBox.show();
       return true;
    }
}
