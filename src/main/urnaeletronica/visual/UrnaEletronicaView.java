package main.urnaeletronica.visual;

import javax.swing.*;

import main.urnaeletronica.visual.component.ButtonUrna;
import main.urnaeletronica.visual.component.VisorVotacaoView;
import main.urnaeletronica.visual.interfaces.iUrnaEletronicaView;
import main.urnaeletronica.visual.util.MensagemDialogo;

import java.nio.file.*;
import java.awt.*;
import java.awt.event.*;

public class UrnaEletronicaView extends WindowView implements iUrnaEletronicaView {
    /**
     * Array para armazenas botões da urna e o caminho das respectivas imagens
     */
    private ButtonUrna [] buttons = new ButtonUrna[13];
    
    /**
     * Objetos que armazenam os caminhos das imagens da urna
     */
    private ImageIcon itela, itopo, ifaixaDir, iladoEsqTec, iladoDirTec, iptaBaixo7, iptaBaixo9, iabaixoTec;

    /**
     * Label's para visualização das imagens da urna
     */
    private JLabel jLtela, jLtopo, jLfaixaDir, jLladoEsqTec, jLladoDirTec, jLptaBaixo7, jLptaBaixo9, jLabaixoTec;
    
    /**
     * Conteiner principal da aplicação
     */
    private JPanel panel;

    /**
     * Tela do visor do momento de votação no layout de urna
     */
    private VisorVotacaoView visorVotacao;

    /**
     * Armazenamento do caminho do projeto para construção de caminho relativo
     */
    private String caminhoImages;

    private boolean aptoConfirmarVoto = false;

    private String numeroDigitado = "", cargo;

    private int num;

    public UrnaEletronicaView () {
        super();
        ConstructUrnaEletronicaView(3, "Bolacha x Biscoito");
    }

    public UrnaEletronicaView (int num, String cargo) {
        super();
        ConstructUrnaEletronicaView(num, cargo);
    }

    public void ConstructUrnaEletronicaView (int num, String cargo) {
        
        /**
         * Verifica se há janela aberta
         */
        if (!janelaAberta) {
            this.num = num;
            this.cargo = cargo;
            /**
             * Definição das propriedades da tela e instanciação do panel principal
             */
            setTitle("Urna Eletrônica: Tela de Votação");
            setSize(500,500);
            setExtendedState(MAXIMIZED_BOTH);
            setCaminhoImages();
            
            /**
             * Instânciando container principal
             */
            panel = new JPanel(null);
            panel.setBackground(Color.WHITE);
            add(panel);
            
            /**
             * Instânciando container do visor da urna
             */
            visorVotacao = new VisorVotacaoView(this.num, this.cargo);
            visorVotacao.setBackground(Color.WHITE);
            panel.add(visorVotacao);

            
            /**
             * Instanciando os botões de 0 a 9
             */
            for (int c = 0; c <= 9; c++) {
                buttons[c] = new ButtonUrna("n" + c, caminhoImages);
            }

            /**
             * Instanciando os botões especiais: Branco, Corrige e Confirma
             */
            buttons[10] = new ButtonUrna("branco", caminhoImages);
            buttons[11] = new ButtonUrna("corrige", caminhoImages);
            buttons[12] = new ButtonUrna("confirma", caminhoImages);

            /**
             * Adicionando ouvintes de eventos para o mouse e teclado
             */
            adicionarListenes();

            /**
             * Instanciando objetos que armazenam o caminho das imagens da urna
             */
            itela = new ImageIcon(caminhoImages + "tela.jpg");
            itopo = new ImageIcon(caminhoImages + "topo.jpg");
            ifaixaDir = new ImageIcon(caminhoImages + "faixaDir.jpg");
            iladoEsqTec = new ImageIcon(caminhoImages + "ladoEsqTec.jpg");
            iladoDirTec = new ImageIcon(caminhoImages + "ladoDirTec.jpg");
            iptaBaixo7 = new ImageIcon(caminhoImages + "ptaBaixo7.jpg");
            iptaBaixo9 = new ImageIcon(caminhoImages + "ptaBaixo9.jpg");
            iabaixoTec = new ImageIcon(caminhoImages + "abaixoTec.jpg");
            
            /**
             * Instanciando os componentes JLabel que servirá como contêiner para as imagens da urna
             */
            jLtela = new JLabel(itela);
            jLtopo = new JLabel(itopo);
            jLfaixaDir = new JLabel(ifaixaDir);
            jLladoEsqTec = new JLabel(iladoEsqTec);
            jLladoDirTec = new JLabel(iladoDirTec);
            jLptaBaixo7 = new JLabel(iptaBaixo7);
            jLptaBaixo9 = new JLabel(iptaBaixo9);
            jLabaixoTec = new JLabel(iabaixoTec);

            /**
             * Adicionando os labels ao panel
             */
            panel.add(jLtela);
            panel.add(jLtopo);
            panel.add(jLfaixaDir);
            panel.add(jLladoEsqTec);
            panel.add(jLladoDirTec);
            panel.add(jLptaBaixo7);
            panel.add(jLptaBaixo9);
            panel.add(jLabaixoTec);

            /**
             * Contrução do layout da urna: Definindo as posições na tela para cada parte de imagem da urna e os botões
             */
            setPositionComponents();

            /**
             * Atualização da variável de controle indicando que há uma janela aberta
             */
            janelaAberta = true;
        }
    }

