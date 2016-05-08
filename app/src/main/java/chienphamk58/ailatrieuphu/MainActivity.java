package chienphamk58.ailatrieuphu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
public class MainActivity extends Activity {
    Intent intent;
    PlaySound playSound = new PlaySound();
    Button btnPlay, btnHelp, btnAbout;
    ImageButton btnMute, btnDownload, btnShare, btnExit;
    static ImageButton sound;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

            intent = new Intent(MainActivity.this, PlaySongService.class);
        if(playSound.mutesound)
            startService(intent);


        btnMute = (ImageButton)findViewById(R.id.imageButton5);
        btnDownload = (ImageButton)findViewById(R.id.imageButton6);
        btnShare = (ImageButton)findViewById(R.id.imageButton7);
        btnExit = (ImageButton)findViewById(R.id.imageButton8);

        btnPlay = (Button)findViewById(R.id.button1);
        btnHelp = (Button)findViewById(R.id.button2);
        btnAbout = (Button)findViewById(R.id.button4);
        sound = (ImageButton)findViewById(R.id.imageButton5);
        if(!playSound.mutesound)
            sound.setBackgroundResource(R.drawable.sound_mute);
    }
    public void Play(View view){
        AlertDialog.Builder arlertDialogBuiler = new AlertDialog.Builder(this);
        final Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        if(playSound.mutesound)
            playSound.setSound(getApplicationContext(),R.raw.ready);
        // set Title
        arlertDialogBuiler.setTitle("");

        // set dialog Message
        arlertDialogBuiler.setMessage("Bạn đã sẵn sàng chơi ?").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                btnPlay.setClickable(false);
                btnHelp.setClickable(false);
                btnAbout.setClickable(false);
                btnExit.setClickable(false);
                btnMute.setClickable(false);
                btnDownload.setClickable(false);
                btnShare.setClickable(false);
                btnExit.setClickable(false);
                Integer wait = 2000;

                if(playSound.mutesound) {
                    wait = 3000;
                    playSound.mediaPlayer.stop();
                    playSound.setSound(getApplicationContext(), R.raw.gofind);
                }
                Toast toast=Toast.makeText(MainActivity.this, "Chúng ta bắt đầu đi tìm Ai Là Triệu Phú ",   Toast.LENGTH_LONG);
                toast.show();
                btnPlay.setBackgroundResource(R.drawable.case3);
                new CountDownTimer(wait,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        MainActivity.this.startActivity(myIntent);
                    }
                }.start();
                new CountDownTimer(4000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        btnPlay.setBackgroundResource(R.drawable.button_press);
                        btnPlay.setClickable(true);
                        btnHelp.setClickable(true);
                        btnAbout.setClickable(true);
                        btnExit.setClickable(true);
                        btnMute.setClickable(true);
                        btnDownload.setClickable(true);
                        btnShare.setClickable(true);
                    }
                }.start();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
                if(playSound.mutesound)
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
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

    public void Mute(View view){
        if(playSound.mutesound){
            sound.setBackgroundResource(R.drawable.sound_mute);
            stopService(intent);
            playSound.mutesound = false;
        }
        else {
            sound.setBackgroundResource(R.drawable.sound);
            startService(intent);
            playSound.mutesound = true;
        }
    }
    public void Exit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
                if(playSound.mutesound)
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
                if(playSound.mutesound)
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
        if(playSound.mutesound)
            startService(intent);
    }
}
