package br.com.lais.duelomarvel.servico;

import android.content.Context;

import java.util.List;

import br.com.lais.duelomarvel.dao.DueloDAO;
import br.com.lais.duelomarvel.modelo.ResultsResponse;

/**
 * Created by Lais on 01/10/2016.
 */
public class DueloService {

    private DueloDAO dao;
    private Context context;

    public DueloService(Context context){

        this.context = context;
        dao = new DueloDAO(this.context);
    }

    public void inserir(List<ResultsResponse> timeVitorioso, List<ResultsResponse> timePerdedor){
        dao.inserir(timeVitorioso,timePerdedor);
        dao.close();
    }
}
