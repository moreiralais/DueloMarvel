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
import android.widget.Toast;

import java.util.AbstractList;
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
    private List<ResultsResponse> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutManager = new LinearLayoutManager(TimesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewUm = (RecyclerView) findViewById(R.id.recycler_time_um);
        recyclerViewDois = (RecyclerView) findViewById(R.id.recycler_time_dois);

        recyclerViewUm.setHasFixedSize(true);
        recyclerViewUm.setLayoutManager(layoutManager);

        recyclerViewDois.setHasFixedSize(true);
        recyclerViewDois.setLayoutManager(layoutManager);


        ArrayList<ResultsResponse> listaum = null;
        listaAdapter = new ListaAdapter(getApplicationContext(),listaum);
        listaAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {

                int itemPosition = recyclerViewUm.getChildLayoutPosition(view);
                ResultsResponse item = data.get(itemPosition);

                //TODO inserir item clicado na lista, calcular nivel poder salvar dados no banco exibir vencedoes com descricao e foto

            }
        });
        recyclerViewUm.setAdapter(listaAdapter);

        ArrayList<ResultsResponse> listadois = null;
        listaAdapter = new ListaAdapter(getApplicationContext(),listadois);
        listaAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int posicao) {

            }
        });
        recyclerViewDois.setAdapter(listaAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.duelar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


}
