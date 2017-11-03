package nonsobiose.com.exchange.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import nonsobiose.com.exchange.R;

public class AddCardsActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton showAddButton;
    private FloatingActionButton addBitcoinsButton;
    private FloatingActionButton addEthereumButton;

    private Spinner bitcoinsCurrencySpinner;
    private Spinner ethereumCurrencySpinner;

    private ArrayAdapter<CharSequence> currenciesAdapter;

    private LinearLayout cardList;
    private LinearLayout emptyStateView;

    private boolean isShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doActivityTransition();
        setContentView(R.layout.activity_add_cards);

        // Button for revealing the digital currencies button for creating the digital currencies cards
        showAddButton = (FloatingActionButton) findViewById(R.id.show_add_button);
        showAddButton.setOnClickListener(this);

        // Button for creating a Bitcoins card
        addBitcoinsButton = (FloatingActionButton) findViewById(R.id.add_bitcoins_card_button);
        addBitcoinsButton.setOnClickListener(this);

        // Button for creating an Ethereum card
        addEthereumButton = (FloatingActionButton) findViewById(R.id.add_ethereum_card_button);
        addEthereumButton.setOnClickListener(this);

        // Adapter for the spinner used to populate the spinner with list of currencies
        currenciesAdapter = ArrayAdapter.createFromResource(this, R.array.currencies, R.layout.spinner_item);
        currenciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Linear layout used to hold the created cards
        cardList = (LinearLayout) findViewById(R.id.cards_list);

        // Empty state view used to indicated that there is no created card yet
        emptyStateView = (LinearLayout) findViewById(R.id.empty_state_view);
        emptyStateView.setVisibility(View.VISIBLE);

    }

    /**
     *  Handles all onClick function in this activity
     * @param v
     */

    @TargetApi(21)
    @Override
    public void onClick(View v) {
        String selectedCurrency = "";
        switch (v.getId()) {

            /**
             *  Onclick function for the floating action button used to reveal the buttons for creating
             *  the digital currencies card. if "isShown" is true ( i.e the buttons are displayed) the fadeOut
             *  animation is called to hide the buttons else, the fadeIn animation is called to reveal the buttons
             */
            case R.id.show_add_button:
                if(isShown) {
                    YoYo.with(Techniques.FadeOutDown).duration(500).playOn(addEthereumButton);
                    addEthereumButton.setVisibility(View.GONE); // Hide the Bitcoins button
                    YoYo.with(Techniques.FadeOutDown).duration(500).playOn(addBitcoinsButton);
                    addBitcoinsButton.setVisibility(View.GONE); // Hide the Ethereum button
                    isShown = false; // Digital currencies are no more visible
                    break;
                } else {
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(addEthereumButton);
                    addEthereumButton.setVisibility(View.VISIBLE); // Show the Bitcoins button
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(addBitcoinsButton);
                    addBitcoinsButton.setVisibility(View.VISIBLE); // Show the Ethereum button
                    isShown = true; // Digital currencies are now visible
                    break;
                }

            /**
             *  Onclick function for the Bitcoins and Ethereum buttons.
             *  Onclick, the empty state view is made invisible then the corresponding card is created and added to its
             *  parent layout(Linear layout). The button is then disabled to prevent further clicks.
             *  Also, a click listener is registered for the created card, which is used to catch a click event on the card.
             *  Finally, the created card is used to obtain a reference to the spinner which is attached to the card.
             */
            case R.id.add_bitcoins_card_button:
                    emptyStateView.setVisibility(View.GONE); // Hide the empty state view
                    View bitcoinCard = getLayoutInflater().inflate(R.layout.bitcoin_card_item, cardList, false); // Inflate the card
                    cardList.addView(bitcoinCard); // Card is added to its parent layout
                    YoYo.with(Techniques.SlideInDown).duration(500).playOn(bitcoinCard);
                    addBitcoinsButton.setEnabled(false); // Prevent further clicks
                    bitcoinCard.setOnClickListener(this); // Listen for a click on this card
                    bitcoinsCurrencySpinner = (Spinner) bitcoinCard.findViewById(R.id.bitcoin_card_spinner); // Get a reference to the spinner
                    bitcoinsCurrencySpinner.setAdapter(currenciesAdapter);// Sets its data source
                    break;
            case R.id.add_ethereum_card_button:
                    emptyStateView.setVisibility(View.GONE); // Hide the empty state view
                    View ethereumCard = getLayoutInflater().inflate(R.layout.ethereum_card_item, cardList, false); // Inflate the card
                    cardList.addView(ethereumCard); // Card is added to its parent layout
                    YoYo.with(Techniques.SlideInDown).duration(500).playOn(ethereumCard);
                    addEthereumButton.setEnabled(false); // Prevent further clicks
                    ethereumCard.setOnClickListener(this); // Listen for a click on this card
                    ethereumCurrencySpinner = (Spinner) ethereumCard.findViewById(R.id.ethereum_card_spinner); // Get a reference to the spinner
                    ethereumCurrencySpinner.setAdapter(currenciesAdapter);// Sets its data source
                    break;

            /**
             *  Onclick function for the created Digital currency_background_wall cards
             *  Where the selected currency_background_wall from the spinner and the name of the selected card is passed
             *  as a extra through an intent to the conversion Activity
             */
            case R.id.bitcoin_card:
                selectedCurrency = (String) bitcoinsCurrencySpinner.getSelectedItem();// Get selected currency_background_wall
                Intent intent1 = new Intent(this, ConversionActivity.class);
                intent1.putExtra("selectedCurrency", selectedCurrency);// Add the selected currency_background_wall to the intent
                intent1.putExtra("selectedCard", "BITCOIN");//  Add the name of the selected card to the intent
                startActivity(intent1);// launch the conversion intent
                break;
            case R.id.ethereum_card:
                selectedCurrency = (String) ethereumCurrencySpinner.getSelectedItem();// Get selected currency_background_wall
                Intent intent2 = new Intent(this, ConversionActivity.class);
                intent2.putExtra("selectedCurrency", selectedCurrency);// Add the selected currency_background_wall to the intent
                intent2.putExtra("selectedCard", "ETHEREUM");//  Add the name of the selected card to the intent
                startActivity(intent2);// launch the conversion intent
                break;
        }
    }

    /**
     *  Used to perform transition animation between the Splash screen and AddCards activity
     *  This Transition is only supported in devices running API 21(Lollipop) and above
     */
    @TargetApi(21)
    public void doActivityTransition() {
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Slide(Gravity.END));
        }

    }

}
