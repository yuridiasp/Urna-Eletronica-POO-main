package main.urnaeletronica.visual.component;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.*;
import java.nio.file.*;

/**
 * Classe para gerar o visor da urna eletrônica.
 */
public class VisorVotacaoView extends JPanel {
    private String cargo;
    private int numerosCargo;
    private JLabel tituloEleicao, jlImgCandidato, jlNome;
    private JLabel[] numeroContainer;
    private JPanel panelPrincipal, panelCandidato, panelFim, jpInstructions;
    private CardLayout cardLayout;
    private ImageIcon iImgCandidato;
    private String caminhoImages;

    /**
     * Construtor sobrecarregado em que o paramêtro cargo é obrigatório e num é opcional
     * @param cargo é o título da eleição
     * @param num é o número de caracteres que cada candidato possui em sua numeração de identificação. Portanto, se a eleição é com candidatos que possuem dois digitos (00 até 99) o "num" é 2.
     */
    public VisorVotacaoView(String cargo) {
        super(null);
        ConstruirVisor(3, cargo, 50, 207, 547, 319);
    }

    
    public VisorVotacaoView(int num, String cargo) {
        super(null);
        ConstruirVisor(num, cargo, 50, 207, 547, 319);
    }

    public VisorVotacaoView(int num, String cargo, int x, int y, int width, int height) {
        super(null);
        ConstruirVisor(num, cargo, x, y, width, height);
    }

    public void ConstruirVisor(int num, String cargo, int x, int y, int width, int height) {
        this.cargo = cargo;
        numerosCargo = num;
        numeroContainer = new JLabel[numerosCargo];
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBounds(x, y, width, height);
        setCaminhoImages();

        panelPrincipal = new JPanel(null);
        panelPrincipal.setSize(this.getWidth(), this.getHeight());
        contruirPanelPrincipal(panelPrincipal);
        esconderDadosVoto();
        
        panelFim = new JPanel(null);
        panelFim.setSize(this.getWidth(), this.getHeight());
        construirPanelFim(panelFim);
        
        add(panelPrincipal, "panelPrincipal");
        add(panelFim, "panelFim");
        
        cardLayout.show(this, "panelPrincipal");
    }

    public void setCaminhoImages() {
        /**
         * Definindo o caminho relativo da aplicação com base na localização do diretório
         */
        String caminhoUser = System.getProperty("user.dir");
        //this.caminhoImages = caminhoUser + "\\main\\resources\\candidatos\\images\\";
        this.caminhoImages = Paths.get(caminhoUser,"main","resources","candidatos","images").toString();
    }

    public void contruirPanelPrincipal(JPanel panel) {
        tituloEleicao = new JLabel(cargo);        
        int [] dim = calcularCoordenadasComponent(tituloEleicao);
        tituloEleicao.setBounds(dim[0], 50, dim[2], dim[3]);
        generateLabelsDigitos(panel,dim[0], dim[1]);

        iImgCandidato = new ImageIcon(caminhoImages + "Bolacha.png");
        jlImgCandidato = new JLabel(iImgCandidato);
        int x = getWidth() - iImgCandidato.getIconWidth();
        int y = 0;
        jlImgCandidato.setBounds(x, y, iImgCandidato.getIconWidth(), iImgCandidato.getIconHeight());
        
        jlNome = new JLabel("BOLACHA");
        Font font = jlNome.getFont();
        Font fontGrande = font.deriveFont(font.getSize() + 30f);
        jlNome.setFont(fontGrande);
        dim = calcularCoordenadasComponent(jlNome);
        jlNome.setBounds(dim[0], dim[1]+dim[3], dim[2], dim[3]);

        jpInstructions = new JPanel();
        jpInstructions.setBounds(1, 270, 365, 100);

        JLabel lAperte = new JLabel("Aperte a tecla:");
        lAperte.setBounds(5, 0, 150, 20);
        
        JLabel lConfirma = new JLabel("CONFIRMA para CONFIRMAR este voto");
        lConfirma.setBounds(100, 15, 200, 20);
        
        JLabel lCorrige = new JLabel("CORRIGE para REINICIAR este voto");   
        lCorrige.setBounds(0, 30, 95, 20);

        jpInstructions.add(lAperte);
        jpInstructions.add(lConfirma);
        jpInstructions.add(lCorrige);

        panel.add(jlImgCandidato);
        panel.add(tituloEleicao);
        panel.add(jlNome);
        panel.add(jpInstructions);
    }

    public void construirPanelFim(JPanel panel) {
        JLabel fimText = new JLabel("FIM");
        Font font = fimText.getFont();
        Font fontGrande = font.deriveFont(font.getSize() + 80f);
        fimText.setFont(fontGrande);
        int [] dim = calcularCoordenadasComponent(fimText);
        fimText.setBounds(dim[0], dim[1], dim[2], dim[3]);
        panel.add(fimText);
    }

