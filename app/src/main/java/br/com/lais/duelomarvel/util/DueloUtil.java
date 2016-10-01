package br.com.lais.duelomarvel.util;

import br.com.lais.duelomarvel.R;

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
