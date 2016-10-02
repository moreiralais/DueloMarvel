package br.com.lais.duelomarvel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.modelo.JsonResponse;
import br.com.lais.duelomarvel.modelo.ResultsResponse;
import br.com.lais.duelomarvel.servico.MarvelAPI;
import br.com.lais.duelomarvel.servico.MarvelAPIInstance;
import br.com.lais.duelomarvel.util.DueloUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicialActivity extends AppCompatActivity {

    private ArrayList<ResultsResponse> resultados;
    String offset = "0";
    List<ResultsResponse> listaComDescricoes = new ArrayList<>();

    List<ResultsResponse> timeUm = new ArrayList<>();
    List<ResultsResponse> timeDois = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        listaComDescricoes = new ArrayList<>();

        Button btniniciar = (Button) findViewById(R.id.btniniciar);
        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                loadJSON(offset);
            }
        });
    }

    private void loadJSON(String offset) {
        MarvelAPI marvelAPI = MarvelAPIInstance.getMarvelAPI();

        DueloUtil util = new DueloUtil(InicialActivity.this);
        String hash =  util.getHash();
        String ts = util.getTs();
        String limit = util.getLimit();

        Call<JsonResponse> call = marvelAPI.getLista(ts,hash,limit,offset);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                tratarRetornoCallback(jsonResponse);
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(InicialActivity.this,"Erro interno. Por favor tente mais tarde.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void tratarRetornoCallback(JsonResponse jsonResponse) {
        resultados = new ArrayList<>(Arrays.asList(jsonResponse.getData().getResults()));

        for(ResultsResponse r :resultados){
            if(!r.getDescription().isEmpty()){
                listaComDescricoes.add(r);
            }
        }

        if(listaComDescricoes.size()<20){
            Log.i("LOG-LAIS", "Lista com descricoes menos que 20");
            offset+=1;
            loadJSON(offset);
        }else {
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            timeUm = new ArrayList<>();
            timeDois = new ArrayList<>();
            Log.i("LOG-LAIS", " TAMANHO COM DESCRICAO " + listaComDescricoes.size());

            for (int i = 0; i < 10; i++) {
                timeUm.add(listaComDescricoes.get(i));
                timeDois.add(listaComDescricoes.get(i + 10));
            }

            Intent intent = new Intent(InicialActivity.this, TimesActivity.class);
            intent.putExtra("timeUm", (ArrayList<ResultsResponse>) timeUm);
            intent.putExtra("timeDois", (ArrayList<ResultsResponse>) timeDois);
            startActivity(intent);
        }
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
