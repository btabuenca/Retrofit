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

    // https://restcountries.eu/rest/v2/all
    @GET("/rest/v2/all")
    Call<List<Country>> getAllCountries();

    // https://restcountries.eu/rest/v2/name/Spain
    @GET("/rest/v2/name/{countryName}")
    Call<List<Country>> getCountryByName(@Path("countryName") String countryName);

    // https://restcountries.eu/rest/v2/name/Spain?keytoken=e33423faf32r2asd
    @GET("/rest/v2/name/{countryName}")
    Call<List<Country>> getCountryByName(@Path("countryName") String countryName, @Query("keytoken") String keytoken);

    // https://restcountries.eu/rest/v2/alpha/ES
    @GET("/rest/v2/alpha/{countryCode}")
    Call<Country> getCountryByCode(@Path("countryCode") String countryCode);

}
