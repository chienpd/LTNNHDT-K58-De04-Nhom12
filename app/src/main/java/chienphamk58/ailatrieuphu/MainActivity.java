package chienphamk58.ailatrieuphu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends Activity {
    Intent intent;
    MediaPlayer mediaPlayer;
    Button btnPlay, btnHelp, btnAbout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, PlaySongService.class);
        btnPlay = (Button)findViewById(R.id.button1);
        btnHelp = (Button)findViewById(R.id.button2);
        btnAbout = (Button)findViewById(R.id.button4);
        startService(intent);
    }
    public void Play(View view){
        AlertDialog.Builder arlertDialogBuiler = new AlertDialog.Builder(this);
        final Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        mediaPlayer = new MediaPlayer().create(this, R.raw.ready);
        mediaPlayer.start();
        // set Title
        arlertDialogBuiler.setTitle("");

        // set dialog Message
        arlertDialogBuiler.setMessage("Bạn đã sẵn sàng chơi ?").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                btnPlay.setClickable(false);
                btnHelp.setClickable(false);
                btnAbout.setClickable(false);
                mediaPlayer.stop();
                setSound(R.raw.gofind);
                Toast toast=Toast.makeText(MainActivity.this, "Chúng ta bắt đầu đi tìm Ai Là Triệu Phú ",   Toast.LENGTH_LONG);
                toast.show();
                btnPlay.setBackgroundResource(R.drawable.case3);
                new CountDownTimer(3000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        MainActivity.this.startActivity(myIntent);
                        btnPlay.setBackgroundResource(R.drawable.button_press);
                        btnPlay.setClickable(true);
                        btnHelp.setClickable(true);
                        btnAbout.setClickable(true);
                    }
                }.start();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
                mediaPlayer.stop();
                btnPlay.setClickable(true);
                btnHelp.setClickable(true);
                btnAbout.setClickable(true);
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
                stopService(intent);
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
    public void setSound(Integer id){
        mediaPlayer = new MediaPlayer().create(getApplicationContext(), id);
        mediaPlayer.start();
    }
}
