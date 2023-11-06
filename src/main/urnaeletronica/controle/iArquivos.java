package main.urnaeletronica.controle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.urnaeletronica.modelos.Arquivos;
import main.urnaeletronica.modelos.Candidato;
import main.urnaeletronica.modelos.Voto;
/**
 * Descrição dos métodos:
 * comparar(): Recebe uma string que especifica um arquivo que será usado para fins de copmparação e um ArrayList
 * contendo linhas no formato String[]
 * O arquivo será carregado em memória de modo que ele possua o mesmo formato que a ArrayList a ser comparada
 * e então verifica se os dados de ambas são iguais
 * escreverArquivo(): Recebe uma string para especificar o nome do arquivo que será escrito e uma ArrayList
 * contendo cada linha no formato String[] para ser escrita no arquivo correspondente
 * O método verifica se o arquivo já existe para evitar sobrescrita e garantir a adição de valores novos quando necessário
 * ou criação de um novo quando o arquivo não existe
 * lerTxtNaoFormatado(): Recebe o nome do arquivo a ser lido
 * Lê o arquivo linha por linha e entrega uma ArrayList de String[]
 * onde cada String[] contem todo o conteúdo da linha do arquivo separados por vírgula
 * Exemplo: arquivo possui a linha Candidato:Biscoito,Id:8, a String[] sera {"Candidato:Biscoito", "Id:8"}
 * Esse formato é usado apeans para facilitar a escrita do arquivo
 * lerTxtFormatado(): Esse método faz algo muito semelhante ao método anterior, porém, salva apenas os atributos de cada campo
 * Exemplo: arquivo possui a linha Candidato:Biscoito,Id:8, a String[] sera {"Biscoito", "8"}
 * Esse método é usado para facilitar o processamento pois, sabendo de qual arquivo os dados estão vindo,
 * é possível saber do que se trata cada valor e usar esses valores sme necessidade de tratamento adicional
 */
public interface iArquivos {
    public static Boolean comparar(String arquivo,ArrayList<String[]> dadosDeProva) {
        return Arquivos.compararDados(arquivo, dadosDeProva);
    }
    public static void escreverArquivo(String arquivos, String[] dadosParaEscrever) throws Exception{
        Arquivos.escreverTxt(arquivos,dadosParaEscrever);
    }
    public static ArrayList<String[]> lerTxtNaoFormatado(String arquivo) throws FileNotFoundException{
        return Arquivos.lerTxtNaoFormatado(arquivo);
    }
    public static ArrayList<String[]> lerTxtFormatado(String arquivo) {
        return Arquivos.lerTxtFormatado(arquivo);
    }
    public ArrayList<Candidato> getCandidatos();
    public ArrayList<Voto> getEleitor();
    public ArrayList<Voto> getVotos();
}