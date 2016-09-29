package br.com.lais.duelomarvel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.adapter.ListaAdapter;
import br.com.lais.duelomarvel.listener.RecyclerViewOnClickListener;
import br.com.lais.duelomarvel.modelo.ResultsResponse;

public class TimesActivity extends AppCompatActivity {

    private ListaAdapter listaAdapter;
    private RecyclerView recyclerViewUm;
    private RecyclerView recyclerViewDois;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManagerDois;
    private List<ResultsResponse> data;
    private List<ResultsResponse> timeUmSelecionado;
    private List<ResultsResponse> timeDoisSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<ResultsResponse> timeUm = (List<ResultsResponse>) getIntent().getSerializableExtra("timeUm");
        List<ResultsResponse> timeDois = (List<ResultsResponse>) getIntent().getSerializableExtra("timeDois");

        List<ResultsResponse> listaum = new ArrayList<>();
        List<ResultsResponse> listadois = new ArrayList<>();

        timeUmSelecionado = new ArrayList<>();
        timeDoisSelecionado = new ArrayList<>();

        if(timeUm!=null && timeDois!=null){
            listaum = timeUm;
            listadois = timeDois;
        }


        layoutManager = new LinearLayoutManager(TimesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        layoutManagerDois = new LinearLayoutManager(TimesActivity.this);
        layoutManagerDois.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewUm = (RecyclerView) findViewById(R.id.recycler_time_um);
        recyclerViewDois = (RecyclerView) findViewById(R.id.recycler_time_dois);

        recyclerViewUm.setHasFixedSize(true);
        recyclerViewUm.setLayoutManager(layoutManager);

        recyclerViewDois.setHasFixedSize(true);
        recyclerViewDois.setLayoutManager(layoutManagerDois);



        listaAdapter = new ListaAdapter(getApplicationContext(),listaum);
        listaAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {

                int itemPosition = recyclerViewUm.getChildLayoutPosition(view);
                ResultsResponse personagem = data.get(itemPosition);
                timeUmSelecionado.add(personagem);

                //TODO pintar elemento selecionado para diferenciar

                //TODO permitir deselecionar




            }
        });
        recyclerViewUm.setAdapter(listaAdapter);


        listaAdapter = new ListaAdapter(getApplicationContext(),listadois);
        listaAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {
                int itemPosition = recyclerViewDois.getChildLayoutPosition(view);
                ResultsResponse personagem = data.get(itemPosition);
                timeDoisSelecionado.add(personagem);
            }
        });
        recyclerViewDois.setAdapter(listaAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.duelar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO salvar dados no banco

                if (timeUmSelecionado.size()>0&&timeDoisSelecionado.size()>0){
                    int nivelPoderTimeUm=0;
                    int nivelPoderTimeDois=0;

                    for(ResultsResponse r :timeUmSelecionado){
                        nivelPoderTimeUm+=r.getStories().getAvailable();
                    }

                    for(ResultsResponse r :timeDoisSelecionado){
                        nivelPoderTimeDois+=r.getStories().getAvailable();
                    }

                    Intent intent = new Intent(TimesActivity.this,ResultadoActivity.class);

                    if (nivelPoderTimeUm>nivelPoderTimeDois){
                        intent.putExtra("timevencedor", (ArrayList<ResultsResponse>) timeUmSelecionado);
                        startActivity(intent);
                    }else{
                        intent.putExtra("timevencedor", (ArrayList<ResultsResponse>) timeDoisSelecionado);
                        startActivity(intent);
                    }

                }else{
                    Snackbar.make(view, "Você deve selecionar pelo menos um personagem para cada time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }


}
