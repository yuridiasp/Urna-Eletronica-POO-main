package main.urnaeletronica.controle;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import main.urnaeletronica.modelos.Resultado;

public interface iUrnaController {
    void carregarListaCandidatos() throws NoSuchAlgorithmException, FileNotFoundException;
    void carregarListaEleitores() throws NoSuchAlgorithmException, FileNotFoundException;
    void carregarEleicao() throws NoSuchAlgorithmException, FileNotFoundException;
    void iniciarVotacao();
    void listarResultados();
    void abrirModuloMesario() throws ParseException;
    ArrayList<Resultado> carregarResultados();
    Boolean reiniciarEleicao();
    Boolean validarEleitor();
    Boolean desautorizarEleitor(String id);
    Boolean autorizarEleitor(String id);
    Boolean inserirEleitor(String nome, String nascimento) throws Exception;
    void registrarVoto(String voto) throws Exception;
    String consultarNumeroCandidato(String numero);
}
