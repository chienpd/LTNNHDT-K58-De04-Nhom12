package chienphamk58.ailatrieuphu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import chienphamk58.ailatrieuphu.Database.DatabaseAccess;

public class Main4Activity extends AppCompatActivity {

    DatabaseAccess databaseAccess;
    List<String> qst, ansA, ansB, ansC, ansD, ansID, ansCorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main4);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        qst = databaseAccess.getQuestion(0);
        ansA = databaseAccess.getQuestion(3);
        ansB = databaseAccess.getQuestion(4);
        ansC = databaseAccess.getQuestion(5);
        ansD = databaseAccess.getQuestion(6);
        databaseAccess.close();
        Button btna = (Button)findViewById(R.id.button9);
        Button btnb = (Button)findViewById(R.id.button14);
        Button btnc = (Button)findViewById(R.id.button15);
        Button btnd = (Button)findViewById(R.id.button16);

        TextView question = (TextView)findViewById(R.id.textView25);
        question.setText(qst.get(0));

        btna.setText(ansA.get(0));
        btnb.setText(ansB.get(0));
        btnc.setText(ansC.get(0));
        btnd.setText(ansD.get(0));
    }
}
