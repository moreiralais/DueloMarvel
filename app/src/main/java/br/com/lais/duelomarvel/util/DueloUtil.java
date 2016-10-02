package br.com.lais.duelomarvel.util;

import android.content.Context;

import br.com.lais.duelomarvel.R;
import br.com.lais.duelomarvel.activity.InicialActivity;

/**
 * Created by Lais on 30/09/2016.
 */
public class DueloUtil {

    String ts;
    String hash;
    String limit;

    public DueloUtil(){
        setTs(String.valueOf(R.string.ts));
        setLimit(String.valueOf(R.string.limit));
        setHash(String.valueOf(R.string.hash));
    }

    public DueloUtil(Context context){
        setTs(context.getResources().getString(R.string.ts));
        setLimit(context.getResources().getString(R.string.limit));
        setHash(context.getResources().getString(R.string.hash));
    }

    public String getTs() {
        return ts;
    }

    public String getHash() {
        return hash;
    }

    public String getLimit() {
        return limit;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
