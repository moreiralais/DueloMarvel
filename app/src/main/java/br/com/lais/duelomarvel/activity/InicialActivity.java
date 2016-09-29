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

import java.io.Serializable;
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
                loadJSON(offset);


            }
        });
    }

    private void loadJSON(String offset) {
        MarvelAPI marvelAPI = MarvelAPIInstance.getMarvelAPI();

        /**
         * String s="Texto de Exemplo";
         MessageDigest m=MessageDigest.getInstance("MD5");
         m.update(s.getBytes(),0,s.length());
         System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));

         public key

         fcf6688c1ba1b7803f228c5b8daa4fbd

         private key

         dbb02236c60329a1bf628e6d690a700ae67ae1f8

         ts + PrivateKey + publickey
         */

        String hash = "c21f277accb58bb9007a42df2039d446";
        String ts = "1475149059";
        String limit = "100";


        Call<JsonResponse> call = marvelAPI.getLista(ts,hash,limit,offset);
        Call<JsonResponse> call2 = marvelAPI.getLista(ts,hash,limit,offset+1);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                JsonResponse jsonResponse = response.body();

                tratarRetornoCallback(jsonResponse);

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }

    private void tratarRetornoCallback(JsonResponse jsonResponse) {
        resultados = new ArrayList<>(Arrays.asList(jsonResponse.getData().getResults()));

        Log.i("LOG-LAIS", "Tratando retorno Callback");


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
