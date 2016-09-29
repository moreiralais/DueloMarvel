package br.com.lais.duelomarvel.modelo;

import java.io.Serializable;

/**
 * Created by Lais on 29/09/2016.
 */
public class Thumbnail implements Serializable {

    String path;
    String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
