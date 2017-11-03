package nonsobiose.com.exchange.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nonsobiose.com.exchange.model.DigitalCurrency;
import nonsobiose.com.exchange.R;

public class ConversionActivity extends AppCompatActivity {

    private String result = "";
    private List<HashMap<String, Double>> listOfCurrencies = DigitalCurrency.getDigitalCurrencies();
    private double currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doTransition();// Performs transitioning upon entering this activity
        setContentView(R.layout.activity_converter);

        // Sets up navigation for going back to AddCardsActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Extracts the selected Card extra and selected Currency extra used to determine
        // which currency_background_wall is to be converted in
        Intent intent = getIntent();
        final String selectedCard = intent.getStringExtra("selectedCard");
        final String selectedCurrency = intent.getStringExtra("selectedCurrency");

        // Used to display the corresponding name of the currency_background_wall conversion
        TextView digitalCurrencyName = (TextView) findViewById(R.id.digital_currency_name);

        // Used to display the corresponding logo of the currency_background_wall conversion
        ImageView digitalCurrencyIcon = (ImageView) findViewById(R.id.digital_currency_icon);

        // Used to display the result of the currency_background_wall conversion
        final TextView digitalCurrencyResult = (TextView) findViewById(R.id.digital_currency_display);

        // Used to accept number input for curency conversion
        final EditText amountInput = (EditText) findViewById(R.id.amount_input);

        // Used to perform the mathematical conversion of the currency_background_wall exchange
        FloatingActionButton convertButton = (FloatingActionButton) findViewById(R.id.convert_button);

        if(selectedCard.equals("BITCOIN")) {
            digitalCurrencyName.setText(selectedCard);// Set text to Bitcoin
            currency = listOfCurrencies.get(0).get(selectedCurrency);// Get Bicoin HashMap currency_background_wall value
            digitalCurrencyIcon.setImageResource(R.drawable.bitcoin_logo); // Set bitcoin logo
        } else {
            digitalCurrencyName.setText(selectedCard);// Set text to Ethereum
            currency = listOfCurrencies.get(1).get(selectedCurrency);// Get Ethereum HashMap currency_background_wall value
            digitalCurrencyIcon.setImageResource(R.drawable.ethereum_logo);// Set Ethereum logo
        }

        // Get inputted amount and multiply it against the selected currency_background_wall
        convertButton.setOnClickListener((v) -> {
                String amount = amountInput.getText().toString().trim();
                if(!amount.equals("")) {
                    int inputtedAmount = Integer.parseInt(amount);
                    result = String.format(Locale.getDefault(), "%.2f", currency * inputtedAmount);
                    digitalCurrencyResult.setText(result);
                }
        });
    }

    /**
     * navigates from this current activity to the parent Activity(AddCards Activity)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     *  Used to perform transition animation between Activities
     *  This Transition is only supported in devices running API 21(Lollipop) and above
     */
    @TargetApi(21)
    public void doTransition() {
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Slide(Gravity.START));
            getWindow().setExitTransition(new Slide(Gravity.END));
        }
    }
}
