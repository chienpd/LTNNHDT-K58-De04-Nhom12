package chienphamk58.ailatrieuphu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;

public class Main2Activity extends AppCompatActivity {
    Intent intent;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansID, ansCorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        intent = new Intent(Main2Activity.this, PlaySongServiceLevel1.class);
        startService(intent);
        final TextView myCounter = (TextView)findViewById(R.id.textView3);
        countDownTimer = new CountDownTimer(601000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                myCounter.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {

            }
        }.start();
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        qst = databaseAccess.getQuestion(0);
        ansA = databaseAccess.getQuestion(3);
        ansB = databaseAccess.getQuestion(4);
        ansC = databaseAccess.getQuestion(5);
        ansD = databaseAccess.getQuestion(6);
        databaseAccess.close();
        Button btna = (Button)findViewById(R.id.buttonD);
        Button btnb = (Button)findViewById(R.id.buttonB);
        Button btnc = (Button)findViewById(R.id.buttonC);
        Button btnd = (Button)findViewById(R.id.buttonD);

        TextView question = (TextView)findViewById(R.id.textView5);
        question.setText(qst.get(0));

        btna.setText("A. " + ansA.get(0));
        btnb.setText("B. " + ansB.get(0));
        btnc.setText("C. " + ansC.get(0));
        btnd.setText("D. " + ansD.get(0));

    }

    public void khangia(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //final ImageButton button = (ImageButton)findViewById(R.id.imageButton2);
        builder.setMessage("Are you sure you want to use hoi y kien khan gia?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Exit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit and save your money?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Main2Activity.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fifty(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //final ImageButton button = (ImageButton)findViewById(R.id.imageButton2);
        builder.setMessage("Are you sure you want to use 50:50?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


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
        builder.setMessage("Are you sure you want to exit and save your money?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Main2Activity.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    /*
    public void click(View view){
        Button btn = (Button)findViewById(R.id.button5);
        btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.case_correct));
    }*/
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
