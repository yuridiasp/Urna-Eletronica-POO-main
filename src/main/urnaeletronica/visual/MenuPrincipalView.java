package main.urnaeletronica.visual;

import javax.swing.*;
import main.urnaeletronica.controle.*;
import main.urnaeletronica.visual.util.MensagemDialogo;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe responsável por gerar a tela do menu principal da aplicação.
 */
public class MenuPrincipalView extends WindowView {
    private JPanel panel;
    private JButton votarBtn, mesarioBtn, resultadosBtn, sairBtn;
    private Background backgroundPanel;
    private String msg;
    
    private class Background extends JPanel {
        private Image background;
        public Background() {
            String caminhoUser = System.getProperty("user.dir");
            //String caminhoImages = caminhoUser + "\\main\\resources\\menu\\images\\";
            Path caminhoImages = Paths.get(caminhoUser,"main","resources","menu","images");
            Path backgroundjpg = caminhoImages.resolve("background.jpg");
            String caminhoImagem = backgroundjpg.toString();
            System.out.println(caminhoImages);
            System.out.println(backgroundjpg);
            System.out.println(caminhoImagem);
            background = new ImageIcon(caminhoImagem).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public MenuPrincipalView(UrnaController controller, String msg) {
        super();
        this.msg = msg;
        ConstructMenuPrincipalView(controller);
        if (!controller.getPermissao()) {
            MensagemDialogo.mostrarMensagemDialogo(msg);
            MensagemDialogo.mostrarMensagemDialogo("Necessário realizar as devidas correções para poder prosseguir com a votação.");
        }
    }

    public MenuPrincipalView(UrnaController controller) {
        super();
        ConstructMenuPrincipalView(controller);
    }

    public void ConstructMenuPrincipalView(UrnaController controller) {
        
        if (!janelaAberta) {
            WindowView.controller = controller;
            
            /**
             * Definição de propriedade da janela: título, tamanho, dimensões, layout, localização, plano de fundo
             */
            setTitle("Urna Eletrônica: Menu Principal");
            setSize(800,500);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            backgroundPanel = new Background();
            getContentPane().add(backgroundPanel);
            
            
            /**
             * Adiciona o painel principal com layout de grade, sendo uma coluna e quatro linhas
             */
            panel = new JPanel();
            panel.setOpaque(false);
            add(panel, BorderLayout.SOUTH);
            
            /**
             * Adiciona listeners para os botões do menu principal
             */
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        controller.atualizarPermissao();
                    } catch (Exception ex) {
                        msg = ex.getMessage();
                    }
                    /**
                     * Verifica qual o botão pressionado para chamar o método correspondente
                     */
                    if (e.getSource() == resultadosBtn && controller.getPermissao()) {
                        try {
                            controller.listarResultados();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
                        }
                    } else if (e.getSource() == mesarioBtn && controller.getPermissao()) {
                        try {
                            controller.abrirModuloMesario();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
                        }
                    } else if (e.getSource() == votarBtn && controller.getPermissao()) {
                        if (controller.validarEleitor()) {
                            controller.iniciarVotacao();
                        } else {
                            MensagemDialogo.mostrarMensagemDialogo("Sr. mesário, autorize o eleitor a votar.");
                        }
                    } else if (e.getSource() == sairBtn) {
                        System.exit(0);
                    } else {
                        MensagemDialogo.mostrarMensagemDialogo(msg);
                    }
                }
            };
            
            /**
             * Instanciando e definindo as propriedades de tamanho, posição e texto dos botões do menu principal, bem como adicionando os listeners
             */
            votarBtn = new JButton("Votar");
            votarBtn.setBounds(0, 0, 100, 30);
            votarBtn.addActionListener(listener);
            
            mesarioBtn = new JButton("Módulo Mesário");
            mesarioBtn.setBounds(0, 0, 100, 30);
            mesarioBtn.addActionListener(listener);
            
            resultadosBtn = new JButton("Resultado das Eleições");
            resultadosBtn.setBounds(0, 30, 100, 30);
            resultadosBtn.addActionListener(listener);
            
            sairBtn = new JButton("Sair");
            sairBtn.setBounds(0, 30, 100, 30);
            sairBtn.addActionListener(listener);
            
            /**
             * Adição dos botões ao painel principal e definindo a visisibilidade da janela
             */
            panel.add(votarBtn);
            panel.add(mesarioBtn);
            panel.add(resultadosBtn);
            panel.add(sairBtn);
            setVisible(true);
        }
    }
}