    /**
     * O método calcula o tamanho e localização de um componente para centralizá-lo em relação ao componente pai
     * @param component é um componente Java Swing
     * @return é um array de 4 inteiros, sendo composto pela coordenada x, coordenada y, larguta e altura, respectivamente
     */
    private int [] calcularCoordenadasComponent(Component component) {
        // obtendo as dimensões do titulo
        int componentWidth = component.getPreferredSize().width;
        int componentHeight = component.getPreferredSize().height;
        int visorWidth = getWidth();
        int visorHeigth = getHeight();
        
        // calculando as coordenadas x e y do titulo para centralizá-lo
        int x = (visorWidth - componentWidth) / 2;
        int y = (visorHeigth - componentHeight) / 2;

        int [] coordenadas = { x , y, componentWidth, componentHeight };

        return coordenadas;
    }

    /**
     * Método responsável por gerar e posicionar os labels que receberão os números digitados pelo usuário.
     * @param x coordenada horizontal de referência para posicionamento inicial dos labels.
     * @param y coordenada vertical de referência para posicionamento inicial dos labels.
     */
    private void generateLabelsDigitos(JPanel panel,int x, int y) {
        for (int c = 0; c < numerosCargo; c++) {
            numeroContainer[c] = new JLabel("");
            Border borda = BorderFactory.createLineBorder(Color.BLACK, 1);
            numeroContainer[c].setBorder(borda);
            numeroContainer[c].setBounds(x + (c*35), y, 30, 30);
            numeroContainer[c].setHorizontalAlignment(SwingConstants.CENTER);;
            panel.add(numeroContainer[c]);
        }
    }

    /**
     * Método que atualizar o conteúdo de texto dos labels dos números no visor da votação
     * @param s é um array de char contendo caracteres correspondentes as teclas de 0 a 9 da urna.
     */
    public void atualizarTelaVotacao(char [] s) {
        for (int c = 0; c < numeroContainer.length; c++) {
            numeroContainer[c].setText(String.valueOf(s[c]));
        }
    }

    /**
     * Método responsável por limpar a string dos números digitados
     */
    public void redefinirNumeroDigitado() {
        for (int c = 0; c < numeroContainer.length; c++) {
            numeroContainer[c].setText("");
        }
    }

    /**
     * Exibe os dados do voto do eleitor
     */
    public void mostrarDadosVoto() {
        jlImgCandidato.setVisible(true);
        jlNome.setVisible(true);
        jpInstructions.setVisible(true);
    }
    
    /**
     * Esconde os dados do voto do eleitor
     */
    public void esconderDadosVoto() {
        jlImgCandidato.setVisible(false);
        jlNome.setVisible(false);
        jpInstructions.setVisible(false);
    
    }

    public void alterarFotoCandidato(String nome) {
        iImgCandidato = new ImageIcon(caminhoImages + nome + ".png");
        jlImgCandidato.setIcon(iImgCandidato); 
    }

    public void encerrarVotacao() {
        cardLayout.show(this, "panelFim");
    }

    public String getCargo() {
        return cargo;
    }


    public void setCargo(String cargo) {
        this.cargo = cargo;
    }


    public int getNumerosCargo() {
        return numerosCargo;
    }


    public void setNumerosCargo(int numerosCargo) {
        this.numerosCargo = numerosCargo;
    }


    public JLabel getTituloEleicao() {
        return tituloEleicao;
    }


    public void setTituloEleicao(JLabel tituloEleicao) {
        this.tituloEleicao = tituloEleicao;
    }


    public JLabel[] getNumeroContainer() {
        return numeroContainer;
    }


    public void setNumeroContainer(JLabel[] numeroContainer) {
        this.numeroContainer = numeroContainer;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }


    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }


    public JPanel getPanelCandidato() {
        return panelCandidato;
    }


    public void setPanelCandidato(JPanel panelCandidato) {
        this.panelCandidato = panelCandidato;
    }


    public JPanel getPanelFim() {
        return panelFim;
    }


    public void setPanelFim(JPanel panelFim) {
        this.panelFim = panelFim;
    }


    public CardLayout getCardLayout() {
        return cardLayout;
    }


    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }


    public JLabel getJlImgCandidato() {
        return jlImgCandidato;
    }


    public void setJlImgCandidato(JLabel jlImgCandidato) {
        this.jlImgCandidato = jlImgCandidato;
    }


    public JLabel getJlNome() {
        return jlNome;
    }


    public void setJlNome(JLabel jlNome) {
        this.jlNome = jlNome;
    }


    public JPanel getJpInstructions() {
        return jpInstructions;
    }


    public void setJpInstructions(JPanel jpInstructions) {
        this.jpInstructions = jpInstructions;
    }


    public ImageIcon getiImgCandidato() {
        return iImgCandidato;
    }


    public void setiImgCandidato(ImageIcon iImgCandidato) {
        this.iImgCandidato = iImgCandidato;
    }


    public String getCaminhoImages() {
        return caminhoImages;
    }


    public void setCaminhoImages(String caminhoImages) {
        this.caminhoImages = caminhoImages;
    }
}
