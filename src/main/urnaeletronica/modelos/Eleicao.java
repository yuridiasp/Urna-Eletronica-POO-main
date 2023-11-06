package main.urnaeletronica.modelos;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import main.urnaeletronica.controle.UrnaController;
import main.urnaeletronica.modelos.utils.Criptografia;

public class Eleicao {
    private Map<String,Candidato> candidatos;
    private Map<String,Eleitor> eleitores;
    private ArrayList<Voto> votos;
    private ArrayList<Resultado> resultados;
    private Map<String,ControleFile> controles;
    private boolean aberta = true;

    public Eleicao(UrnaController controller) throws NoSuchAlgorithmException, FileNotFoundException {
        candidatos = Arquivos.getCandidatos();
        eleitores = Arquivos.getEleitores();
        votos = Arquivos.getVotos();
        controles = Arquivos.getControle();
    }

    public void validarCandidatos() throws Exception{
        int contador = 0;
        for (Map.Entry<String, Candidato> candidato : candidatos.entrySet()) {
            contador++;
            String txt = "Candidato:" + candidato.getValue().getNome() + ",Numero:" + candidato.getValue().getNumero();
            String hash = Criptografia.generateHashString(txt);
            Boolean validationHash = Criptografia.Validarhash(hash, hash);

            if (!validationHash) {
                throw new Exception("Falha na validação do arquivo de candidato. O Hash da linha " + contador + " é inválido.");
            }
        }
    }

    public void validarEleitores() throws Exception{
        int contador = 0;
        for (Map.Entry<String, Eleitor> eleitor : eleitores.entrySet()) {
            contador++;
            String txt = "Eleitor:" + eleitor.getValue().getNome() + ",Nascimento:" + eleitor.getValue().getDataNascimento() + ",Id:" + eleitor.getValue().getId();
            String hash = Criptografia.generateHashString(txt);
            Boolean validationHash = Criptografia.Validarhash(hash, hash);

            if (!validationHash) {
                throw new Exception("Falha na validação do arquivo de eleitor. O Hash da linha " + contador + " é inválido.");
            }
        }
    }

    public void validarVotos() throws Exception{
        int contador = 0;
        for (Voto voto : votos) {
            contador++;
            String txt = "Voto:" + voto.getVoto() + ",Id:" + voto.getId();
            String hash = Criptografia.generateHashString(txt);
            Boolean validationHash = Criptografia.Validarhash(hash, hash);

            if (!validationHash) {
                throw new Exception("Falha na validação do arquivo de votos. O Hash da linha " + contador + " é inválido.");
            }
        }
    }

    public void validarArquivosControle() throws Exception{
        String hashCandidatosFile = Criptografia.getHashCandidatos();
        String hashEleitoresFile = Criptografia.getHashEleitores();
        String hashVotosFile = Criptografia.getHashVotos();
        if (!hashCandidatosFile.equals(controles.get("candidatos").getHash())) {
            throw new Exception("Falha na validação do arquivo de candidatos. O Hash do arquivo não confere com o hash de controle.");
        }
        if (!hashEleitoresFile.equals(controles.get("eleitores").getHash())) {
            throw new Exception("Falha na validação do arquivo de eleitores. O Hash do arquivo não confere com o hash de controle.");
        }
        if (!hashVotosFile.equals(controles.get("votos").getHash())) {
            throw new Exception("Falha na validação do arquivo de votos. O Hash do arquivo não confere com o hash de controle.");
        }
    }

    public Map<String, Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(Map<String, Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public Map<String, Eleitor> getEleitores() {
        return eleitores;
    }

    public void setEleitores(Map<String, Eleitor> eleitores) {
        this.eleitores = eleitores;
    }

    public ArrayList<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(ArrayList<Resultado> resultados) {
        this.resultados = resultados;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public Map<String, ControleFile> getControles() {
        return controles;
    }

    public void setControles(Map<String, ControleFile> controles) {
        this.controles = controles;
    }

    public ArrayList<Voto> getVotos() {
        return votos;
    }

    public void setVotos(ArrayList<Voto> votos) {
        this.votos = votos;
    }
}
