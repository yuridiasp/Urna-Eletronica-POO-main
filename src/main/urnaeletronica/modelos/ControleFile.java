package main.urnaeletronica.modelos;

public class ControleFile {
    public String id;
    private String hash;

    public ControleFile(){}

    public ControleFile(String id, String hash) {
        this.hash = hash;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
