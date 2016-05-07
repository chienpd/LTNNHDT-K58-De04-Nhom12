package chienphamk58.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;
public class Main2Activity extends AppCompatActivity {
    PlaySound playSound = new PlaySound();
    Intent intent;
    Integer level = 1, moneyStr = 0, chooseNum = 0,i, j = 0, one, two;
    Boolean x,y,z,t;
    CountDownTimer countDownTimer2;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansCorrect;
    AlertDialog alertDialog;
    Button btna, btnb,btnc, btnd;
    ImageButton change, ask, fifty, call;
    TextView question;
    Button b;
    TextView levelstr,money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        intent = new Intent(getApplicationContext(), PlaySongServiceLevel1.class);
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
        fifty = (ImageButton)findViewById(R.id.imageButton);
        call = (ImageButton)findViewById(R.id.imageButton2);

        x = fifty.isClickable();
        y = call.isClickable();
        z = ask.isClickable();
        t = change.isClickable();

        final TextView myCounter = (TextView)findViewById(R.id.textView3);

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

        Question();
        countDownTimer2 = new CountDownTimer(601000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                myCounter.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            @Override
            public void onFinish() {
                playSound.mediaPlayer.stop();
                playSound.setSound(getApplicationContext(),R.raw.out_of_time);
                new CountDownTimer(2000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        Fail();
                    }
                }.start();
            }
        }.start();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        playSound.mediaPlayer.stop();
        playSound.setSound(getApplicationContext(),R.raw.lose);
        if(level == 1) {
            money.setText("1");
            moneyStr = 1;
        }
        builder.setMessage("Bạn muốn tạm dừng cuộc chơi và nhận số tiền " + moneyStr +"$ của chương trình ?").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Main2Activity.this.finish();
                stopService(intent);
            }
        }).setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                playSound.mediaPlayer.stop();
                if(level == 1)
                    money.setText("0");
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fifty(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp 50:50").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                playSound.setSound(getApplicationContext(),R.raw.sound5050);

                btna.setClickable(false);
                btnb.setClickable(false);
                btnc.setClickable(false);
                btnd.setClickable(false);

                if(change.isClickable() && ask.isClickable()){
                    change.setClickable(false);
                    ask.setClickable(false);
                    new CountDownTimer(5000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            random50_50();
                            change.setClickable(true);
                            ask.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                            getButton(one).setClickable(true);
                            getButton(two).setClickable(true);
                        }
                    }.start();
                }
                else if(change.isClickable() && !ask.isClickable()){
                    change.setClickable(false);
                    new CountDownTimer(5000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            random50_50();
                            change.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                            getButton(one).setClickable(true);
                            getButton(two).setClickable(true);
                        }
                    }.start();
                }
                else if(!change.isClickable() && ask.isClickable()){
                    ask.setClickable(false);
                    new CountDownTimer(5000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            random50_50();
                            ask.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                            getButton(one).setClickable(true);
                            getButton(two).setClickable(true);
                        }
                    }.start();
                }
                else{
                    new CountDownTimer(5000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            random50_50();
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                            getButton(one).setClickable(true);
                            getButton(two).setClickable(true);
                        }
                    }.start();
                }
                fifty.setBackgroundResource(R.drawable.fifty2);
                fifty.setClickable(false);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void changeQuestion(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Integer j = i;
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp đổi câu hỏi").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                while (j==i){
                    Question();
                }
                change.setBackgroundResource(R.drawable.switch3);
                change.setClickable(false);
                Toast toast=Toast.makeText(Main2Activity.this, "Câu hỏi đã được đổi",   Toast.LENGTH_SHORT);
                toast.show();

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
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp của khán giả trong trường quay ?").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final Dialog dialog1 = new Dialog(Main2Activity.this);
                dialog1.setTitle("Hỏi ý kiến khán giả");
                Integer A,B,C,D;
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

                playSound.setSound(getApplicationContext(),R.raw.khan_gia);

                btna.setClickable(false);
                btnb.setClickable(false);
                btnc.setClickable(false);
                btnd.setClickable(false);

                if(change.isClickable()&&fifty.isClickable()){
                    change.setClickable(false);
                    fifty.setClickable(false);
                    new CountDownTimer(6000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            dialog1.show();
                            change.setClickable(true);
                            fifty.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                        }
                    }.start();
                }
                else if(change.isClickable()&&!fifty.isClickable()){
                    change.setClickable(false);
                    new CountDownTimer(6000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            dialog1.show();
                            change.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                        }
                    }.start();
                }

                else if(!change.isClickable()&&fifty.isClickable()){
                    fifty.setClickable(false);
                    new CountDownTimer(6000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            dialog1.show();
                            fifty.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                        }
                    }.start();
                }
                else {
                    new CountDownTimer(6000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            playSound.mediaPlayer.stop();
                            dialog1.show();
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                        }
                    }.start();
                }
                ask.setBackgroundResource(R.drawable.khangia2);
                ask.setClickable(false);
            }
        }).setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
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
        playSound.mediaPlayer.stop();
        switch (b.getId()){
            case R.id.buttonA :
                answer = "A";
                playSound.setSound(getApplicationContext(),R.raw.ans_a);
                btna.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonB:
                answer = "B";
                playSound.setSound(getApplicationContext(),R.raw.ans_b);
                btnb.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonC:
                answer = "C";
                playSound.setSound(getApplicationContext(),R.raw.ans_c);
                btnc.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonD:
                answer = "D";
                playSound.setSound(getApplicationContext(),R.raw.ans_d);
                btnd.setBackgroundResource(R.drawable.case3);
                break;
        }
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Phương án trả lời của bạn là " + answer +  " ?").setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int whick) {
                playSound.mediaPlayer.stop();
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

        alertDialogBuilder.setNegativeButton("Hủy bỏ",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseNum = 0;
                btna.setBackgroundResource(R.drawable.button_press);
                btnb.setBackgroundResource(R.drawable.button_press);
                btnc.setBackgroundResource(R.drawable.button_press);
                btnd.setBackgroundResource(R.drawable.button_press);
                playSound.mediaPlayer.stop();
            }
        });
        alertDialog = alertDialogBuilder.show();
    }
    /*
    public void playSound.setSound(getApplicationContext(),Integer id){
        playSound.mediaPlayer = new playSound.mediaPlayer().create(getApplicationContext(), id);
        playSound.mediaPlayer.start();
    }*/
    public void PlayGame() {
        btna.setClickable(false);
        btnb.setClickable(false);
        btnc.setClickable(false);
        btnd.setClickable(false);

        x = fifty.isClickable();
        y = call.isClickable();
        z = ask.isClickable();
        t = change.isClickable();

        fifty.setClickable(false);
        call.setClickable(false);
        ask.setClickable(false);
        change.setClickable(false);
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        if (chooseNum != 0) {
            playSound.mediaPlayer.stop();
            switch (Integer.parseInt(ansCorrect.get(i))) {
                case 1:
                    if (chooseNum == 1) {
                        level++;
                        playSound.setSound(getApplicationContext(),R.raw.true_a);
                    } else {
                        playSound.setSound(getApplicationContext(),R.raw.lose_a);
                    }
                    new CountDownTimer(3000,200){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            j++;
                            if(j%2 == 0)
                                if(chooseNum == 1)
                                    btna.setBackgroundResource(R.drawable.case_correct);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case_wrong);

                            else
                                if(chooseNum == 1)
                                    btna.setBackgroundResource(R.drawable.case3);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case3);
                        }
                        @Override
                        public void onFinish() {
                            j = 0;
                            if(chooseNum == 1)
                                Question();
                            else Fail();
                        }
                    }.start();
                    break;
                case 2:
                    if (chooseNum == 2) {
                        level++;
                        playSound.setSound(getApplicationContext(),R.raw.true_b);
                    } else {
                        playSound.setSound(getApplicationContext(),R.raw.lose_b);
                    }

                    new CountDownTimer(3000,200){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            j++;
                            if(j%2 == 0)
                                if(chooseNum == 2)
                                    btnb.setBackgroundResource(R.drawable.case_correct);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case_wrong);

                            else
                                if(chooseNum == 2)
                                    btnb.setBackgroundResource(R.drawable.case3);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case3);
                        }
                        @Override
                        public void onFinish() {
                            j = 0;
                            if(chooseNum == 2)
                                Question();
                            else Fail();
                        }
                    }.start();
                    break;
                case 3:
                    if (chooseNum == 3) {
                        level++;
                        playSound.setSound(getApplicationContext(),R.raw.true_c);
                    } else {
                        playSound.setSound(getApplicationContext(),R.raw.lose_c);
                    }

                    new CountDownTimer(3000,200){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            j++;
                            if(j%2 == 0)
                                if(chooseNum == 3)
                                    btnc.setBackgroundResource(R.drawable.case_correct);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case_wrong);

                            else
                                if(chooseNum == 3)
                                    btnc.setBackgroundResource(R.drawable.case3);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case3);
                        }
                        @Override
                        public void onFinish() {
                            j = 0;
                            if(chooseNum ==3)
                                Question();
                            else Fail();
                        }
                    }.start();

                    break;
                case 4:
                    if (chooseNum == 4) {
                        level++;
                        playSound.setSound(getApplicationContext(),R.raw.true_d);
                    } else {
                        playSound.setSound(getApplicationContext(),R.raw.lose_d);
                    }

                    new CountDownTimer(3000,200){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            j++;
                            if(j%2 == 0)
                                if(chooseNum == 4)
                                    btnd.setBackgroundResource(R.drawable.case_correct);
                                else
                                    getCorrectButton().setBackgroundResource(R.drawable.case_wrong);

                            else
                            if(chooseNum == 4)
                                btnd.setBackgroundResource(R.drawable.case3);
                            else
                                getCorrectButton().setBackgroundResource(R.drawable.case3);
                        }
                        @Override
                        public void onFinish() {
                            j = 0;
                            if(chooseNum == 4)
                                Question();
                            else Fail();
                        }
                    }.start();
                    break;
            }
        }


    }
    public void Question(){
        btna.setClickable(true);
        btnb.setClickable(true);
        btnc.setClickable(true);
        btnd.setClickable(true);
        if(x)
            fifty.setClickable(true);
        if(y)
            call.setClickable(true);
        if(z)
            ask.setClickable(true);
        if(t)
            change.setClickable(true);
        btna.setBackgroundResource(R.drawable.button_press);
        btnb.setBackgroundResource(R.drawable.button_press);
        btnc.setBackgroundResource(R.drawable.button_press);
        btnd.setBackgroundResource(R.drawable.button_press);
        chooseNum = 0;
        levelstr.setText("Câu hỏi số " + level.toString());
        switch (level) {
            case 1:
                playSound.setSound(getApplicationContext(),R.raw.ques01);
                i = getRandom(0, 412);
                setAnswer(i);
                break;
            case 2:
                moneyStr += 1000;
                playSound.setSound(getApplicationContext(),R.raw.ques02);
                i = getRandom(413, 715);
                setAnswer(i);
                break;

            case 3:
                moneyStr += 1000;
                playSound.setSound(getApplicationContext(),R.raw.ques03);
                i = getRandom(716, 961);
                setAnswer(i);
                break;
            case 4:
                moneyStr += 1000;
                playSound.setSound(getApplicationContext(),R.raw.ques04);
                i = getRandom(962, 1269);
                setAnswer(i);
                break;
            case 5:
                moneyStr += 2000;
                playSound.setSound(getApplicationContext(),R.raw.ques05);
                new CountDownTimer(2000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                   playSound.setSound(getApplicationContext(),R.raw.important);
                }
                }.start();
                i = getRandom(1270, 1591);
                setAnswer(i);
                break;
            case 6:
                moneyStr += 5000;
                playSound.setSound(getApplicationContext(),R.raw.ques06);
                i = getRandom(1592, 1991);
                setAnswer(i);
                break;
            case 7:
                moneyStr += 10000;
                playSound.setSound(getApplicationContext(),R.raw.ques07);
                i = getRandom(1992, 2395);
                setAnswer(i);
                break;
            case 8:
                moneyStr += 10000;
                playSound.setSound(getApplicationContext(),R.raw.ques08);
                i = getRandom(2396, 2798);
                setAnswer(i);
                break;
            case 9:
                moneyStr += 30000;
                playSound.setSound(getApplicationContext(),R.raw.ques09);
                i = getRandom(2799, 3184);
                setAnswer(i);
                break;
            case 10:
                moneyStr += 30000;
                playSound.setSound(getApplicationContext(),R.raw.ques10);
                new CountDownTimer(2000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                    playSound.setSound(getApplicationContext(),R.raw.important);
                }
                }.start();
                i = getRandom(3185, 3441);
                setAnswer(i);
                break;
            case 11:
                moneyStr += 60000;
                playSound.setSound(getApplicationContext(),R.raw.ques11);
                i = getRandom(3442, 3653);
                setAnswer(i);
                break;
            case 12:
                moneyStr += 100000;
                playSound.setSound(getApplicationContext(),R.raw.ques12);
                i = getRandom(3654, 3839);
                setAnswer(i);
                break;
            case 13:
                moneyStr += 100000;
                playSound.setSound(getApplicationContext(),R.raw.ques13);
                i = getRandom(3840, 4011);
                setAnswer(i);
                break;
            case 14:
                moneyStr += 150000;
                playSound.setSound(getApplicationContext(),R.raw.ques14);
                i = getRandom(4012, 4169);
                setAnswer(i);
                break;
            case 15:
                moneyStr += 300000;
                playSound.setSound(getApplicationContext(),R.raw.pass14);
                new CountDownTimer(4000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        playSound.setSound(getApplicationContext(),R.raw.ques15);
                        i = getRandom(4170, 4228);
                        setAnswer(i);
                    }
                }.start();
                break;
            case 16:
                moneyStr += 400000;
                money.setText(moneyStr.toString());
                playSound.setSound(getApplicationContext(),R.raw.best_player);
                new CountDownTimer(12000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                    Fail();
                }
                }.start();
                break;
        }
    }
    public int getRandom(int min, int max) {
        Random r = new Random();
        return min+r.nextInt(max-min);
    }
    public void random50_50(){
        do{
            one = getRandom(1,4);
        }while (one == Integer.parseInt(ansCorrect.get(i)));

        do{
            two = getRandom(1,4);
        }while (two == Integer.parseInt(ansCorrect.get(i)) || two == one);
        getButton(one).setText("");
        getButton(one).setClickable(false);
        getButton(two).setText("");
        getButton(two).setClickable(false);
    }

    public Button getButton(Integer i){
        Integer j = 0;
        switch (i){
            case 1:
                j = R.id.buttonA;
                break;
            case 2:
                j = R.id.buttonB;
                break;
            case 3:
                j = R.id.buttonC;
                break;
            case 4:
                j = R.id.buttonD;
                break;
        }
        return (Button)findViewById(j);
    }
    public void setAnswer(Integer i){
        money.setText(moneyStr.toString());
        question.setText(qst.get(i));
        btna.setText("A. " + ansA.get(i));
        btnb.setText("B. " + ansB.get(i));
        btnc.setText("C. " + ansC.get(i));
        btnd.setText("D. " + ansD.get(i));
    }
    public Button getCorrectButton(){
        Integer k = null;
        switch (Integer.parseInt(ansCorrect.get(i))){
            case 1:
                k = R.id.buttonA;
                break;
            case 2:
                k = R.id.buttonB;
                break;
            case 3:
                k =  R.id.buttonC;
                break;
            case 4:
               k =  R.id.buttonD;
                break;
        }
        return  (Button)findViewById(k);
    }
    public void Fail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        playSound.setSound(getApplicationContext(),R.raw.lose);
        countDownTimer2.cancel();
        if(level == 1) {
            money.setText("1");
            moneyStr = 1;
        }
        builder.setMessage("Bạn sẽ ra về với số tiền là " + moneyStr +"$\nBạn có muốn chơi lại ?").setCancelable(false).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moneyStr = 0;
                stopService(intent);
                playSound.mediaPlayer.stop();
                final Intent myIntent = new Intent(Main2Activity.this, Main2Activity.class);
                Main2Activity.this.finish();
                intent = new Intent(Main2Activity.this, PlaySongServiceLevel1_1.class);
                startService(intent);
                Main2Activity.this.startActivity(myIntent);

            }
        }).setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                stopService(intent);
                playSound.mediaPlayer.stop();
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
        playSound.mediaPlayer.stop();
        countDownTimer2.cancel();
    }
    @Override
    public void onPause(){
        super.onPause();
        stopService(intent);
        playSound.mediaPlayer.stop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(intent);
        playSound.mediaPlayer.stop();
        countDownTimer2.cancel();
    }
    @Override
    public void onResume(){
        super.onResume();
        startService(intent);
    }
}
