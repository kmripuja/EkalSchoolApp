package ekalschoolapp.pooja.com.ekalschoolapp;

/**
 * Created by Puja on 3/15/2017.
 */

public class Storagefiles {
    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) { this.url = url; }

    public Storagefiles(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Storagefiles(){}
}