    public void setCaminhoImages() {
        /**
         * Definindo o caminho relativo da aplicação com base na localização do diretório
         */
        String caminhoUser = System.getProperty("user.dir");
        this.caminhoImages = Paths.get(caminhoUser,"main/resources/urna/images/").toString();
        System.out.println(caminhoImages);
    }

    public void adicionarListenes() {


        /**
         * Adicionando ouvintes de eventos para o botão esquerdo do mouse para trocar as imagens dos botões para sua versão pressionada
         */
        for (int c = 0; c < buttons.length; c++) {
            panel.add(buttons[c]);
            int index = c;
            buttons[c].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    try {
                        buttons[index].setImageButton(true);
                        String key = buttons[index].getKeyName();
                        if (key.length() <= 2) {
                            adicionarDigito(key);
                        }
                        else {
                            if (key == "branco") {
                                exibirVotoBranco();
                            } else if (key == "corrige") {
                                if (aptoConfirmarVoto) {
                                    reiniciarVotacao();
                                } else {
                                    removerDigito();
                                }
                            } else if (key == "confirma") {
                                confimarVoto();
                            }
                        }
                    }  catch (Exception ex) {
                        ex.printStackTrace();
                        MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    buttons[index].setImageButton(false);
                }
            });
            buttons[c].addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (buttons[index].contains(e.getPoint())) {
                        buttons[index].setImageButton(true);
                    } else {
                        buttons[index].setImageButton(false);
                    }
                }
            });
            setVisible(true);
        }
    }

    public void setPositionComponents() {
        /**
         * Contrução do layout da urna: Definindo as posições na tela para cada parte de imagem da urna e os botões
         */
        jLtela.setBounds(0, 0, itela.getIconWidth(), itela.getIconHeight());

        jLtopo.setBounds(itela.getIconWidth(), -3, itopo.getIconWidth(), itopo.getIconHeight());

        jLfaixaDir.setBounds(itela.getIconWidth() + itopo.getIconWidth(), 32,ifaixaDir.getIconWidth(),ifaixaDir.getIconHeight());

        jLladoEsqTec.setBounds(itela.getIconWidth(), itopo.getIconHeight()-3, iladoEsqTec.getIconWidth(), iladoEsqTec.getIconHeight());

        buttons[1].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth(), itopo.getIconHeight()-3, buttons[1].getIcon().getIconWidth(), buttons[1].getIcon().getIconHeight());

        buttons[2].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[1].getIcon().getIconWidth(), itopo.getIconHeight()-3, buttons[2].getIcon().getIconWidth(), buttons[2].getIcon().getIconHeight());

        buttons[3].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[1].getIcon().getIconWidth() + buttons[2].getIcon().getIconWidth(), itopo.getIconHeight()-3, buttons[3].getIcon().getIconWidth(), buttons[3].getIcon().getIconHeight());

        jLladoDirTec.setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[1].getIcon().getIconWidth() + buttons[2].getIcon().getIconWidth() + buttons[3].getIcon().getIconWidth(), itopo.getIconHeight()-3, iladoDirTec.getIconWidth(), iladoDirTec.getIconHeight());

        buttons[4].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight(), buttons[4].getIcon().getIconWidth(), buttons[4].getIcon().getIconHeight());

        buttons[5].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[4].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight(), buttons[5].getIcon().getIconWidth(), buttons[5].getIcon().getIconHeight());

        buttons[6].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[5].getIcon().getIconWidth() + buttons[4].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight(), buttons[6].getIcon().getIconWidth(), buttons[6].getIcon().getIconHeight());

        buttons[7].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight(), buttons[7].getIcon().getIconWidth(), buttons[7].getIcon().getIconHeight());

        buttons[8].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[4].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight(), buttons[8].getIcon().getIconWidth(), buttons[8].getIcon().getIconHeight());

        buttons[9].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + buttons[4].getIcon().getIconWidth() + buttons[5].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight(), buttons[9].getIcon().getIconWidth(), buttons[9].getIcon().getIconHeight());
        
        jLptaBaixo7.setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[9].getIcon().getIconHeight(), iptaBaixo7.getIconWidth(), iptaBaixo7.getIconHeight());
        
        buttons[0].setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + iptaBaixo7.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[9].getIcon().getIconHeight(), buttons[0].getIcon().getIconWidth(), buttons[0].getIcon().getIconHeight());
        
        jLptaBaixo9.setBounds(itela.getIconWidth() + iladoEsqTec.getIconWidth() + iptaBaixo7.getIconWidth() + buttons[0].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[9].getIcon().getIconHeight(), iptaBaixo9.getIconWidth(), iptaBaixo9.getIconHeight());

        buttons[10].setBounds(itela.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[7].getIcon().getIconHeight() + iptaBaixo7.getIconHeight(), buttons[10].getIcon().getIconWidth(), buttons[10].getIcon().getIconHeight());
    
        buttons[11].setBounds(itela.getIconWidth() + buttons[10].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[7].getIcon().getIconHeight() + iptaBaixo7.getIconHeight(), buttons[11].getIcon().getIconWidth(), buttons[11].getIcon().getIconHeight());
    
        buttons[12].setBounds(itela.getIconWidth() + buttons[10].getIcon().getIconWidth() + buttons[11].getIcon().getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[7].getIcon().getIconHeight() + iptaBaixo9.getIconHeight(), buttons[12].getIcon().getIconWidth(), buttons[12].getIcon().getIconHeight());

        jLabaixoTec.setBounds(itela.getIconWidth(), itopo.getIconHeight()-3 + buttons[1].getIcon().getIconHeight() + buttons[4].getIcon().getIconHeight() + buttons[7].getIcon().getIconHeight() + iptaBaixo7.getIconHeight() + buttons[10].getIcon().getIconHeight(), iabaixoTec.getIconWidth(), iabaixoTec.getIconHeight());
    }

    public void reiniciarVotacao() {
        aptoConfirmarVoto = false;
        numeroDigitado = "";
        visorVotacao.redefinirNumeroDigitado();
        visorVotacao.esconderDadosVoto();
    }

    public void adicionarDigito(String c) {
        if (numeroDigitado.length() < num) {
            StringBuilder sb = new StringBuilder();
            sb.append(numeroDigitado);
            sb.append(c);
            numeroDigitado = sb.toString();
            sb.capacity();
            int restante = num - sb.length();
            if (restante > 0) {
                int position = sb.length() - 1;
                for (int cont = position; cont < num; cont++) {
                    sb.append(" ");
                }
            }
            visorVotacao.atualizarTelaVotacao(sb.toString().toCharArray());
            if (numeroDigitado.length() == num) {
                validarVoto();
            }
        } else {
            try {
                throw new UnsupportedOperationException("Não é possível inserir mais números. Confirme o seu voto pressionando CONFIRMAR ou aperte CORRIGIR para reiniciar votação.");
            } catch (Exception ex) {
                ex.printStackTrace();
                MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
            }
        }
    }

    public void removerDigito() {
        if (numeroDigitado.length() > 1) {
            numeroDigitado = numeroDigitado.substring(0, (numeroDigitado.length() - 1));
        } else if (numeroDigitado.length() == 1) {
            numeroDigitado = "";
        } else {
            try {
                throw new UnsupportedOperationException();
            } catch (Exception ex) {
                ex.printStackTrace();
                MensagemDialogo.mostrarMensagemDialogo("Não há números digitados para apagar. Digite o número do seu candidato.");
            }
        }
        visorVotacao.atualizarTelaVotacao(numeroDigitado.toCharArray());
    }

    @Override
    public void confimarVoto() {
        if (aptoConfirmarVoto) {
            visorVotacao.encerrarVotacao();
            MensagemDialogo.mostrarMensagemDialogo("Votação encerrada! Muito Obrigado por votar!");
            try {
                controller.registrarVoto(numeroDigitado);
            } catch (Exception e) {
                e.printStackTrace();
                MensagemDialogo.mostrarMensagemDialogo(e.getMessage() + "Desculpe pelo erro. Tente mais tarde.");
            }
            numeroDigitado = "";
            dispose();
        } else {
            try {
                throw new UnsupportedOperationException("Digite todos os números necessários para poder confirmar o voto.");
            } catch (Exception ex) {
                ex.printStackTrace();
                MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
            }
        }
    }

    @Override
    public void exibirVotoBranco() {
        numeroDigitado = "###";
        visorVotacao.redefinirNumeroDigitado();
        visorVotacao.getJlNome().setText("Voto Branco");
        visorVotacao.mostrarDadosVoto();
        visorVotacao.getJlImgCandidato().setVisible(false);
        aptoConfirmarVoto = true;
    }

    @Override
    public void exibirVotoNulo() {
        visorVotacao.getJlNome().setText("Voto Nulo");
        visorVotacao.mostrarDadosVoto();
        visorVotacao.getJlImgCandidato().setVisible(false);
        aptoConfirmarVoto = true;
    }
    
    @Override
    public void exibirVotoValido() {
        String nome = controller.consultarNumeroCandidato(numeroDigitado);
        if ("Nulo".equals(nome)) {
            exibirVotoNulo();
        } else {
            visorVotacao.getJlNome().setText(nome);
            visorVotacao.mostrarDadosVoto();
            visorVotacao.alterarFotoCandidato(nome);
            visorVotacao.getJlImgCandidato().setVisible(true);
            aptoConfirmarVoto = true;
        }
    }

    public void validarVoto() {
        exibirVotoValido();
    }
}