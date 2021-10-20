package es.upm.miw.demoretrofit;
import java.util.List;

import es.upm.miw.demoretrofit.models.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


@SuppressWarnings("Unused")
interface ICountryRESTAPIService {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://restcountries.com/v3.1/all
    // On click second button a>z
    @GET("/v3.1/all")
    Call<List<Country>> getAllCountries();


    // https://restcountries.com/v3.1/name/Spain
    // works with a fake token. On click first-lupa button
    @GET("/v3.1/name/{countryName}")
    Call<List<Country>> getCountryByName(@Path("countryName") String countryName);


}
