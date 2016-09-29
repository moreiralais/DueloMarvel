package br.com.lais.duelomarvel.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.modelo.JsonResponse;
import br.com.lais.duelomarvel.modelo.ResultsResponse;
import br.com.lais.duelomarvel.servico.MarvelAPI;
import br.com.lais.duelomarvel.servico.MarvelAPIInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicialActivity extends AppCompatActivity {

    private ArrayList<ResultsResponse> resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton btniniciar = (FloatingActionButton) findViewById(R.id.btniniciar);
        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                loadJSON();


            }
        });
    }

    private void loadJSON() {
        MarvelAPI marvelAPI = MarvelAPIInstance.getMarvelAPI();

        String hash = "c21f277accb58bb9007a42df2039d446";
        String ts = "1475149059";

        Call<JsonResponse> call = marvelAPI.getLista(ts,hash);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                JsonResponse jsonResponse = response.body();



                resultados = new ArrayList<>(Arrays.asList(jsonResponse.getData().getResults()));

                Log.i("LOG-LAIS",resultados.get(0).getName());


                List<ResultsResponse> listaComDescricoes = new ArrayList<>();
                List<ResultsResponse> timeUm = new ArrayList<>();
                List<ResultsResponse> timeDois = new ArrayList<>();

                for(ResultsResponse r :resultados){
                    if(!r.getDescription().isEmpty()){
                        listaComDescricoes.add(r);
                    }
                }

                for(int i=0;i<10;i++){
                    timeUm.add(listaComDescricoes.get(i));
                    timeDois.add(listaComDescricoes.get(i+10));
                }

//TODO enviar lista para activity times


            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
