package chienphamk58.ailatrieuphu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;

import static chienphamk58.ailatrieuphu.R.drawable.button_press;

public class Main2Activity extends AppCompatActivity {
    Intent intent;
    Integer level = 1;
    Integer chooseNum = 0,i;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansCorrect;
    static AlertDialog alertDialog;


    static Button btna;
    static Button btnb;
    static Button btnc;
    static Button btnd;
    ImageButton change;
    static TextView question;
    MediaPlayer mediaPlayer;
    Button b;
    TextView levelstr,money;

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
        money = (TextView)findViewById(R.id.textView2);
        change = (ImageButton)findViewById(R.id.imageButton4);


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

    public void changeQuestion(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Integer j = i;
        //final ImageButton button = (ImageButton)findViewById(R.id.imageButton2);
        builder.setMessage("Are you sure you want to use change question?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            while (j==i){
                PlayGame();
            }

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        change.setBackgroundResource(R.drawable.switch3);
        change.setClickable(false);
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
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you choose " + answer +  " ?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                switch (b.getId()){
                    case R.id.buttonA :
                        chooseNum = 1;
                        break;
                    case R.id.buttonB:
                        chooseNum = 2;
                        break;
                    case R.id.buttonC:
                        chooseNum = 3;
                        break;
                    case R.id.buttonD:
                        chooseNum = 4;
                        break;
                    default:
                        chooseNum = 0;
                }
                PlayGame();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseNum = 0;
                mediaPlayer.stop();
                dialog.cancel();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void setSound(Integer id){
        mediaPlayer = new MediaPlayer().create(getApplicationContext(), id);
        mediaPlayer.start();
    }
    public void PlayGame() {
        if (chooseNum != 0) {
            switch (Integer.parseInt(ansCorrect.get(i))) {
                case 1:
                    if (chooseNum == 1) {
                        setSound(R.raw.true_a);
                    } else {
                        setSound(R.raw.lose_a);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    if (chooseNum == 2) {
                        setSound(R.raw.true_b);
                    } else {
                        setSound(R.raw.lose_b);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if (chooseNum == 3) {
                        setSound(R.raw.true_c);
                    } else {
                        setSound(R.raw.lose_c);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    if (chooseNum == 4) {
                        setSound(R.raw.true_d);
                    } else {
                        setSound(R.raw.lose_d);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            level++;
        }

            chooseNum = 0;
            levelstr.setText("Câu hỏi số " + level.toString());
            switch (level) {
                case 1:
                    money.setText("1");
                    setSound(R.raw.ques01);
                    i = getRandom(0, 412);
                    setAnswer(i);
                    break;
                case 2:
                    money.setText("1000");
                    setSound(R.raw.ques02);
                    i = getRandom(413, 715);
                    setAnswer(i);
                    break;

                case 3:
                    money.setText("2000");
                    setSound(R.raw.ques03);
                    i = getRandom(716, 961);
                    setAnswer(i);
                    break;
                case 4:
                    money.setText("3000");
                    setSound(R.raw.ques04);
                    i = getRandom(962, 1269);
                    setAnswer(i);
                    break;
                case 5:
                    money.setText("5000");
                    setSound(R.raw.ques05);
                    i = getRandom(1270, 1591);
                    setAnswer(i);
                    break;
                case 6:
                    money.setText("10000");
                    setSound(R.raw.ques06);
                    i = getRandom(1592, 1991);
                    setAnswer(i);
                    break;
                case 7:
                    money.setText("20000");
                    setSound(R.raw.ques07);
                    i = getRandom(1992, 2395);
                    setAnswer(i);
                    break;
                case 8:
                    money.setText("30000");
                    setSound(R.raw.ques08);
                    i = getRandom(2396, 2798);
                    setAnswer(i);
                    break;
                case 9:
                    money.setText("60000");
                    setSound(R.raw.ques09);
                    i = getRandom(2799, 3184);
                    setAnswer(i);
                    break;
                case 10:
                    money.setText("90000");
                    setSound(R.raw.ques10);
                    i = getRandom(3185, 3441);
                    setAnswer(i);
                    break;
                case 11:
                    money.setText("150000");
                    setSound(R.raw.ques11);
                    i = getRandom(3442, 3653);
                    setAnswer(i);
                    break;
                case 12:
                    money.setText("250000");
                    setSound(R.raw.ques12);
                    i = getRandom(3654, 3839);
                    setAnswer(i);
                    break;
                case 13:
                    money.setText("350000");
                    setSound(R.raw.ques13);
                    i = getRandom(3840, 4011);
                    setAnswer(i);
                    break;
                case 14:
                    money.setText("500000");
                    setSound(R.raw.ques14);
                    i = getRandom(4012, 4169);
                    setAnswer(i);
                    break;
                case 15:
                    money.setText("800000");
                    setSound(R.raw.ques15);
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
