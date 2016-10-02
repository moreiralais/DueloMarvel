package br.com.lais.duelomarvel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.adapter.ListaAdapter;
import br.com.lais.duelomarvel.listener.RecyclerViewOnClickListener;
import br.com.lais.duelomarvel.modelo.ResultsResponse;
import br.com.lais.duelomarvel.servico.DueloService;

public class TimesActivity extends AppCompatActivity {

    private ListaAdapter listaAdapter;
    private ListaAdapter listaAdapterDois;
    private RecyclerView recyclerViewUm;
    private RecyclerView recyclerViewDois;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManagerDois;
    private List<ResultsResponse> timeUmSelecionado;
    private List<ResultsResponse> timeDoisSelecionado;
    private DueloService dueloService;
    private List<View> listaViewSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);

        final List<ResultsResponse> timeUm = (List<ResultsResponse>) getIntent().getSerializableExtra("timeUm");
        final List<ResultsResponse> timeDois = (List<ResultsResponse>) getIntent().getSerializableExtra("timeDois");

        List<ResultsResponse> listaum = new ArrayList<>();
        List<ResultsResponse> listadois = new ArrayList<>();

        timeUmSelecionado = new ArrayList<>();
        timeDoisSelecionado = new ArrayList<>();
        listaViewSelecionado = new ArrayList<>();

        if(timeUm!=null && timeDois!=null){
            listaum = timeUm;
            listadois = timeDois;
        }

        layoutManager = new LinearLayoutManager(TimesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManagerDois = new LinearLayoutManager(TimesActivity.this);
        layoutManagerDois.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewUm = (RecyclerView) findViewById(R.id.recycler_time_um);
        recyclerViewDois = (RecyclerView) findViewById(R.id.recycler_time_dois);

        recyclerViewUm.setHasFixedSize(true);
        recyclerViewUm.setLayoutManager(layoutManager);

        recyclerViewDois.setHasFixedSize(true);
        recyclerViewDois.setLayoutManager(layoutManagerDois);

        listaAdapter = new ListaAdapter(getApplicationContext(),listaum);
        recyclerViewUm.setAdapter(listaAdapter);
        listaAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {
                int itemPosition = recyclerViewUm.getChildLayoutPosition(view);
                ResultsResponse personagem = timeUm.get(itemPosition);

                if(timeUmSelecionado.contains(personagem)){
                    timeUmSelecionado.remove(personagem);
                    view.setBackgroundColor(getResources().getColor(R.color.White));
                    listaViewSelecionado.remove(view);
                }else {
                    timeUmSelecionado.add(personagem);
                    listaViewSelecionado.add(view);
                    view.setBackgroundColor(getResources().getColor(R.color.OrangeRed));
                }

            }
        });


        listaAdapterDois = new ListaAdapter(getApplicationContext(),listadois);
        recyclerViewDois.setAdapter(listaAdapterDois);
        listaAdapterDois.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {
                int itemPosition = recyclerViewDois.getChildLayoutPosition(view);
                ResultsResponse personagem = timeDois.get(itemPosition);

                if(timeDoisSelecionado.contains(personagem)){
                    timeDoisSelecionado.remove(personagem);
                    listaViewSelecionado.remove(view);
                    view.setBackgroundColor(getResources().getColor(R.color.White));
                }else {
                    timeDoisSelecionado.add(personagem);
                    listaViewSelecionado.add(view);
                    view.setBackgroundColor(getResources().getColor(R.color.OrangeRed));
                }

            }
        });

        Button fab = (Button) findViewById(R.id.duelar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compararNivelPoder(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeUmSelecionado.clear();
        timeDoisSelecionado.clear();
        for(View v:listaViewSelecionado){
            v.setBackgroundColor(getResources().getColor(R.color.White));
        }

    }

    private void compararNivelPoder(View view) {
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

            dueloService = new DueloService(TimesActivity.this);

            if (nivelPoderTimeUm>nivelPoderTimeDois){
                intent.putExtra("timevencedor", (ArrayList<ResultsResponse>) timeUmSelecionado);
                dueloService.inserir(timeUmSelecionado,timeDoisSelecionado);
                startActivity(intent);
            }else{
                intent.putExtra("timevencedor", (ArrayList<ResultsResponse>) timeDoisSelecionado);
                dueloService.inserir(timeDoisSelecionado,timeUmSelecionado);
                startActivity(intent);
            }

        }else{
            Snackbar.make(view, "Você deve selecionar pelo menos um personagem para cada time", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


}
