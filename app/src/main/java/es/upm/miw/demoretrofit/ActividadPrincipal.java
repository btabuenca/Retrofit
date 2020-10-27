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

public class ActividadPrincipal extends Activity {

    private static final String API_BASE_URL = "https://restcountries.eu";

    private static final String LOG_TAG = "MiW";

    private TextView tvRespuesta;
    private EditText etCountryName;

    private ICountryRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
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
    // A este método se llama cada vez que se hace click sobre la lupa
    // Ver  activity_actividad_principal.xml
    //
    public void obtenerInfoPais(View v) {
        String countryName = etCountryName.getText().toString();
        Log.i(LOG_TAG, "obtenerInfoPais => país=" + countryName);
        tvRespuesta.setText("");

        // Realiza la llamada por nombre
        // Call<List<Country>> call_async = apiService.getCountryByName(countryName);
        Call<List<Country>> call_async = apiService.getCountryByName(countryName, "asfdasdfasdf");

        // Asíncrona
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
                    Log.i(LOG_TAG, "obtenerInfoPais => respuesta=" + countryList);
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


        // Síncrona... no aquí => NetworkOnMainThreadException
//        Call<Country> call_sync = apiService.getCountryByName("spain");
//        try {
//            Country country = call_sync.execute().body();
//            Log.i(LOG_TAG, "SYNC => " + country.toString());
//        } catch (IOException e) {
//            Log.e(LOG_TAG, e.getMessage());
//        }
    }



    public void obtenerTodosPaises(View v) {
        String countryName = etCountryName.getText().toString();
        Log.i(LOG_TAG, "obtenerInfoPais => país=" + countryName);
        tvRespuesta.setText("");

        // Realiza la llamada por nombre
        Call<List<Country>> call_async = apiService.getAllCountries();

        // Asíncrona
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
                    Log.i(LOG_TAG, "obtenerInfoPais => respuesta=" + countryList);
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