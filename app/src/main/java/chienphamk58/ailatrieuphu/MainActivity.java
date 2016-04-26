package chienphamk58.ailatrieuphu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;

public class MainActivity extends Activity {

    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, PlaySongService.class);
        startService(intent);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> qst = databaseAccess.getQuestion();
        databaseAccess.close();

    }
    public void Play(View view){
        AlertDialog.Builder arlertDialogBuiler = new AlertDialog.Builder(this);
        final Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        final MediaPlayer mediaPlayer = new MediaPlayer().create(this, R.raw.ready);
        mediaPlayer.start();
        // set Title
        arlertDialogBuiler.setTitle("");

        // set dialog Message
        arlertDialogBuiler.setMessage("Are you ready ?").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                MainActivity.this.startActivity(myIntent);
                mediaPlayer.stop();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
                mediaPlayer.stop();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = arlertDialogBuiler.create();

        // show it
        alertDialog.show();

    }

    public void Help(View view){
        final Intent myIntent = new Intent(MainActivity.this, Main3Activity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void High_Score(View view){
        Intent myIntent = new Intent(MainActivity.this, Main4Activity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void Setting(View view){
        Intent myIntent = new Intent(MainActivity.this, Main5Activity.class);
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onStop(){
        super.onStop();
        stopService(intent);
    }

    @Override
    public void onPause(){
        super.onPause();
        stopService(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        startService(intent);
    }
}
