package chienphamk58.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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
    Integer level = 1, moneyStr = 0;
    Integer chooseNum = 0,i, j = 0;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansCorrect;
    static AlertDialog alertDialog;


    static Button btna;
    static Button btnb;
    static Button btnc;
    static Button btnd;
    ImageButton change, ask;
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
        ask = (ImageButton)findViewById(R.id.imageButton3);

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
        //
        Question();


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
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp đổi câu hỏi").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            while (j==i){
                Question();
            }
                change.setBackgroundResource(R.drawable.switch3);
                change.setClickable(false);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void khangia(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //final ImageButton button = (ImageButton)findViewById(R.id.imageButton2);

        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp của khán giả trong trường quay ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final Dialog dialog1 = new Dialog(Main2Activity.this);
                dialog1.setTitle("Hỏi ý kiến khán giả");
                Integer A,B,C,D;
                // khởi tạo dialog
                dialog1.setContentView(R.layout.chart);
                ImageView imageViewA = (ImageView)dialog1.findViewById(R.id.imageView7);
                ImageView imageViewB = (ImageView)dialog1.findViewById(R.id.imageView9);
                ImageView imageViewC = (ImageView)dialog1.findViewById(R.id.imageView10);
                ImageView imageViewD = (ImageView)dialog1.findViewById(R.id.imageView11);
                TextView Atv = (TextView)dialog1.findViewById(R.id.textView17);
                TextView Btv = (TextView)dialog1.findViewById(R.id.textView18);
                TextView Ctv = (TextView)dialog1.findViewById(R.id.textView19);
                TextView Dtv = (TextView)dialog1.findViewById(R.id.textView20);

                switch (Integer.parseInt(ansCorrect.get(i))) {
                    case 1:
                        A = imageViewA.getLayoutParams().height = getRandom(40,100)*3;
                        A /= 3;
                        Atv.setText(A.toString()+"%");
                        B = imageViewB.getLayoutParams().height = getRandom(0,100 - A)*3;
                        B /= 3;
                        Btv.setText(B.toString()+"%");
                        C = imageViewC.getLayoutParams().height = getRandom(0,100 - A - B )*3;
                        C /= 3;
                        Ctv.setText(C.toString()+"%");
                        D = imageViewD.getLayoutParams().height = (100 - A - B - C)*3;
                        D /= 3;
                        Dtv.setText(D.toString()+"%");
                        break;

                    case 2:
                        B = imageViewB.getLayoutParams().height = getRandom(40,100)*3;
                        B /= 3;
                        Btv.setText(B.toString()+"%");
                        A = imageViewA.getLayoutParams().height = getRandom(0,100 - B)*3;
                        A /= 3;
                        Atv.setText(A.toString()+"%");
                        C = imageViewC.getLayoutParams().height = getRandom(0,100 - A - B )*3;
                        C /= 3;
                        Ctv.setText(C.toString()+"%");
                        D = imageViewD.getLayoutParams().height = (100 - A - B - C)*3;
                        D /= 3;
                        Dtv.setText(D.toString()+"%");
                        break;
                    case 3:
                        C = imageViewC.getLayoutParams().height = getRandom(40,100)*3;
                        C /= 3;
                        Ctv.setText(C.toString()+"%");
                        A = imageViewA.getLayoutParams().height = getRandom(0,100 - C)*3;
                        A /= 3;
                        Atv.setText(A.toString()+"%");
                        B = imageViewB.getLayoutParams().height = getRandom(0,100 - A - C)*3;
                        B /= 3;
                        Btv.setText(B.toString()+"%");
                        D = imageViewD.getLayoutParams().height = (100 - A - B - C)*3;
                        D /= 3;
                        Dtv.setText(D.toString()+"%");
                        break;
                    case 4:
                        D = imageViewD.getLayoutParams().height = getRandom(40,100)*3;
                        D /= 3;
                        Dtv.setText(D.toString()+"%");
                        A = imageViewA.getLayoutParams().height = getRandom(0,100 - D)*3;
                        A /= 3;
                        Atv.setText(A.toString()+"%");
                        B = imageViewB.getLayoutParams().height = getRandom(0,100 - A - D)*3;
                        B /= 3;
                        Btv.setText(B.toString()+"%");
                        C = imageViewC.getLayoutParams().height = (100 - A - B - D)*3;
                        C /= 3;
                        Ctv.setText(C.toString()+"%");
                        break;
                }

                dialog1.show();
                // hiển thị dialog
                ask.setBackgroundResource(R.drawable.khangia2);
                ask.setClickable(false);

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
        builder.setMessage("Bạn muốn tạm dừng cuộc chơi và nhận số tiền " + moneyStr +"$ của chương trình ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
        alertDialogBuilder.setMessage("Phương án trả lời của bạn là " + answer +  " ?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int whick) {
                mediaPlayer.stop();
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
                }if(alertDialog != null && alertDialog.isShowing()){
                    alertDialog.dismiss();
                }
                new CountDownTimer(1,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        dialog.dismiss();
                        if(alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFinish() {
                        PlayGame();
                    }
                }.start();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseNum = 0;
                mediaPlayer.stop();
            }
        });
        alertDialog = alertDialogBuilder.show();
    }


    public void setSound(Integer id){
        mediaPlayer = new MediaPlayer().create(getApplicationContext(), id);
        mediaPlayer.start();
    }


    public void PlayGame() {
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        if (chooseNum != 0) {
            mediaPlayer.stop();
            switch (Integer.parseInt(ansCorrect.get(i))) {
                case 1:
                    if (chooseNum == 1) {
                        setSound(R.raw.true_a);
                    } else {
                        setSound(R.raw.lose_a);
                        Fail();
                    }
                    new CountDownTimer(3000,500){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btna.setBackgroundResource(R.drawable.case_wrong);
                        }
                        @Override
                        public void onFinish() {
                            if(chooseNum == 1)
                                Question();
                        }
                    }.start();
                    break;
                case 2:
                    if (chooseNum == 2) {
                        setSound(R.raw.true_b);
                    } else {
                        setSound(R.raw.lose_b);
                        Fail();
                    }

                    new CountDownTimer(3000,500){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnb.setBackgroundResource(R.drawable.case_wrong);
                        }
                        @Override
                        public void onFinish() {

                            if(chooseNum == 2)
                                Question();
                        }
                    }.start();
                    break;
                case 3:
                    if (chooseNum == 3) {
                        setSound(R.raw.true_c);
                    } else {
                        setSound(R.raw.lose_c);
                        Fail();
                    }

                    new CountDownTimer(3000,500){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnc.setBackgroundResource(R.drawable.case_wrong);
                        }
                        @Override
                        public void onFinish() {

                            if(chooseNum ==3)
                                Question();
                        }
                    }.start();

                    break;
                case 4:
                    if (chooseNum == 4) {
                        setSound(R.raw.true_d);
                    } else {
                        setSound(R.raw.lose_d);
                        Fail();
                    }

                    new CountDownTimer(3000,500){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnd.setBackgroundResource(R.drawable.case_wrong);
                        }
                        @Override
                        public void onFinish() {

                            if(chooseNum == 4)
                                Question();
                        }
                    }.start();
                    break;
            }
            level++;
        }


        }
