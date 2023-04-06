package ro.pub.cs.systems.eim.colocviumodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu2MainActivity extends AppCompatActivity {

    private TextView allText;
    private Button buttonSouth;
    private Button buttonNorth;
    private Button buttonEast;
    private Button buttonWest;
    private Button buttonNavigateActivity;

    Object serviceStatus = Constants.SERVICE_STOPPED;

    private int nr = 0;


    BroadcastReceiver broadcastReceiver = new PracticalTest01BroadcastReceiver();

    private IntentFilter intentFilter = new IntentFilter();

    private class PracticalTest01BroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
            // Toast.makeText(this, "The activity returned with " + valueFinal, Toast.LENGTH_LONG).show();
            //   System.out.println("primesc");
            //  Toast.makeText(context, "Received from service " + intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA), Toast.LENGTH_LONG).show();
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonEst:
                    allText.setText(allText.getText().toString() + ", East");
                    nr++;
                    myStartService();
                    break;
                case R.id.buttonNorth:
                    allText.setText(allText.getText().toString() + ", North");
                    nr++;
                    myStartService();
                    break;
                case R.id.buttonVest:
                    allText.setText(allText.getText().toString() + ", Weast");
                    nr++;
                    myStartService();
                    break;
                case R.id.buttonSouth:
                    allText.setText(allText.getText().toString() + ", South");
                    nr++;
                    myStartService();
                    break;
                case R.id.navigateSecondActivity:
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    System.out.print(" trimit " + allText.getText().toString());
                    intent.putExtra(Constants.MESGTEXT, allText.getText().toString());
                    // astept rezultat
                    startActivityForResult(intent, Constants.RequestCode);
            }
        }
    }


    public void myStartService() {
        // System.out.println("ajung cu "+valueFinal);
        if (nr == 4 && serviceStatus == Constants.SERVICE_STOPPED) {
            Intent intent = new Intent(getApplicationContext(), Colocviu2Service.class);
            intent.putExtra(Constants.COORD, allText.getText().toString());
            getApplicationContext().startService(intent);
            serviceStatus = Constants.SERVICE_STARTED;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // salvez doar val calculata
        savedInstanceState.putString(Constants.NR, Integer.toString(nr));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.NR)) {
            nr = Integer.parseInt(savedInstanceState.getString(Constants.NR));
            // System.out.println("Nr now "+ nr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu2_main);

        allText = (TextView) findViewById(R.id.actualText);
        buttonEast = (Button) findViewById(R.id.buttonEst);
        buttonNorth = (Button) findViewById(R.id.buttonNorth);
        buttonSouth = (Button) findViewById(R.id.buttonSouth);
        buttonWest = (Button) findViewById(R.id.buttonVest);
        buttonNavigateActivity = (Button) findViewById(R.id.navigateSecondActivity);

        buttonEast.setOnClickListener(buttonClickListener);
        buttonNorth.setOnClickListener(buttonClickListener);
        buttonSouth.setOnClickListener(buttonClickListener);
        buttonWest.setOnClickListener(buttonClickListener);
        buttonNavigateActivity.setOnClickListener(buttonClickListener);


        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.RequestCode)
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with result CANCEL", Toast.LENGTH_LONG).show();
            }
        nr = 0;
        allText.setText(" ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), Colocviu2Service.class);
        getApplicationContext().stopService(intent);
    }


    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }
}