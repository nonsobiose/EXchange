package nonsobiose.com.exchange.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.HashMap;
import java.util.List;


/**
 * Created by slimb on 11/10/2017.
 */

public class CryptoCurrencyLoader extends AsyncTaskLoader<List<HashMap<String, Double>>> {

    private String mUrl;

    public CryptoCurrencyLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<HashMap<String, Double>> loadInBackground() {
        return NetworkUtils.getDigitalCurrency(mUrl);
    }
}