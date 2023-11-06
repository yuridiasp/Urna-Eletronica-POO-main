package main.urnaeletronica.modelos;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import main.urnaeletronica.modelos.utils.Criptografia;
public final class Arquivos {

    private static String getLocalDeTrabalho(){
        return System.getProperty("user.dir");
    }

    
    public static boolean criarArquivo(String arquivoTxt) throws IOException {
        String caminho = getLocalDeTrabalho();
        File file = new File(caminho + arquivoTxt);
        boolean arquivoCriado;
        arquivoCriado = file.createNewFile();
        return arquivoCriado;
    }

    /**
     * Método responsável por fazer a leitura dos arquivos do programa. Verifica-se se o arquivo existe, caso contrário irá criá-lo, e fára a leitura.
     * @param arquivotxt é o nome do arquivo com extensão .txt salvo na pasta main da aplicação.
     * @return retorna um ArrayList de array de Strings com o seguinte em que cada array de String corresponde a uma linha do arquivo e cada indice segue o seguinte formato:
     * <p>
     * candidatos.txt:
     * <p>
     * {@code indice 0}: nome do candidato
     * <p>
     * {@code indice 1}: número do candidato
     * <p>
     * {@code indice 2}: hash da linha
     * <p>
     * votos.txt: 
     * <p>
     * {@code indice 0}: nome do candidato votado
     * <p>
     * {@code indice 1}: id único do voto
     * <p>
     * {@code indice 2}: hash da linha
     * <p>
     * eleitores.txt: 
     * <p>
     * {@code indice 0}: nome do eleitor
     * <p>
     * {@code indice 1}: data de nascimento do eleitor
     * <p>
     * {@code indice 2}: id único do eleitor
     * <p>
     * {@code indice 3}: hash da linha
     * <p>
     * controle.txt: 
     * <p>
     * {@code Linha 1 | indice 0}: controle de votos
     * <p>
     * {@code Linha 1 | indice 1}: hash da linha de votos
     * <p>
     * {@code Linha 2 | indice 0}: controle de eleitores
     * <p>
     * {@code Linha 2 | indice 1}: hash da linha de eleitores
     * <p>
     * {@code Linha 3 | indice 0}: controle de candidatos
     * <p>
     * {@code Linha 3 | indice 1}: hash da linha de candidatos
     * <p>
     * @throws FileNotFoundException
     */
    public static ArrayList<String[]> lerTxtNaoFormatado(String arquivotxt) throws FileNotFoundException {
        final String localDeTrabalho = getLocalDeTrabalho();
        final String caminho = localDeTrabalho+"/main/"+ arquivotxt;
        ArrayList<String[]> linhas = new ArrayList<String[]>();
        Scanner leitor;
        try {
            File file = new File(caminho);
            if (!file.exists()) {
                try {
                    boolean criado = file.createNewFile();
                    inicializarArquivoCandidato();
                    if (arquivotxt.equals("candidatos.txt"))
                    if (!criado) {
                        throw new IOException("Não há um arquivo " + arquivotxt + "no diretório " + caminho + " e ao tentar criá-lo, houve um erro.");
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            leitor = new Scanner(file);
            while(leitor.hasNext()){
                String linha = leitor.next();
                if(linha.contains("Candidato:")){
                    String[] partes =  linha.split(",");
                    String candidato = partes[0].substring(partes[0].indexOf(":")+1);
                    String numero = partes[1].substring(partes[1].indexOf(":")+1);
                    String hash = partes[2].substring(partes[2].indexOf(":")+1);
                    String[] valores = {candidato,numero, hash};
                    linhas.add(valores);
                }
                else if (linha.contains("Eleitor:")){
                    String[] partes = linha.split(",");
                    String eleitor = partes[0].substring(partes[0].indexOf(":")+1);
                    String nascimento = partes[1].substring(partes[1].indexOf(":")+1);
                    String id = partes[2].substring(partes[2].indexOf(":")+1);
                    String votou = partes[3].substring(partes[3].indexOf(":")+1);
                    String hash = partes[4].substring(partes[4].indexOf(":")+1);
                    String[] valores = {eleitor,nascimento,id,votou,hash};
                    linhas.add(valores);
                }
                else if (linha.contains("Voto:")){
                    String[] partes = linha.split(",");
                    String voto = partes[0].substring(partes[0].indexOf(":")+1);
                    String id = partes[1].substring(partes[1].indexOf(":")+1);
                    String hash = partes[2].substring(partes[2].indexOf(":")+1);
                    String[] valores = {voto,id,hash};
                    linhas.add(valores);
                }
                else if (linha.contains("Controle:")){
                    String[] partes = linha.split(",");
                    String controle = partes[0].substring(partes[0].indexOf(":")+1);
                    String hash = partes[1].substring(partes[1].indexOf(":")+1);
                    String[] valores = {controle,hash};
                    linhas.add(valores);
                }
            }
            leitor.close();
            return linhas;
        } catch (FileNotFoundException e) {
            throw e;
        }
        catch(Exception e){
            return linhas;
        }
    }
    public static void escreverTxt(String arquivotxt,String[] dadosParaEscrever) throws Exception{
        String localDeTrabalho = getLocalDeTrabalho();
        File arquivo = new File(localDeTrabalho+"/main/"+arquivotxt);
        Boolean existeArquivo = arquivo.exists();
        ArrayList<String[]> dados;
        if (existeArquivo){
            try {
                dados = Arquivos.lerTxtNaoFormatado(arquivotxt);
                dados.add(dadosParaEscrever);
            } catch (Exception e) {
                throw e;
            }
        }
        else{
            dados = new ArrayList<String[]>();
            dados.add(dadosParaEscrever);
        }
        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo));
            if (arquivotxt=="candidatos.txt"){
                for (String[] linha:dados){
                    escritor.write("Candidato:"+linha[0]+",Numero:"+linha[1]+",Hash:"+linha[2]);
                    escritor.newLine();
                }
            }
            else if (arquivotxt=="controle.txt"){
                for (String[] linha:dados){
                    escritor.write("Controle:"+linha[0]+",Hash:"+linha[1]);
                    escritor.newLine();
                }
            }
            else if (arquivotxt=="votos.txt"){
                for (String[] linha:dados){
                    escritor.write("Voto:"+linha[0]+",Id:"+linha[1]+",Hash:"+linha[2]);
                    escritor.newLine();
                }
            }
            else if (arquivotxt=="eleitores.txt"){
                for (String[] linha:dados){
                    escritor.write("Eleitor:"+linha[0]+",Nascimento:"+linha[1]+",Id:"+linha[2]+",votou:"+linha[3]+",Hash:"+linha[4]);
                    escritor.newLine();
                }
            }
            escritor.close();
            
        }
        catch (IOException e) {
            System.out.println("Impossível escrever");
        }
        catch (Exception e){
            System.out.println("Ops! algo deu errado");
            System.out.println("Verifique se os caminhos dos arquivos, os nomes para os mesmos e o formato está de acordo com os padrões");
        }
    }

