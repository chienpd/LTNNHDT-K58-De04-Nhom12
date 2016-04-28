package chienphamk58.ailatrieuphu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;

public class Main2Activity extends AppCompatActivity {
    Intent intent;
    Integer level = 0, money = 1;
    Integer chooseId;
    Integer chooseNum = 0,i,correct=0;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansCorrect;


    Button btna;
    Button btnb;
    Button btnc;
    Button btnd;
    TextView question;
    MediaPlayer mediaPlayer;
    Button b;
    TextView levelstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        intent = new Intent(Main2Activity.this, PlaySongServiceLevel1.class);
        startService(intent);

        btna = (Button)findViewById(R.id.buttonA);
        btnb = (Button)findViewById(R.id.buttonB);
        btnc = (Button)findViewById(R.id.buttonC);
        btnd = (Button)findViewById(R.id.buttonD);
        question = (TextView)findViewById(R.id.textView5);
        levelstr = (TextView)findViewById(R.id.textView4);


        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.getData();

        qst = databaseAccess.list1;
        ansA = databaseAccess.list2;
        ansB = databaseAccess.list3;
        ansC = databaseAccess.list4;
        ansD = databaseAccess.list5;
        ansCorrect = databaseAccess.list6;
        databaseAccess.close();
        PlayGame();


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
    }


    public int getRandom(int min, int max)
    {
        Random r = new Random();
        return min+r.nextInt(max-min);
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


    public void onClick(View view) throws InterruptedException {
        b = (Button)view;
        String answer = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (b.getId()){
            case R.id.buttonA :
                answer = "A";
                setSound(R.raw.ans_a);
                break;
            case R.id.buttonB:
                answer = "B";
                setSound(R.raw.ans_b);
                break;
            case R.id.buttonC:
                answer = "C";
                setSound(R.raw.ans_c);
                break;
            case R.id.buttonD:
                answer = "D";
                setSound(R.raw.ans_d);
                break;
        }

        builder.setMessage("Are you sure you choose " +answer+  " ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (b.getId()){
                    case R.id.buttonA :
                        chooseNum = 1;
                        if (Integer.parseInt(ansCorrect.get(i)) == chooseNum){
                            setSound(R.raw.true_a);
                            PlayGame();
                        }else{
                            setSound(R.raw.lose_a);
                        }
                        break;
                    case R.id.buttonB:
                        chooseNum = 2;
                        if (Integer.parseInt(ansCorrect.get(i)) == chooseNum){
                            setSound(R.raw.true_b);
                            PlayGame();
                        }else {
                            setSound(R.raw.lose_b);
                        }
                        break;
                    case R.id.buttonC:
                        chooseNum = 3;
                        if (Integer.parseInt(ansCorrect.get(i)) == chooseNum){
                            setSound(R.raw.true_c);
                            PlayGame();

                        }else {
                            setSound(R.raw.lose_c);
                        }
                        break;
                    case R.id.buttonD:
                        chooseNum = 4;
                        if (Integer.parseInt(ansCorrect.get(i)) == chooseNum){
                            setSound(R.raw.true_d);
                            PlayGame();
                        }else {
                            setSound(R.raw.lose_d);
                        }
                        break;
                    }
                dialog.dismiss();
                }

        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                chooseNum = 0;
                mediaPlayer.stop();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void setSound(Integer id){
        mediaPlayer = new MediaPlayer().create(getApplicationContext(), id);
        mediaPlayer.start();
    }
    public void PlayGame() {
        chooseNum = 0;
        level++;
        levelstr.setText("Câu hỏi số " + level.toString());
        switch (level) {
            case 1:
                setSound(R.raw.ques01);
                i = getRandom(0, 412);
                setAnswer(i);
                break;
            case 2:
                setSound(R.raw.ques02);
                i = getRandom(413, 715);
                setAnswer(i);
                break;
            case 3:
                setSound(R.raw.ques03);
                i = getRandom(716, 961);
               setAnswer(i);
                break;
            case 4:
                setSound(R.raw.ques04);
                i = getRandom(962, 1269);
                setAnswer(i);
                break;
            case 5:
                setSound(R.raw.ques05);
                i = getRandom(1270, 1591);
                setAnswer(i);
                break;
            case 6:
                setSound(R.raw.ques06);
                i = getRandom(1592, 1991);
                setAnswer(i);
                break;
            case 7:
                setSound(R.raw.ques07);
                i = getRandom(1992, 2395);
                setAnswer(i);
                break;
            case 8:
                setSound(R.raw.ques08);
                i = getRandom(2396, 2798);
                setAnswer(i);
                break;
            case 9:
                setSound(R.raw.ques09);
                i = getRandom(2799, 3184);
                setAnswer(i);
                break;
            case 10:
                setSound(R.raw.ques10);
                i = getRandom(3185, 3441);
                setAnswer(i);
                break;
            case 11:
                setSound(R.raw.ques11);
                i = getRandom(3442, 3653);
                setAnswer(i);
                break;
            case 12:
                setSound(R.raw.ques12);
                i = getRandom(3654, 3839);
                setAnswer(i);
                break;
            case 13:
                setSound(R.raw.ques13);
                i = getRandom(3840, 4011);
                setAnswer(i);
                break;
            case 14:
                setSound(R.raw.ques14);
                i = getRandom(4012, 4169);
                setAnswer(i);
                break;
            case 15:
                setSound(R.raw.ques06);
                i = getRandom(4170, 4228);
                setAnswer(i);
                break;

        }
    }

    public void setAnswer(Integer i){
        question.setText(qst.get(i));
        btna.setText("A. " + ansA.get(i));
        btnb.setText("B. " + ansB.get(i));
        btnc.setText("C. " + ansC.get(i));
        btnd.setText("D. " + ansD.get(i));
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
