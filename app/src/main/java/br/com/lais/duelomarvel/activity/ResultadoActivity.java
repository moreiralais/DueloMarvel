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
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.adapter.ListaAdapter;
import br.com.lais.duelomarvel.adapter.ListaVencedorAdapter;
import br.com.lais.duelomarvel.modelo.ResultsResponse;

public class ResultadoActivity extends AppCompatActivity {

    private ListaVencedorAdapter listaAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);


        List<ResultsResponse> timevencedor = (List<ResultsResponse>) getIntent().getSerializableExtra("timevencedor");


        List<ResultsResponse> listatimevencedor = new ArrayList<>();


        if(timevencedor!=null ){
            listatimevencedor = timevencedor;

        }

        layoutManager = new LinearLayoutManager(ResultadoActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_time_vencedor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        listaAdapter = new ListaVencedorAdapter(getApplicationContext(),listatimevencedor);

        recyclerView.setAdapter(listaAdapter);

        Button btnnovoduelo = (Button) findViewById(R.id.btnnovoduelo);
        btnnovoduelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultadoActivity.this,InicialActivity.class);
                startActivity(intent);
            }
        });
    }

}
