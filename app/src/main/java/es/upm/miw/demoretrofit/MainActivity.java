package es.upm.miw.demoretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.upm.miw.demoretrofit.models.Country;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

    private static final String API_BASE_URL = "https://restcountries.com";

    private static final String LOG_TAG = "MiW";

    private TextView tvRespuesta;
    private ImageView ivRespuesta;
    private EditText etCountryName;

    private ICountryRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRespuesta = (TextView) findViewById(R.id.tvRespuesta);
        ivRespuesta = (ImageView) findViewById(R.id.ivRespuesta);
        etCountryName = (EditText) findViewById(R.id.countryName);

        // btb added for retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        apiService = retrofit.create(ICountryRESTAPIService.class);


    }




    //
    // Al hacer click sobre la lupa
    // Ver  activity_actividad_principal.xml
    //
    public void getCountryByName(View v) {
        String countryName = etCountryName.getText().toString();
        Log.i(LOG_TAG, "getCountryByName =" + countryName);
        tvRespuesta.setText("");

        // Retrofit call
        Call<List<Country>> call_async = apiService.getCountryByName(countryName);
        call_async.enqueue(new Callback<List<Country>>() {

            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countryList = response.body();
                int contaPais = 0;
                if (null != countryList) {
                    for (Country country : countryList) {
                        contaPais++;
                        tvRespuesta.append(contaPais+ ".["+country.getName().getCommon() + "] "+country.getRegion()+" | "+country.getCapital().get(0).toString()+" | "+country.getPopulation().toString()+" "+country.getFlag()+" \n\n");
                        //tvRespuesta.append(contaPais+ ".["+country.getName().getCommon() + "] "+country.getRegion()+" | "+country.getCapital().get(0).toString()+" | "+ country.getFlags().getPng()+" \n\n");
                        Picasso.get().load(country.getFlags().getPng()).into(ivRespuesta);
                        Log.i(LOG_TAG, "getCountryByName => respuesta=" + country.getName().getCommon());
                    }

                } else {
                    tvRespuesta.setText(getString(R.string.strError));
                    Log.i(LOG_TAG, getString(R.string.strError));
                }
            }


            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }



    //
    // Al hacer click sobre boton a>z
    //
    public void getAllCountries(View v) {
        String countryName = etCountryName.getText().toString();
        Log.i(LOG_TAG, "getAllCountries => país=" + countryName);
        tvRespuesta.setText("");

        // Retrofit call
        Call<List<Country>> call_async = apiService.getAllCountries();
        call_async.enqueue(new Callback<List<Country>>() {

            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countryList = response.body();
                int contaPais = 0;
                if (null != countryList) {
                    for (Country country : countryList) {
                        contaPais++;
                        tvRespuesta.append(country.getFlag()+"  "+contaPais+".["+country.getName().getCommon() + "] \n\n");
                    }
                    Log.i(LOG_TAG, "getAllCountries => respuesta=" + countryList);
                } else {
                    tvRespuesta.setText(getString(R.string.strError));
                    Log.i(LOG_TAG, getString(R.string.strError));
                }
            }


            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }



    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}