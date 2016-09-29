package br.com.lais.duelomarvel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.listener.RecyclerViewOnClickListener;
import br.com.lais.duelomarvel.modelo.ResultsResponse;

/**
 * Created by Lais on 29/09/2016.
 */
public class ListaVencedorAdapter extends RecyclerView.Adapter<ListaVencedorAdapter.ViewHolder>{

    private List<ResultsResponse> resultsResponses;
    private Context contexto;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public ListaVencedorAdapter(Context contexto, List<ResultsResponse> resultsResponses) {
        this.resultsResponses = resultsResponses;
        this.contexto = contexto;
        layoutInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card_time_vencedor, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        holder.nome_personagem.setText(resultsResponses.get(i).getName());
        holder.descricao_personagem.setText(resultsResponses.get(i).getDescription());

        String foto = resultsResponses.get(i).getThumbnail().getPath()+"."+resultsResponses.get(i).getThumbnail().getExtension();

        Picasso.with(contexto)
                .load(foto)
                .placeholder(R.mipmap.ic_account)
                .error(R.mipmap.ic_account)
                .into(holder.foto_personagem);


    }

    @Override
    public int getItemCount() {
        return resultsResponses.size();
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
        recyclerViewOnClickListener = r;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nome_personagem,descricao_personagem;
        private ImageView foto_personagem;

        public ViewHolder(View view) {
            super(view);

            nome_personagem = (TextView)view.findViewById(R.id.nome_personagem);
            descricao_personagem = (TextView)view.findViewById(R.id.descricao_personagem);
            foto_personagem = (ImageView)view.findViewById(R.id.foto_personagem);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(recyclerViewOnClickListener!=null){
                recyclerViewOnClickListener.onClickListener(v,getAdapterPosition());
            }
        }
    }
}
