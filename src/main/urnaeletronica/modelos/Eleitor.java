package main.urnaeletronica.modelos;

import java.security.NoSuchAlgorithmException;
import main.urnaeletronica.modelos.utils.GeradorId;

public final class Eleitor extends Participante{
    private String id;
    private String dataNascimento;
    private Boolean votou;
    private Boolean autorizado = false;

    public Eleitor(String nomeParticipante, String dataNascimento, String id, boolean votou) {
        super(nomeParticipante);
        this.votou = votou;
        this.dataNascimento=dataNascimento;
        this.id = id;
    }
    
    public Eleitor(String nomeParticipante, String dataNascimento) throws NoSuchAlgorithmException {
        super(nomeParticipante);
        this.votou = false;
        this.dataNascimento=dataNascimento;
        this.id = GeradorId.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean getVotou() {
        return votou;
    }

    public void setVotou(boolean votou) {
        this.votou = votou;
    }

    public void setVotou(Boolean votou) {
        this.votou = votou;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }
}