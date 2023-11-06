package main.urnaeletronica.modelos;

public abstract class Participante{
    private String nome;

    public Participante(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}