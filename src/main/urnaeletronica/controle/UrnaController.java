package main.urnaeletronica.controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import main.urnaeletronica.modelos.Arquivos;
import main.urnaeletronica.modelos.Candidato;
import main.urnaeletronica.modelos.Eleicao;
import main.urnaeletronica.modelos.Eleitor;
import main.urnaeletronica.modelos.Resultado;
import main.urnaeletronica.modelos.Voto;
import main.urnaeletronica.modelos.utils.Criptografia;
import main.urnaeletronica.visual.*;
import main.urnaeletronica.visual.util.MensagemDialogo;

public class UrnaController implements iUrnaController {
    private Eleicao eleicao;
    private UrnaEletronicaView telaUrna;
    private ResultadosView telaResultados;
    private MesarioView telaMesario;
    private String numeroDigitado;
    private MenuPrincipalView menuPrincipalView;
    private Boolean permissao = true;
    private String idEleitorAutorizado = "";
    
    
    public UrnaController () {
        try {
            carregarEleicao();
            menuPrincipalView = new MenuPrincipalView(this);
        } catch (Exception e) {
            e.printStackTrace();
            permissao = false;
            String msg = e.getMessage();
            menuPrincipalView = new MenuPrincipalView(this, msg);
        }
        menuPrincipalView.setDefaultCloseOperation(MenuPrincipalView.EXIT_ON_CLOSE);
    }
    
    public void iniciarVotacao() {
        telaUrna = new UrnaEletronicaView();
    }

    public Boolean atualizarPermissao() throws Exception {
        try {
            eleicao.validarCandidatos();
            eleicao.validarEleitores();
            eleicao.validarVotos();
            eleicao.validarArquivosControle();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        permissao = true;
        return true;
    }
    
    @Override
    public void listarResultados() {
        try {
            telaResultados = new ResultadosView();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void abrirModuloMesario() throws ParseException {
        telaMesario = new MesarioView();
    }
    
    @Override
    public void carregarListaCandidatos() throws NoSuchAlgorithmException, FileNotFoundException {
        eleicao.setCandidatos(Arquivos.getCandidatos());
    }

    @Override
    public void carregarListaEleitores() throws NoSuchAlgorithmException, FileNotFoundException {
        eleicao.setEleitores(Arquivos.getEleitores());
    }

    @Override
    public void carregarEleicao() throws NoSuchAlgorithmException, FileNotFoundException {
        eleicao = new Eleicao(this);
    }


    @Override
    public ArrayList<Resultado> carregarResultados() {
        ArrayList<Resultado> r = new ArrayList<Resultado>();
        try {
            Map<String,Candidato> candidatos = Arquivos.getCandidatos();
            if (candidatos.size() == 0) {
                throw new ArrayIndexOutOfBoundsException("Arquivo de candidatos está vazio.");
            }
            ArrayList<Voto> votos = Arquivos.getVotos();
            if (votos.size() > 0) {
                for (Entry<String, Candidato> candidato : candidatos.entrySet()) {
                    Resultado resultado = new Resultado(candidato.getValue(), votos);
                    r.add(resultado);
                }
                Resultado bN = new Resultado();
                bN.setCandidato("Brancos e Nulos");
                bN.setVotosContagem(Resultado.contarBrancoNulos(candidatos, votos));
                r.add(bN);
            } else {
                throw new ArrayIndexOutOfBoundsException("Arquivo de votos está vazio.");
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return r;
    }

    @Override
    public Boolean reiniciarEleicao() {
        try {
            Arquivos.limparArquivo("votos.txt");
            atualizarArquivoControle();
        } catch (Exception e) {
            e.printStackTrace();
            MensagemDialogo.mostrarMensagemDialogo(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Boolean autorizarEleitor(String id) {
        Eleitor e = eleicao.getEleitores().get(id);
        if (!e.getVotou() && !e.getAutorizado()) {
            e.setAutorizado(true);
            idEleitorAutorizado = id;
            return true;
        }
        return false;
    }

    @Override
    public Boolean desautorizarEleitor(String id) {
        Eleitor e = eleicao.getEleitores().get(id);
        e.setAutorizado(false);
        idEleitorAutorizado = "";
        return true;
    }

    @Override
    public Boolean inserirEleitor(String nome, String nascimento) throws Exception {
        Eleitor e = new Eleitor(nome, nascimento);
        eleicao.getEleitores().put(e.getId(), e);
        String texto = "Eleitor:"+e.getNome()+",Nascimento:"+e.getDataNascimento()+",Id"+e.getId()+",votou:"+e.getVotou();
        String hash = Criptografia.generateHashString(texto);
        String [] dados = {e.getNome(),e.getDataNascimento(),e.getId(),"false",hash};
        Arquivos.escreverTxt("eleitores.txt", dados);
        atualizarArquivoControle();
        return true;
    }

    @Override
    public Boolean validarEleitor() {
        if (idEleitorAutorizado.length() > 0) {
            Eleitor eleitor = eleicao.getEleitores().get(idEleitorAutorizado);
    
            if (!eleitor.getVotou() && eleitor.getAutorizado()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registrarVoto(String voto) throws Exception {
        Voto v = new Voto(voto);
        v.salvar();
        eleicao.getEleitores().get(idEleitorAutorizado).setVotou(true);
        atualizarArquivoEleitores();
        atualizarArquivoControle();
    }

    public void atualizarArquivoEleitores() throws Exception {
        try {
            Arquivos.limparArquivo("eleitores.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Map.Entry<String,Eleitor> eleitor : eleicao.getEleitores().entrySet()) {
            String txt = "Eleitor:"+eleitor.getValue().getNome()+",Nascimento:"+eleitor.getValue().getDataNascimento()+",Id:"+eleitor.getValue().getId();
            String hash = Criptografia.generateHashString(txt);
            String votou = eleitor.getValue().getVotou() ? "true" : "false";
            String[] dados = {eleitor.getValue().getNome(),eleitor.getValue().getDataNascimento(),eleitor.getValue().getId(),votou,hash};
            Arquivos.escreverTxt("eleitores.txt", dados);
        }
    }

    public void atualizarArquivoControle() throws Exception {
        try {
            Arquivos.limparArquivo("controle.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String hashCandidatosFile = Criptografia.getHashCandidatos();
        String hashEleitoresFile = Criptografia.getHashEleitores();
        String hashVotosFile = Criptografia.getHashVotos();

        String[] dadosCandidados = {"candidatos",hashCandidatosFile};
        String[] dadosEleitores = {"eleitores",hashEleitoresFile};
        String[] dadosVotos = {"votos",hashVotosFile};
        
        Arquivos.escreverTxt("controle.txt", dadosCandidados);
        Arquivos.escreverTxt("controle.txt", dadosEleitores);
        Arquivos.escreverTxt("controle.txt", dadosVotos);

    }

    public String getIdEleitorAutorizado() {
        return idEleitorAutorizado;
    }

    public void setIdEleitorAutorizado(String idEleitorAutorizado) {
        this.idEleitorAutorizado = idEleitorAutorizado;
    }

    public Eleicao getEleicao() {
        return eleicao;
    }

    public Boolean getPermissao() {
        return permissao;
    }

    public void setPermissao(Boolean permissao) {
        this.permissao = permissao;
    }

    @Override
    public String consultarNumeroCandidato(String numero) {
        Candidato c = eleicao.getCandidatos().get(numero);

        if (c != null) {
            return c.getNome();
        }
        return "Nulo";
    }
}