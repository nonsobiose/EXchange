package nonsobiose.com.exchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by slimb on 07/10/2017.
 * Class that holds the returned Bitcoins and Ethereum currencies and their respective values
 */

public class DigitalCurrency {

    /**
     * List used to hold both the Bitcoins and Ethereum Map collections
     * The Maps contain a key("currency_background_wall Name") and value ("currency_background_wall value") pair
     */
    private static List<HashMap<String, Double>> digitalCurrencies = new ArrayList<>();


    /**
     *
     * @return a list of the Bitcoins and Ethereum Map collections
     */
    public static List<HashMap<String, Double>> getDigitalCurrencies() {
        return digitalCurrencies;
    }

    /**
     * The returned Bitcoins and Ethereum values are been stored in a Map collections
     * and are being passed into a list collection
     */
    public static void setDigitalCurrencies(List<HashMap<String, Double>> digitalCurrencies) {
        DigitalCurrency.digitalCurrencies = digitalCurrencies;
    }
}
