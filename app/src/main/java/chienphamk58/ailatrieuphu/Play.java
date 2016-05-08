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
import chienphamk58.ailatrieuphu.Sound_Music.PlaySongServiceLevel1;
import chienphamk58.ailatrieuphu.Sound_Music.PlaySongServiceLevel1_1;
import chienphamk58.ailatrieuphu.Sound_Music.PlaySound;


public class Play extends AppCompatActivity {
    Dialog dialog2, dialog3;
    PlaySound playSound = new PlaySound();
    Intent intent;
    Integer level = 1, moneyStr = 0, chooseNum = 0,i, j = 0, one, two;
    Boolean x,y,z,t,w;
    CountDownTimer countDownTimer2, countDownTimer;
    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansCorrect;
    AlertDialog alertDialog;
    Button btna, btnb,btnc, btnd;
    ImageButton change, ask, fifty, call, pause;
    TextView question;
    Button b;
    TextView levelstr,money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        intent = new Intent(getApplicationContext(), PlaySongServiceLevel1.class);
        if(playSound.mutesound)
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
        pause = (ImageButton)findViewById(R.id.imageButton13);

        x = fifty.isClickable();
        y = call.isClickable();
        z = ask.isClickable();
        t = change.isClickable();
        w = pause.isClickable();

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
                countDownTimer.cancel();
                if(playSound.mutesound){
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
                    playSound.setSound(getApplicationContext(),R.raw.out_of_time);
                }
                new CountDownTimer(1500,1000){
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

    public void pause(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(playSound.mutesound) {
            if(playSound.mediaPlayer.isPlaying())
                playSound.mediaPlayer.stop();
            playSound.setSound(getApplicationContext(), R.raw.lose);
        }
        if(level == 1) {
            money.setText("1");
            moneyStr = 1;
        }
        builder.setMessage("Bạn muốn tạm dừng cuộc chơi và nhận số tiền " + moneyStr +"$ của chương trình ?").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //countDownTimer.cancel();
                countDownTimer2.cancel();
                Play.this.finish();
                if(playSound.mutesound) {
                    stopService(intent);
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
                }
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(playSound.mutesound)
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
                if(level == 1) {
                    money.setText("0");
                    moneyStr = 0;
                }
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }   // bat su kien cho button Tam dung cuoc choi
    public void fifty(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp 50:50").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Integer i = 2000;
                if(playSound.mutesound) {
                    i = 5000;
                    playSound.setSound(getApplicationContext(), R.raw.sound5050);
                }
                btna.setClickable(false);
                btnb.setClickable(false);
                btnc.setClickable(false);
                btnd.setClickable(false);

                change.setClickable(false);
                ask.setClickable(false);
                call.setClickable(false);
                pause.setClickable(false);
                    countDownTimer = new CountDownTimer(i,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            if(playSound.mutesound)
                                playSound.mediaPlayer.stop();
                            random50_50();
                            pause.setClickable(true);
                            if(y)
                                call.setClickable(true);
                            if(z)
                                ask.setClickable(true);
                            if(t)
                                change.setClickable(true);
                            btna.setClickable(true);
                            btnb.setClickable(true);
                            btnc.setClickable(true);
                            btnd.setClickable(true);
                            getButton(one).setClickable(false);
                            getButton(two).setClickable(false);
                        }
                    }.start();
                fifty.setBackgroundResource(R.drawable.fifty2);
                fifty.setClickable(false);

            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }   // bat su kien cho button tro giup 50:50
    public void call(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp xin tư vấn từ chuyên gia?").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog2 = new Dialog(Play.this);
                dialog2.setTitle("Lựa chọn chuyên gia");
                dialog2.setContentView(R.layout.tuvan);
                dialog2.setCancelable(false);
                dialog2.show();

                btna.setClickable(false);
                btnb.setClickable(false);
                btnc.setClickable(false);
                btnd.setClickable(false);

                fifty.setClickable(false);
                ask.setClickable(false);
                pause.setClickable(false);
                change.setClickable(false);

                call.setBackgroundResource(R.drawable.call2);
                call.setClickable(false);

                dialog3 = new Dialog(Play.this);
                dialog3.setCancelable(false);
                dialog3.setContentView(R.layout.chuyengia);
                final ImageView imageView = (ImageView)dialog3.findViewById(R.id.imageView12);
                Button button = (Button)dialog3.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog3.dismiss();
                        if(x)
                            fifty.setClickable(true);
                        if(z)
                            ask.setClickable(true);
                        if(t)
                            change.setClickable(true);
                        pause.setClickable(true);

                        if(getButton(one) != btna && getButton(two) != btna)
                            btna.setClickable(true);
                        if(getButton(one) != btnb && getButton(two) != btnb)
                            btnb.setClickable(true);
                        if(getButton(one) != btnc && getButton(two) != btnc)
                            btnc.setClickable(true);
                        if(getButton(one) != btnd && getButton(two) != btnd)
                            btnd.setClickable(true);
                    }
                });

                ImageButton imageButton1 = (ImageButton)dialog2.findViewById(R.id.imageButton9);
                imageButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer i = 1000;
                        if(playSound.mutesound) {
                            i = 3000;
                            playSound.setSound(getApplicationContext(), R.raw.call);
                        }
                        imageView.setBackgroundResource(R.mipmap.levanlan);
                        dialog3.setTitle("GS. Lê Văn Lan");
                        dialog2.dismiss();

                        countDownTimer = new CountDownTimer(i,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                dialog3.show();
                            }
                        }.start();

                    }
                });


                ImageButton imageButton2 = (ImageButton)dialog2.findViewById(R.id.imageButton10);
                imageButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer i = 1000;
                        if(playSound.mutesound) {
                            i = 3000;
                            playSound.setSound(getApplicationContext(), R.raw.call);
                        }
                        imageView.setBackgroundResource(R.mipmap.anhxtanh);
                        dialog3.setTitle("Albert Einstein");
                        dialog2.dismiss();
                        countDownTimer = new CountDownTimer(i,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                dialog3.show();
                            }
                        }.start();
                    }
                });

                ImageButton imageButton3 = (ImageButton)dialog2.findViewById(R.id.imageButton11);
                imageButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer i = 1000;
                        if(playSound.mutesound) {
                            i = 3000;
                            playSound.setSound(getApplicationContext(), R.raw.call);
                        }
                        imageView.setBackgroundResource(R.mipmap.ngobaochau);
                        dialog3.setTitle("GS. Ngô Bảo Châu");
                        dialog2.dismiss();
                        countDownTimer = new CountDownTimer(i,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                dialog3.show();
                            }
                        }.start();
                    }
                });

                ImageButton imageButton4 = (ImageButton)dialog2.findViewById(R.id.imageButton12);
                imageButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer i = 1000;
                        if(playSound.mutesound) {
                            i = 3000;
                            playSound.setSound(getApplicationContext(), R.raw.call);
                        }
                        imageView.setBackgroundResource(R.mipmap.markzukerbeg);
                        dialog3.setTitle("Cristiano Ronaldo");
                        dialog2.dismiss();
                        countDownTimer = new CountDownTimer(i,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                dialog3.show();
                            }
                        }.start();
                    }
                });

                TextView textView = (TextView)dialog3.findViewById(R.id.textView25);
                switch (Integer.parseInt(ansCorrect.get(i))) {
                    case 1:
                        textView.setText("Tôi đoán câu trả lời đúng là A");
                        break;
                    case 2:
                        textView.setText("Tôi đoán câu trả lời đúng là B");
                        break;
                    case 3:
                        textView.setText("Tôi đoán câu trả lời đúng là C");
                        break;
                    case 4:
                        textView.setText("Tôi đoán câu trả lời đúng là D");
                        break;
                }

            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }   // bat su kien cho button tro giup to tu van
    public void changeQuestion(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp đổi câu hỏi").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Integer j = i;
                while (j==i){
                    Question();
                }
                change.setBackgroundResource(R.drawable.switch3);
                change.setClickable(false);
                Toast toast=Toast.makeText(Play.this, "Câu hỏi đã được đổi",   Toast.LENGTH_SHORT);
                toast.show();

            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }   // bat su kien cho button tro doi cau hoi
    public void khangia(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn sử dụng sự trợ giúp của khán giả trong trường quay ?").setCancelable(false).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final Dialog dialog1 = new Dialog(Play.this);
                dialog1.setTitle("Tỉ lệ khán giả lựa chọn");
                dialog1.setCancelable(false);
                Integer A,B,C,D;
                dialog1.setContentView(R.layout.chart);

                ImageView imageViewA = (ImageView)dialog1.findViewById(R.id.imageView9);
                ImageView imageViewB = (ImageView)dialog1.findViewById(R.id.imageView7);
                ImageView imageViewC = (ImageView)dialog1.findViewById(R.id.imageView11);
                ImageView imageViewD = (ImageView)dialog1.findViewById(R.id.imageView10);
                TextView Atv = (TextView)dialog1.findViewById(R.id.textView14);
                TextView Btv = (TextView)dialog1.findViewById(R.id.textView13);
                TextView Ctv = (TextView)dialog1.findViewById(R.id.textView17);
                TextView Dtv = (TextView)dialog1.findViewById(R.id.textView19);
                Button button = (Button)dialog1.findViewById(R.id.button5);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                switch (Integer.parseInt(ansCorrect.get(i))) {
                    case 1:
                        A = imageViewA.getLayoutParams().height = getRandom(40,100)*3;
                        A /= 3;
                        Atv.setText(A.toString()+"%");

                        if(btnb.isClickable()){
                            B = imageViewB.getLayoutParams().height = getRandom(0,100 - A)*3;
                            B /= 3;
                        }else {
                            B = imageViewB.getLayoutParams().height = 0;
                        }
                        Btv.setText(B.toString()+"%");
                        if(btnc.isClickable()) {
                            C = imageViewC.getLayoutParams().height = getRandom(0, 100 - A - B) * 3;
                            C /= 3;
                        }else
                            C = imageViewC.getLayoutParams().height = 0;
                        Ctv.setText(C.toString()+"%");
                        if(btnd.isClickable()) {
                            D = imageViewD.getLayoutParams().height = (100 - A - B - C) * 3;
                            D /= 3;
                        }else
                            D = imageViewD.getLayoutParams().height = 0;
                        Dtv.setText(D.toString()+"%");
                        break;
                    case 2:
                        B = imageViewB.getLayoutParams().height = getRandom(40,100)*3;
                        B /= 3;
                        Btv.setText(B.toString()+"%");

                        if(btna.isClickable()) {
                            A = imageViewA.getLayoutParams().height = getRandom(0, 100 - B) * 3;
                            A /= 3;
                        }else
                            A = imageViewA.getLayoutParams().height = 0;
                        Atv.setText(A.toString()+"%");

                        if(btnc.isClickable()) {
                            C = imageViewC.getLayoutParams().height = getRandom(0, 100 - A - B) * 3;
                            C /= 3;
                        }else
                            C = imageViewC.getLayoutParams().height = 0;
                        Ctv.setText(C.toString()+"%");
                        if(btnd.isClickable()) {
                            D = imageViewD.getLayoutParams().height = (100 - A - B - C) * 3;
                            D /= 3;
                        }else
                            D = imageViewD.getLayoutParams().height = 0;
                        Dtv.setText(D.toString()+"%");
                        break;
                    case 3:
                        C = imageViewC.getLayoutParams().height = getRandom(40,100)*3;
                        C /= 3;
                        Ctv.setText(C.toString()+"%");

                        if(btna.isClickable()) {
                            A = imageViewA.getLayoutParams().height = getRandom(0, 100 - C) * 3;
                            A /= 3;
                        }else
                            A = imageViewA.getLayoutParams().height = 0;
                        Atv.setText(A.toString()+"%");

                        if(btnb.isClickable()) {
                            B = imageViewB.getLayoutParams().height = getRandom(0, 100 - A - C) * 3;
                            B /= 3;
                        }else
                            B = imageViewB.getLayoutParams().height = 0;
                        Btv.setText(B.toString()+"%");

                        if(btnd.isClickable()) {
                            D = imageViewD.getLayoutParams().height = (100 - A - B - C) * 3;
                            D /= 3;
                        }else
                            D = imageViewD.getLayoutParams().height = 0;
                        Dtv.setText(D.toString()+"%");
                        break;
                    case 4:
                        D = imageViewD.getLayoutParams().height = getRandom(40,100)*3;
                        D /= 3;
                        Dtv.setText(D.toString()+"%");

                        if(btna.isClickable()) {
                            A = imageViewA.getLayoutParams().height = getRandom(0, 100 - D) * 3;
                            A /= 3;
                        }else
                            A = imageViewA.getLayoutParams().height = 0;
                        Atv.setText(A.toString()+"%");

                        if (btnb.isClickable()) {
                            B = imageViewB.getLayoutParams().height = getRandom(0, 100 - A - D) * 3;
                            B /= 3;
                        }else
                            B = imageViewB.getLayoutParams().height = 0;
                        Btv.setText(B.toString()+"%");

                        if(btnc.isClickable()) {
                            C = imageViewC.getLayoutParams().height = (100 - A - B - D) * 3;
                            C /= 3;
                        }else
                            C = imageViewC.getLayoutParams().height = 0;
                        Ctv.setText(C.toString()+"%");
                        break;
                }


                btna.setClickable(false);
                btnb.setClickable(false);
                btnc.setClickable(false);
                btnd.setClickable(false);

                change.setClickable(false);
                call.setClickable(false);
                fifty.setClickable(false);
                pause.setClickable(false);

                Integer i = 2000;
                if(playSound.mutesound) {
                    i = 6000;
                    playSound.setSound(getApplicationContext(), R.raw.khan_gia);
                }

                    countDownTimer = new CountDownTimer(i,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            if(playSound.mutesound)
                                if(playSound.mediaPlayer.isPlaying())
                                    playSound.mediaPlayer.stop();
                            dialog1.show();
                            pause.setClickable(true);
                            if(x)
                                fifty.setClickable(true);
                            if(y)
                                call.setClickable(true);
                            if(z)
                                change.setClickable(true);
                            if(getButton(one) != btna && getButton(two) != btna)
                                btna.setClickable(true);
                            if(getButton(one) != btnb && getButton(two) != btnb)
                                btnb.setClickable(true);
                            if(getButton(one) != btnc && getButton(two) != btnc)
                                btnc.setClickable(true);
                            if(getButton(one) != btnd && getButton(two) != btnd)
                                btnd.setClickable(true);
                        }
                    }.start();
                ask.setBackgroundResource(R.drawable.khangia2);
                ask.setClickable(false);
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }       // bat su kien cho button tro giup hoi khan gia
    public void onClick(View view) throws InterruptedException {
        b = (Button)view;
        String answer = null;
        if(playSound.mutesound)
            playSound.mediaPlayer.stop();
        switch (b.getId()){
            case R.id.buttonA :
                answer = "A";
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ans_a);
                btna.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonB:
                answer = "B";
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ans_b);
                btnb.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonC:
                answer = "C";
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ans_c);
                btnc.setBackgroundResource(R.drawable.case3);
                break;
            case R.id.buttonD:
                answer = "D";
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ans_d);
                btnd.setBackgroundResource(R.drawable.case3);
                break;
        }
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Phương án trả lời của bạn là " + answer +  " ?").setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int whick) {
                if(playSound.mutesound)
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

        alertDialogBuilder.setNegativeButton("Không",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseNum = 0;
                btna.setBackgroundResource(R.drawable.button_press);
                btnb.setBackgroundResource(R.drawable.button_press);
                btnc.setBackgroundResource(R.drawable.button_press);
                btnd.setBackgroundResource(R.drawable.button_press);
                if(playSound.mutesound)
                    playSound.mediaPlayer.stop();
            }
        });
        alertDialog = alertDialogBuilder.show();
    }   // bat su kien cho button cau tra loi
    public void PlayGame() {
        btna.setClickable(false);
        btnb.setClickable(false);
        btnc.setClickable(false);
        btnd.setClickable(false);

        x = fifty.isClickable();
        y = call.isClickable();
        z = ask.isClickable();
        t = change.isClickable();
        w = pause.isClickable();

        fifty.setClickable(false);
        call.setClickable(false);
        ask.setClickable(false);
        change.setClickable(false);
        pause.setClickable(false);

        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        if (chooseNum != 0) {
            if(playSound.mutesound){
                if(playSound.mediaPlayer.isPlaying())
                    playSound.mediaPlayer.stop();
            }

            switch (Integer.parseInt(ansCorrect.get(i))) {
                case 1:
                    if (chooseNum == 1) {
                        level++;
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.true_a);
                    } else {
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.lose_a);
                    }
                    countDownTimer = new CountDownTimer(3000,200){
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
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.true_b);
                    } else {
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.lose_b);
                    }

                    countDownTimer = new CountDownTimer(3000,200){
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
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.true_c);
                    } else {
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.lose_c);
                    }

                    countDownTimer =  new CountDownTimer(3000,200){
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
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.true_d);
                    } else {
                        if(playSound.mutesound)
                            playSound.setSound(getApplicationContext(),R.raw.lose_d);
                    }

                    countDownTimer =  new CountDownTimer(3000,200){
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


    }   // kiem tra dap an
    public void Question(){
        one = 0;
        two = 0;
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
        pause.setClickable(true);
        btna.setBackgroundResource(R.drawable.button_press);
        btnb.setBackgroundResource(R.drawable.button_press);
        btnc.setBackgroundResource(R.drawable.button_press);
        btnd.setBackgroundResource(R.drawable.button_press);
        chooseNum = 0;
        levelstr.setText("Câu hỏi số " + level.toString());
        switch (level) {
            case 1:
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques01);
                i = getRandom(0, 412);
                setAnswer(i);
                break;
            case 2:
                moneyStr += 1000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques02);
                i = getRandom(413, 715);
                setAnswer(i);
                break;

            case 3:
                moneyStr += 1000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques03);
                i = getRandom(716, 961);
                setAnswer(i);
                break;
            case 4:
                moneyStr += 1000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques04);
                i = getRandom(962, 1269);
                setAnswer(i);
                break;
            case 5:
                moneyStr += 2000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques05);
                i = getRandom(1270, 1591);
                setAnswer(i);
                break;
            case 6:
                moneyStr += 5000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques06);
                i = getRandom(1592, 1991);
                setAnswer(i);
                break;
            case 7:
                moneyStr += 10000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques07);
                i = getRandom(1992, 2395);
                setAnswer(i);
                break;
            case 8:
                moneyStr += 10000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques08);
                i = getRandom(2396, 2798);
                setAnswer(i);
                break;
            case 9:
                moneyStr += 30000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques09);
                i = getRandom(2799, 3184);
                setAnswer(i);
                break;
            case 10:
                moneyStr += 30000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques10);
                i = getRandom(3185, 3441);
                setAnswer(i);
                break;
            case 11:
                moneyStr += 60000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques11);
                i = getRandom(3442, 3653);
                setAnswer(i);
                break;
            case 12:
                moneyStr += 100000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques12);
                i = getRandom(3654, 3839);
                setAnswer(i);
                break;
            case 13:
                moneyStr += 100000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques13);
                i = getRandom(3840, 4011);
                setAnswer(i);
                break;
            case 14:
                moneyStr += 150000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques14);
                i = getRandom(4012, 4169);
                setAnswer(i);
                break;
            case 15:
                moneyStr += 300000;
                if(playSound.mutesound)
                    playSound.setSound(getApplicationContext(),R.raw.ques15);
                i = getRandom(4170, 4228);
                setAnswer(i);
                break;
            case 16:
                moneyStr += 400000;
                money.setText(moneyStr.toString());
                Integer i = 1000;
                if(playSound.mutesound){
                    i = 12000;
                    playSound.setSound(getApplicationContext(),R.raw.best_player);
                }
                countDownTimer =  new CountDownTimer(i,1000){
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
    }   // dua ra cau hoi
    public int getRandom(int min, int max) {
        Random r = new Random();
        return min+r.nextInt(max-min);
    }   // lay ngau nhien trong khoang min-max
    public void random50_50(){
        do{
            one = getRandom(1,4);
        }while (one == Integer.parseInt(ansCorrect.get(i)));

        do{
            two = getRandom(1,4);
        }while (two == Integer.parseInt(ansCorrect.get(i)) || two == one);
        getButton(one).setText("");
        getButton(two).setText("");
    }   // lay ngau nhien 2 dap an sai
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
    }   // tra ve button cau tra loi
    public void setAnswer(Integer i){
        money.setText(moneyStr.toString());
        question.setText(qst.get(i));
        btna.setText("A. " + ansA.get(i));
        btnb.setText("B. " + ansB.get(i));
        btnc.setText("C. " + ansC.get(i));
        btnd.setText("D. " + ansD.get(i));
    }      // setText cho button cau tra loi
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
    }       // tra ve button chua cau tra loi dung
    public void Fail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(playSound.mutesound)
            playSound.setSound(getApplicationContext(),R.raw.lose);
        countDownTimer.cancel();
        countDownTimer2.cancel();

        if(level == 1) {
            money.setText("1");
            moneyStr = 1;
        }
        builder.setMessage("Bạn sẽ ra về với số tiền là " + moneyStr +"$\nBạn có muốn chơi lại ?").setCancelable(false).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moneyStr = 0;
                if(playSound.mutesound)
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
                stopService(intent);
                final Intent myIntent = new Intent(Play.this, Play.class);
                Play.this.finish();

                intent = new Intent(Play.this, PlaySongServiceLevel1_1.class);
                Play.this.startActivity(myIntent);
                if(playSound.mutesound)
                    startService(intent);
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(playSound.mutesound)
                    if(playSound.mediaPlayer.isPlaying())
                        playSound.mediaPlayer.stop();
                stopService(intent);
                Play.this.finish();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.show();
        alert.show();
    }                   // thua cuoc
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onStop(){
        super.onStop();
        if(playSound.mutesound)
            if(playSound.mediaPlayer.isPlaying())
                playSound.mediaPlayer.stop();
        stopService(intent);
        countDownTimer2.cancel();
    }
    @Override
    public void onPause(){
        super.onPause();
        if(playSound.mutesound)
            if(playSound.mediaPlayer.isPlaying())
                playSound.mediaPlayer.stop();
        stopService(intent);
        countDownTimer2.cancel();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(playSound.mutesound)
            if(playSound.mediaPlayer.isPlaying())
                playSound.mediaPlayer.stop();
        stopService(intent);
        countDownTimer2.cancel();
    }
    @Override
    public void onResume(){
        super.onResume();
        if(playSound.mutesound)
            startService(intent);
    }
}