public void Question(){
    btna.setBackgroundResource(R.drawable.case2);
    btnb.setBackgroundResource(R.drawable.case2);
    btnc.setBackgroundResource(R.drawable.case2);
    btnd.setBackgroundResource(R.drawable.case2);
    chooseNum = 0;
    levelstr.setText("Câu hỏi số " + level.toString());
    switch (level) {
        case 1:
            money.setText(moneyStr.toString());
            setSound(R.raw.ques01);
            i = getRandom(0, 412);
            setAnswer(i);
            break;
        case 2:
            moneyStr += 1000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques02);
            i = getRandom(413, 715);
            setAnswer(i);
            break;

        case 3:
            moneyStr += 1000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques03);
            i = getRandom(716, 961);
            setAnswer(i);
            break;
        case 4:
            moneyStr += 1000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques04);
            i = getRandom(962, 1269);
            setAnswer(i);
            break;
        case 5:
            moneyStr += 2000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques05);
            i = getRandom(1270, 1591);
            setAnswer(i);
            break;
        case 6:
            moneyStr += 5000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques06);
            i = getRandom(1592, 1991);
            setAnswer(i);
            break;
        case 7:
            moneyStr += 10000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques07);
            i = getRandom(1992, 2395);
            setAnswer(i);
            break;
        case 8:
            moneyStr += 10000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques08);
            i = getRandom(2396, 2798);
            setAnswer(i);
            break;
        case 9:
            moneyStr += 30000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques09);
            i = getRandom(2799, 3184);
            setAnswer(i);
            break;
        case 10:
            moneyStr += 60000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques10);
            i = getRandom(3185, 3441);
            setAnswer(i);
            break;
        case 11:
            moneyStr += 60000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques11);
            i = getRandom(3442, 3653);
            setAnswer(i);
            break;
        case 12:
            moneyStr += 100000;
            money.setText(moneyStr.toString());
            setSound(R.raw.ques12);
            i = getRandom(3654, 3839);
            setAnswer(i);
            break;
        case 13:
            moneyStr += 200000;
            money.setText(moneyStr.toString());
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

    public void Fail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Intent myIntent = new Intent(Main2Activity.this, Main2Activity.class);
        if(level == 1) {
            money.setText("1");
            moneyStr = 1;
        }
        builder.setMessage("Bạn sẽ ra về với số tiền " + moneyStr +"$\nBạn có muốn chơi lại ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Main2Activity.this.finish();
                Main2Activity.this.startActivity(myIntent);
                startService(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Main2Activity.this.finish();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.show();
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
