package main.urnaeletronica.modelos;

public final class Candidato extends Participante{
    private String numero;

    public Candidato(String nome, String numero){
        super(nome);
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}