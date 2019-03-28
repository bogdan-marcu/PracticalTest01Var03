package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        Button changeActivityButton = findViewById(R.id.changeActivityButton);
        final Button displayInformationButton = findViewById(R.id.displayInformationButton);
        final CheckBox nameCheckBox = findViewById(R.id.checkBox1);
        final CheckBox groupCheckBox = findViewById(R.id.checkBox2);

        final EditText nameInput = findViewById(R.id.editText1);
        final EditText groupInput = findViewById(R.id.editText2);
        final TextView displayInformationView = findViewById(R.id.displayInformationView);

        final Intent intent = new Intent(this, PracticalTest01Var03SecondaryActivity.class);
        final Intent serviceIntent = new Intent(this, PracticalTest01Var03Service.class);

        changeActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent.putExtra("NAME_INPUT", nameInput.getText().toString());
                intent.putExtra("GROUP_INPUT", groupInput.getText().toString());
                startActivityForResult(intent, 1);
            }
        });

        displayInformationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                if(nameCheckBox.isChecked()){
                    if(nameInput.getText().length() > 0){
                        stringBuilder.append(nameInput.getText());
                        stringBuilder.append(' ');
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill name input", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(groupCheckBox.isChecked()){
                    if(groupInput.getText().length() > 0){
                        stringBuilder.append(groupInput.getText());
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill group input", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(nameCheckBox.isChecked() && groupCheckBox.isChecked()){

                    serviceIntent.putExtra("NAME_INPUT", nameInput.getText().toString());
                    serviceIntent.putExtra("GROUP_INPUT", groupInput.getText().toString());
                    //serviceIntent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01var03.", "ro.pub.cs.systems.eim.practicaltest01var03.service.PracticalTest01Var03Service"));
                    startService(serviceIntent);
                }
                displayInformationView.setText(stringBuilder.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        CheckBox nameCheckBox = findViewById(R.id.checkBox1);
        CheckBox groupCheckBox = findViewById(R.id.checkBox2);

        EditText nameInput = findViewById(R.id.editText1);
        EditText groupInput = findViewById(R.id.editText2);
        TextView displayInformationView = findViewById(R.id.displayInformationView);

        savedInstanceState.putString("NAME_INPUT", nameInput.getText().toString());
        savedInstanceState.putString("GROUP_INPUT", groupInput.getText().toString());
        savedInstanceState.putString("DISPLAY_INFORMATION_VIEW", displayInformationView.getText().toString());

        savedInstanceState.putBoolean("NAME_CHECK_BOX", nameCheckBox.isChecked());
        savedInstanceState.putBoolean("GROUP_CHECK_BOX", groupCheckBox.isChecked());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CheckBox nameCheckBox = findViewById(R.id.checkBox1);
        CheckBox groupCheckBox = findViewById(R.id.checkBox2);

        EditText nameInput = findViewById(R.id.editText1);
        EditText groupInput = findViewById(R.id.editText2);
        TextView displayInformationView = findViewById(R.id.displayInformationView);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey("NAME_INPUT")){
                nameInput.setText(savedInstanceState.getString("NAME_INPUT"));
            }
            if(savedInstanceState.containsKey("GROUP_INPUT")){
                groupInput.setText(savedInstanceState.getString("GROUP_INPUT"));
            }
            if(savedInstanceState.containsKey("DISPLAY_INFORMATION_VIEW")){
                displayInformationView.setText(savedInstanceState.getString("DISPLAY_INFORMATION_VIEW"));
            }
            if(savedInstanceState.containsKey("NAME_CHECK_BOX")){
                nameCheckBox.setChecked(savedInstanceState.getBoolean("NAME_CHECK_BOX"));
            }
            if(savedInstanceState.containsKey("GROUP_CHECK_BOX")){
                groupCheckBox.setChecked(savedInstanceState.getBoolean("GROUP_CHECK_BOX"));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        }
        if(resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_LONG).show();
        }
    }

    private PracticalTest01Var03Service ptService = null;
    private int status;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ptService = ((PracticalTest01Var03Service.SomeBinder)service).getService();
            status = 1;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            ptService = null;
            status = 0;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (status == 1) {
            unbindService(serviceConnection);
        }
    }
}
