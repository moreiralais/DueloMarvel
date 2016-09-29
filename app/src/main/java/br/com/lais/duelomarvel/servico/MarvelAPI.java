package br.com.lais.duelomarvel.servico;

import br.com.lais.duelomarvel.modelo.JsonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lais on 29/09/2016.
 */
public interface MarvelAPI {

    String ENDPOINT = "http://gateway.marvel.com";

    @GET("/v1/public/characters?apikey=fcf6688c1ba1b7803f228c5b8daa4fbd")
    Call<JsonResponse> getLista(@Query("ts") String ts, @Query("hash") String hash);
}
