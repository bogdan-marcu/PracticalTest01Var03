package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        TextView nameView = findViewById(R.id.nameView);
        TextView groupView = findViewById(R.id.groupView);

        Intent startingIntent = getIntent();

        if(startingIntent != null){
            if(startingIntent.getStringExtra("NAME_INPUT") != null){
                nameView.setText(startingIntent.getStringExtra("NAME_INPUT"));
            }
            if(startingIntent.getStringExtra("GROUP_INPUT") != null){
                groupView.setText(startingIntent.getStringExtra("GROUP_INPUT"));
            }
        }
        final Intent returnIntent = new Intent();

        Button okButton = findViewById(R.id.okButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }

}
