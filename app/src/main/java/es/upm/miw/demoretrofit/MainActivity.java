package es.upm.miw.demoretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.demoretrofit.models.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private static final String API_BASE_URL = "https://restcountries.eu";

    private static final String LOG_TAG = "MiW";

    private TextView tvRespuesta;
    private EditText etCountryName;

    private ICountryRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRespuesta = (TextView) findViewById(R.id.tvRespuesta);
        etCountryName = (EditText) findViewById(R.id.countryName);

        // btb added for retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
        Call<List<Country>> call_async = apiService.getCountryByName(countryName, "asfdasdfasdf");
        call_async.enqueue(new Callback<List<Country>>() {


            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countryList = response.body();
                int contaPais = 0;
                if (null != countryList) {
                    for (Country country : countryList) {
                        contaPais++;
                        tvRespuesta.append(contaPais+ " - ["+country.getName() + "] "+country.getAltSpellings()+"\n\n");
                    }
                    Log.i(LOG_TAG, "getCountryByName => respuesta=" + countryList);
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


                        tvRespuesta.append(contaPais+ " - ["+country.getName() + "] "+country.getAltSpellings()+"\n\n");
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

}