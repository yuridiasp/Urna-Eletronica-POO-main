package main.urnaeletronica.modelos;

import main.urnaeletronica.modelos.utils.Criptografia;
import main.urnaeletronica.modelos.utils.GeradorId;

public class Voto {
    private String id;
    private String voto;

    
    public Voto(String voto) {
        this.voto = voto;
        id = GeradorId.getId();
    }

    public Voto(String voto, String id) {
        this.voto = voto;
        this.id = id;
    }

    public void salvar() throws Exception {
        String texto = "Voto:"+voto+",Id:"+id;
        String hash = Criptografia.generateHashString(texto);
        String[] dados = {voto, id, hash};
        Arquivos.escreverTxt("votos.txt", dados);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }
}
