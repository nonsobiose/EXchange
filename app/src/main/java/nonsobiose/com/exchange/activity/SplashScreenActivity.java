package nonsobiose.com.exchange.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;

import nonsobiose.com.exchange.network.CryptoCurrencyLoader;
import nonsobiose.com.exchange.R;
import nonsobiose.com.exchange.model.DigitalCurrency;

public class SplashScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<HashMap<String, Double>>> {

    private CountDownTimer countDownTimer;
    private int loaderCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Initializes a new loader to perform the background network task
        getLoaderManager().initLoader(loaderCounter, null, SplashScreenActivity.this);

        /**
         *  Countdown timer used to determine how long(15seconds) the API request should take before prompting
         *  the user to either LEAVE the app or TRY AGAIN
         *  Upon selecting TRY AGAIN, a new loader is initiated to perform another background API request task
         *  else on selecting LEAVE, the app is exited
         */
        countDownTimer = new CountDownTimer(15000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder alert = new AlertDialog.Builder(SplashScreenActivity.this);
                alert.setMessage(getString(R.string.countdown_message));
                alert.setPositiveButton(getString(R.string.positve_countdown_message), (dialog, which) -> {
                        loaderCounter++;// new loader
                        getLoaderManager().initLoader(loaderCounter, null, SplashScreenActivity.this);
                        countDownTimer.start();
                });

                alert.setNegativeButton(getString(R.string.negative_countdown_message), (dialog, which) -> {
                        countDownTimer.cancel();
                        finish();
                });
                alert.show();
            }
        }.start();
    }

    /**
     * Called when a new loader is being created (The loader makes the background API request)
     * @param id
     * @param args
     * @return Loader<ArrayList<HashMap<String, Double>>>
     */
    @Override
    public Loader<List<HashMap<String, Double>>> onCreateLoader(int id, Bundle args) {
        String REQUEST_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=AED,AUD,BRL,CAD,CHF,CNY,EGP,ETP,EUR,GBP,GHS,HKD,INR,KES,NGN,SEK,TZS,USD,XAF,ZAR";
        return new CryptoCurrencyLoader(this, REQUEST_URL);

    }


    /**
     * Called when a previously created loader has finished loading contents,
     * onLoadFinished, the returned data is passed as a value to a List<String, HashMap<String, Double>>
     * Then the @ConversionActivity is launched and this current activity is closed.
     *
     * @param loader ( The returned loader which was previously created)
     * @param data   ( The data that was returned)
     */
    @Override
    public void onLoadFinished(Loader<List<HashMap<String, Double>>> loader, List<HashMap<String, Double>> data) {
        if (!data.isEmpty() && data.size() != 0) {
            DigitalCurrency.setDigitalCurrencies(data); // Store returned result
            Intent intent = new Intent(this, AddCardsActivity.class);
            startActivity(intent); // Start Conversion Activity
            countDownTimer.cancel();// Cancel timer if still running
            finish(); // Close this activity
        }
    }

    @Override
    public void onLoaderReset(Loader<List<HashMap<String, Double>>> loader) {
        // Do nothing
    }

}