    public static void inicializarArquivoCandidato() {
        String txt1 = "Candidato:Biscoito,Numero:157";
        String txt2 = "Candidato:Bolacha,Numero:171";
        String hash1 = "", hash2 = "";
        
        try {
            hash1 = Criptografia.generateHashString(txt1);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            hash2 = Criptografia.generateHashString(txt2);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] dados1 = {"Biscoito","157", hash1};
        String[] dados2 = {"Bolacha","171", hash2};
        System.out.println(hash1);
        System.out.println(hash2);

        try {
            escreverTxt("candidatos.txt", dados1);
            escreverTxt("candidatos.txt", dados2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Map<String,Candidato> getCandidatos() throws NoSuchAlgorithmException, FileNotFoundException {
        Set<String> numero = new HashSet<>();
        ArrayList<String[]> linhasCandidato = lerTxtNaoFormatado("candidatos.txt");
        Map<String,Candidato> c = new HashMap<>();
        for (String[] linhas : linhasCandidato) {
            if (!numero.add(linhas[1])) {
                throw new IllegalArgumentException("Erro na leitura do arquivo de Candidatos. Existem candidatos com numeração duplicada.");
            }
            Candidato candidato = new Candidato(linhas[0],linhas[1]);
            c.put(candidato.getNumero(),candidato);
        }
        return c;
    }

    public static ArrayList<Voto> getVotos() throws NoSuchAlgorithmException, FileNotFoundException {
        Set<String> votoStr = new HashSet<>();
        ArrayList<String[]> linhasVoto = lerTxtNaoFormatado("votos.txt");
        ArrayList<Voto> v = new ArrayList<Voto>();
        for (String[] linhas : linhasVoto) {
            if (votoStr.add(linhas[1])) {
                Voto voto = new Voto(linhas[0],linhas[1]);
                v.add(voto);
            }
        }

        return v;
    }

    public static Map<String,Eleitor> getEleitores() throws NoSuchAlgorithmException, FileNotFoundException {
        Set<String> eleitorStr = new HashSet<>();
        ArrayList<String[]> linhasEleitor = lerTxtNaoFormatado("eleitores.txt");
        Map<String,Eleitor> e = new HashMap<>();
        for (String[] linhas : linhasEleitor) {
            Boolean votou = linhas[3].equals("true");
            if (eleitorStr.add(linhas[2])) {
                Eleitor eleitor = new Eleitor(linhas[0],linhas[1], linhas[2], votou);
                e.put(eleitor.getId(), eleitor);
            }
        }
        return e;
    }

    public static Map<String,ControleFile> getControle() throws FileNotFoundException {
        ArrayList<String[]> linhasControle = lerTxtNaoFormatado("controle.txt");
        Map<String,ControleFile> controles = new HashMap<>();
        for (String[] linhas : linhasControle) {
            ControleFile c = new ControleFile(linhas[0], linhas[1]);
            controles.put(linhas[0], c);
        }
        return controles;
    }

    public static String getLinhaToString(String[] str, String type) {
        String texto;
        if (type == "candidato") {
            texto = "Candidato:"+str[0]+",Numero:"+str[1];
        } else if (type == "eleitor") {
            texto = "Eleitor:"+str[0]+",Nascimento:"+str[1]+",Id"+str[2];
        } else {
            texto = "Voto:"+str[0]+",Id:"+str[1];
        }
        return texto;
    }

    public static ArrayList<String[]> lerTxtFormatado(String arquivoTxt){
        final String localDeTrabalho = getLocalDeTrabalho();
        final String caminho = localDeTrabalho+"/main/"+ arquivoTxt;
        ArrayList<String[]> linhas = new ArrayList<String[]>();
        try {
            Scanner leitor = new Scanner(new File(caminho));
            while (leitor.hasNext()) {
                String linha = leitor.next();
                linhas.add(linha.split(","));
            }
        } catch (Exception e) {
            System.out.println("Algo deu errado! Mas é assim mesmo, tente de novo");
        }
        return linhas;
    }
    public static Boolean compararDados(String arquivoTxt,ArrayList<String[]> dadosComparar){
        ArrayList<String[]> dado1 = lerTxtFormatado(arquivoTxt);
        ArrayList<String[]> dado2 = dadosComparar;
        ArrayList<String> lista1 = new ArrayList<String>();
        ArrayList<String> lista2 = new ArrayList<String>();
        for(String[] linha:dado1){
            lista1.add(Arrays.toString(linha));
        }
        for(String[] linha:dado2){
            lista2.add(Arrays.toString(linha));
        }
        return lista1.equals(lista2);
    }

    public static void limparArquivo(String arquivoTxt) throws IOException {
        String caminho = getLocalDeTrabalho() +"\\main\\" + arquivoTxt;
        File arquivo = new File(caminho);
        System.out.println(caminho);
        if (!arquivo.exists()) {
            if (!Arquivos.criarArquivo(arquivoTxt)) {
                throw new FileNotFoundException("O arquivo "+ arquivoTxt +" não existe e não foi possível gerá-lo.");
            }
        }

        // Cria um escritor com o arquivo especificado
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));

        //Escreve uma string vazia
        writer.write("");

        // Fecha o FileWriter para garantir que o conteúdo seja gravado
        writer.close();

        System.out.println("Arquivo sobrescrito com sucesso.");
    }
}