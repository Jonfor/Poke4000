package net.jonfor.poke4000;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import net.jonfor.poke4000.helpers.PrefHelper;
import net.jonfor.poke4000.helpers.QuickstartPreferences;
import net.jonfor.poke4000.helpers.RegistrationIntentService;
import net.jonfor.poke4000.networking.RequestUtil;


public class MainActivity extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private ProgressBar mRegistrationProgressBar;
    private EditText pokeAddressEditText;
    private EditText frequencyEditText;
    private EditText portEditText;
    private Button pokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.create_poke_bar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.VISIBLE);
                boolean sentToken = PrefHelper.getTokenSent(context);
                if (sentToken) {
                    Toast.makeText(context, getString(R.string.gcm_send_message),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, getString(R.string.token_error_message),
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        setupView();
    }

    private void setupView() {
        pokeAddressEditText = (EditText) findViewById(R.id.server);
        frequencyEditText = (EditText) findViewById(R.id.frequency);
        portEditText = (EditText) findViewById(R.id.port);
        pokeButton = (Button) findViewById(R.id.submit_poke_btn);

        pokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pokeButtonClicked();
            }
        });
    }

    private void pokeButtonClicked() {
        String address = pokeAddressEditText.getText().toString();
        int frequency = Integer.parseInt(frequencyEditText.getText().toString());
        String port = portEditText.getText().toString();

        //TODO Make work for HTTPS/port 443
        if (port.equals("")) {
            port = "80";
        }

        RequestUtil.sendServerInfo(address, port, frequency, getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
