package ro.pub.cs.systems.eim.colocviumodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonRegister;

    private TextView textFinal;


    private RegisterButtonClickListener registerButtonClickListener = new RegisterButtonClickListener();

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setResult(RESULT_OK, null);
            finish();
        }
    }

    private CancelButtonClickListener cancelButtonClickListener = new CancelButtonClickListener();

    private class CancelButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED, null);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textFinal = (TextView) findViewById(R.id.receivedValue);

        buttonCancel.setOnClickListener(cancelButtonClickListener);
        buttonRegister.setOnClickListener(registerButtonClickListener);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                textFinal.setText(" ");
            } else {
                //     System.out.print("hei "+ extras.getString(Constants.MESGTEXT));
                textFinal.setText(extras.getString(Constants.MESGTEXT));
                //numberFinal.setText(extras.getString(Constants.LEFT_TEXT) + " " + extras.getString(Constants.RIGHT_TEXT));
            }

        } else {
            textFinal.setText(" ");
        }


    }
}