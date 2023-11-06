package main.urnaeletronica.modelos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Resultado {
    private String candidato;
    private int votosContagem;

    public Resultado(){}

    public Resultado(Candidato candidato, ArrayList<Voto> votos) {
        this.candidato = candidato.getNome();
        try {
            votosContagem = contarVotos(votos, candidato.getNumero());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnsupportedOperationException(ex.getMessage());
        }
    }

    public static int contarBrancoNulos(Map<String,Candidato> candidatos, ArrayList<Voto> votos) {
        Set<String> numerosCandidatos = new HashSet<>();
        int contador = 0;

        for (Map.Entry<String,Candidato> candidato : candidatos.entrySet()) {
            numerosCandidatos.add(candidato.getValue().getNumero());
        }

        for (Voto voto : votos) {
            if (numerosCandidatos.add(voto.getVoto())) {
                contador++;
            }
        }

        return contador;
    }

    private int contarVotos(ArrayList<Voto> votos, String numero) {
        int contador = 0;
        for (Voto voto : votos) {
            String votoStr = voto.getVoto();
            if (votoStr.equals(numero)) {
                contador++;
            }
        }
        return contador;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public int getVotosContagem() {
        return votosContagem;
    }

    public void setVotosContagem(int votosContagem) {
        this.votosContagem = votosContagem;
    }
}
