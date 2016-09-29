package br.com.lais.duelomarvel.servico;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lais on 29/09/2016.
 */
public class MarvelAPIInstance {

    public static MarvelAPI getMarvelAPI() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(MarvelAPI.ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(MarvelAPI.class);
    }
}
