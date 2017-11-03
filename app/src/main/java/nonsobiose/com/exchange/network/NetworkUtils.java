package nonsobiose.com.exchange.network;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by slimb on 10/10/2017.
 */

public class NetworkUtils {

    // This method brings to together all the below methods and returns the final result which is the
    // list of Digital currencies(Bitcoin and Ethereum) that are being stored in a HashMap
    public static List<HashMap<String, Double>> getDigitalCurrency(String url) {
        URL urlString = makeUrl(url);
        String jsonResponseString = makeApiRequest(urlString);
        return extractJson(jsonResponseString);
    }

    /**
     *  Creates a URL object from API endpoint link
     * @param apiString, API endpoint
     * @return URL, which is the newly created URL for connection purposes
     */
    private static URL makeUrl(String apiString) {
        URL url = null;
        try {
            url = new URL(apiString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     *  Makes the request to the API endpoint for the JSON response("List of currencies")
     * @param url used to make an Http connection
     * @return String which contains the returned JSON response
     */
    private static String makeApiRequest(URL url) {
        String jsonResponse  = null;
        if(url == null) {
            return null;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            int jsonResponseCode = httpURLConnection.getResponseCode();
            if(jsonResponseCode == 200) {
                jsonResponse = readInputStream(httpURLConnection.getInputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }


    /**
     * Reads the content of the Http request and returns the JSON response
     * @param inputStream returned from the Http Connection
     * @return Json String
     */
    private static String readInputStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        String line ="";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e ) {
                e.printStackTrace();
        }
        return output.toString();
    }

    /**
     * Traverses the JSON object tree and pulls out the required data to be stored in a Hashmap and
     * then passed into a List
     * @param jsonResponse Containing all the Json Object
     * @return ArrayList containing the HashMaps
     */
    private static List<HashMap<String, Double>> extractJson(String jsonResponse) {
        List<HashMap<String, Double>> listOfDigitalCurrencies = new ArrayList<>();

        if(!TextUtils.isEmpty(jsonResponse)) {
            try {
                JSONObject jsonRoot = new JSONObject(jsonResponse);

                // Stores the Bitcoins Json object values in a HashMap
                // then adds the HashMap to a List

                JSONObject btcObject = jsonRoot.getJSONObject("BTC");
                HashMap<String, Double> btcCurrencies = new HashMap<>();
                btcCurrencies.put("AED", btcObject.getDouble("AED"));
                btcCurrencies.put("AUD", btcObject.getDouble("AUD"));
                btcCurrencies.put("BRL", btcObject.getDouble("BRL"));
                btcCurrencies.put("CAD", btcObject.getDouble("CAD"));
                btcCurrencies.put("CHF", btcObject.getDouble("CHF"));
                btcCurrencies.put("CNY", btcObject.getDouble("CNY"));
                btcCurrencies.put("EGP", btcObject.getDouble("EGP"));
                btcCurrencies.put("ETP", btcObject.getDouble("ETP"));
                btcCurrencies.put("EUR", btcObject.getDouble("EUR"));
                btcCurrencies.put("GBP", btcObject.getDouble("GBP"));
                btcCurrencies.put("GHS", btcObject.getDouble("GHS"));
                btcCurrencies.put("HKD", btcObject.getDouble("HKD"));
                btcCurrencies.put("INR", btcObject.getDouble("INR"));
                btcCurrencies.put("KES", btcObject.getDouble("KES"));
                btcCurrencies.put("NGN", btcObject.getDouble("NGN"));
                btcCurrencies.put("SEK", btcObject.getDouble("SEK"));
                btcCurrencies.put("TZS", btcObject.getDouble("TZS"));
                btcCurrencies.put("USD", btcObject.getDouble("USD"));
                btcCurrencies.put("XAF", btcObject.getDouble("XAF"));
                btcCurrencies.put("ZAR", btcObject.getDouble("ZAR"));
                listOfDigitalCurrencies.add(btcCurrencies);

                // Stores the Ethereum Json object values in a HashMap
                // then adds the HashMap to a List

                JSONObject ethObject = jsonRoot.getJSONObject("ETH");
                HashMap<String, Double> ethCurrencies = new HashMap<>();
                ethCurrencies.put("AED", ethObject.getDouble("AED"));
                ethCurrencies.put("AUD", ethObject.getDouble("AUD"));
                ethCurrencies.put("BRL", ethObject.getDouble("BRL"));
                ethCurrencies.put("CAD", ethObject.getDouble("CAD"));
                ethCurrencies.put("CHF", ethObject.getDouble("CHF"));
                ethCurrencies.put("CNY", ethObject.getDouble("CNY"));
                ethCurrencies.put("EGP", ethObject.getDouble("EGP"));
                ethCurrencies.put("ETP", ethObject.getDouble("ETP"));
                ethCurrencies.put("EUR", ethObject.getDouble("EUR"));
                ethCurrencies.put("GBP", ethObject.getDouble("GBP"));
                ethCurrencies.put("GHS", ethObject.getDouble("GHS"));
                ethCurrencies.put("HKD", ethObject.getDouble("HKD"));
                ethCurrencies.put("INR", ethObject.getDouble("INR"));
                ethCurrencies.put("KES", ethObject.getDouble("KES"));
                ethCurrencies.put("NGN", ethObject.getDouble("NGN"));
                ethCurrencies.put("SEK", ethObject.getDouble("SEK"));
                ethCurrencies.put("TZS", ethObject.getDouble("TZS"));
                ethCurrencies.put("USD", ethObject.getDouble("USD"));
                ethCurrencies.put("XAF", ethObject.getDouble("XAF"));
                ethCurrencies.put("ZAR", ethObject.getDouble("ZAR"));
                listOfDigitalCurrencies.add(ethCurrencies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listOfDigitalCurrencies;
    }
}
