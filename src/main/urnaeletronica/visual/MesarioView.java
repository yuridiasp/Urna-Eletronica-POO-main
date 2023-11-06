package main.urnaeletronica.visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import main.urnaeletronica.modelos.Eleitor;
import main.urnaeletronica.visual.util.MensagemDialogo;

public class MesarioView extends WindowView {
    private CardLayout cardLayout;
    private JPanel menu, panelBackground, panelPrincipal, panelCadastrarEleitor, panelAutorizarEleitor, panelReiniciarEleicao;
    private JButton btnAutorizarEleitor,btnCadEleitor, btnResetEleicao, btnFecharPrincipal, btnEnviarEleitor, btnReinicarEleicao, ultimoBotaoClicadoAutorizarEleitor;
    private JTextField nomeEleitor;
    private JLabel tituloPrincipal, tituloCadastroEleitor, tituloAutorizarEleitor, tituloReiniciarEleicao;
    private String ultimoIdAutorizado;

    public MesarioView () throws ParseException {
        if (!janelaAberta) {
            setTitle("Urna Eletrônica: Módulo Mesário");
            setSize(500,500);
            setExtendedState(MAXIMIZED_BOTH);
            setLayout(new GridLayout(1,2));
            
            cardLayout = new CardLayout();
            panelBackground = new JPanel();
            panelBackground.setLayout(cardLayout);
            panelBackground.setSize(this.getWidth()/2, this.getHeight());
            menu = new JPanel();
            add(menu);
            add(panelBackground);
            
            panelPrincipal = new JPanel();    
            tituloPrincipal = new JLabel("Menu Mesário");
            setTamanhoFontText(tituloPrincipal, 30f);
            
            panelPrincipal.setLayout(new BorderLayout());
            tituloPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
            panelPrincipal.add(tituloPrincipal, BorderLayout.CENTER);
            
            panelAutorizarEleitor = new JPanel();
            panelAutorizarEleitor.setSize(panelBackground.getWidth(), panelBackground.getHeight());
            panelAutorizarEleitor.setLayout(new BorderLayout());

            Map<String, Eleitor> eleitores = controller.getEleicao().getEleitores();
            
            JPanel listaPanels = new JPanel();
            listaPanels.setLayout(new GridLayout(eleitores.size(),2));
            
            Boolean style = true;

            for (Map.Entry<String,Eleitor> eleitor : eleitores.entrySet()) {
                gerarListaEleitores(listaPanels, eleitor.getValue().getId(), eleitor.getValue().getNome(), eleitor.getValue().getDataNascimento(), style, eleitor.getValue().getAutorizado(), eleitor.getValue().getVotou());
                style = !style;
            }
            
            JScrollPane containerScroll = new JScrollPane(listaPanels);
            containerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            containerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 50, 50, 50));
            tituloAutorizarEleitor = new JLabel("Autorizar Eleitor");
            setTamanhoFontText(tituloAutorizarEleitor, 10f);
            panelTitulo.add(tituloAutorizarEleitor);
            tituloAutorizarEleitor.setHorizontalAlignment(SwingConstants.CENTER);
            panelAutorizarEleitor.add(panelTitulo, BorderLayout.NORTH);
            panelAutorizarEleitor.add(containerScroll, BorderLayout.CENTER);

            panelCadastrarEleitor = new JPanel();
            panelCadastrarEleitor.setLayout(new GridLayout(4,1));
            panelCadastrarEleitor.setSize(panelBackground.getWidth(), panelBackground.getHeight());

            GridBagLayout gridBagLayout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(0, 0, 10, 0);
            
            tituloCadastroEleitor = new JLabel("Cadastrar Eleitor");
            setTamanhoFontText(tituloCadastroEleitor, 10f);
            tituloCadastroEleitor.setHorizontalAlignment(SwingConstants.CENTER);
            panelCadastrarEleitor.add(tituloCadastroEleitor);

            JPanel containerInputs = new JPanel();
            containerInputs.setLayout(gridBagLayout);
            containerInputs.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 50, 0, 50));

            JLabel nome = new JLabel("Nome:");
            nome.setHorizontalAlignment(SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.weightx = 0.5;
            containerInputs.add(nome, constraints);

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            nomeEleitor = new JTextField();
            nomeEleitor.setPreferredSize(new Dimension(200, 20));;
            nomeEleitor.setHorizontalAlignment(SwingConstants.CENTER);
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 2;
            constraints.weightx = 1.0;
            containerInputs.add(nomeEleitor, constraints);

            JLabel nascimento = new JLabel("Data de Nascimento:");
            nascimento.setHorizontalAlignment(SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.weightx = 0.5;
            containerInputs.add(nascimento, constraints);

            JTextField nascimentoEleitor = new JFormattedTextField(createFormatter("dd/MM/yyyy"));
            nascimentoEleitor.setColumns(10);
            nascimentoEleitor.setHorizontalAlignment(SwingConstants.CENTER);
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 2;
            constraints.weightx = 1.0;
            containerInputs.add(nascimentoEleitor, constraints);

            panelCadastrarEleitor.add(containerInputs);
            
            String dataInput = nascimentoEleitor.getText();

            JPanel containerBtnEnviarEleitor = new JPanel();
            containerBtnEnviarEleitor.setLayout(new GridLayout(1,1));
            containerBtnEnviarEleitor.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 50, 50, 50));
            btnEnviarEleitor = new JButton("Cadastrar");
            btnEnviarEleitor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (nomeEleitor.getText().length() == 0) {
                            throw new IllegalArgumentException("Escreva um nome para cadastrar eleitor.");
                        }
                    } catch (Exception ex) {
                        MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
                    }
                    String input = nascimentoEleitor.getText();
                    try {
                        if (!isValidDate(input)) {
                            throw new IllegalArgumentException("Formato de data inválido. Digite a data no formato DD/MM/YYYY.");
                        }
                        enviarCadastroEleitor(nomeEleitor.getText(), nascimentoEleitor.getText());
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                        MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
                    }
                }
            });
            containerBtnEnviarEleitor.add(btnEnviarEleitor);
            panelCadastrarEleitor.add(containerBtnEnviarEleitor);
            
            panelReiniciarEleicao = new JPanel();
            panelReiniciarEleicao.setLayout(new GridLayout(2,1));
            JPanel containerTituloReiniciarEleicao = new JPanel();
            containerTituloReiniciarEleicao.setLayout(new BorderLayout());
            JPanel containerBtnReiniciarEleicao = new JPanel();
            containerBtnReiniciarEleicao.setLayout(new BorderLayout());
            containerBtnReiniciarEleicao.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 50, 150, 50));
            tituloReiniciarEleicao = new JLabel("Deseja realmente zerar os votos da eleição?");
            setTamanhoFontText(tituloReiniciarEleicao, 10f);
            tituloReiniciarEleicao.setHorizontalAlignment(SwingConstants.CENTER);
            btnReinicarEleicao = new JButton("Confimar");
            btnReinicarEleicao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enviarResetEleicao();
                }
            });
            containerTituloReiniciarEleicao.add(tituloReiniciarEleicao, BorderLayout.CENTER);
            containerBtnReiniciarEleicao.add(btnReinicarEleicao, BorderLayout.CENTER);
            panelReiniciarEleicao.add(containerTituloReiniciarEleicao);
            panelReiniciarEleicao.add(containerBtnReiniciarEleicao);
            
            
            panelBackground.add(panelPrincipal, "panelPrincipal");
            panelBackground.add(panelCadastrarEleitor, "panelCadastrarEleitor");
            panelBackground.add(panelAutorizarEleitor, "panelAutorizarEleitor");
            panelBackground.add(panelReiniciarEleicao, "panelReiniciarEleicao");
            
            
            mostrarPrincipal();

            btnAutorizarEleitor = new JButton("Autorizar Eleitor");
            btnAutorizarEleitor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarAutorizarEleitor();
                }
            });
            
            btnCadEleitor = new JButton("Cadastrar Eleitor");
            btnCadEleitor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarCadastroEleito();
                }
            });
            
            btnResetEleicao = new JButton("Reiniciar Eleição");
            btnResetEleicao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarReiniciarEleicao();
                }
            });

            btnFecharPrincipal = new JButton("Fechar Janela");
            btnFecharPrincipal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            menu.add(btnAutorizarEleitor);
            menu.add(btnCadEleitor);
            menu.add(btnResetEleicao);
            menu.add(btnFecharPrincipal);
            menu.setLayout(new GridLayout(4, 1));

            janelaAberta = true;
            setVisible(true);
        }

    }

    private SimpleDateFormat createFormatter(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);
        return formatter;
    }

    private boolean isValidDate(String input) {
        try {
            SimpleDateFormat formatter = createFormatter("dd/MM/yyyy");
            Date date = formatter.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    protected void enviarResetEleicao() {
        Boolean retorno = controller.reiniciarEleicao();
        if (retorno) {
            MensagemDialogo.mostrarMensagemDialogo("Votos apagados!");
        } else {
            MensagemDialogo.mostrarMensagemDialogo("Erro interno: Não foi possível apagar os votos.");
        }
    }

    public void mostrarPrincipal() {
        cardLayout.show(panelBackground,"panelPrincipal");
    }
    
    public void mostrarAutorizarEleitor() {
        cardLayout.show(panelBackground,"panelAutorizarEleitor");
    }

    public void mostrarCadastroEleito() {
        cardLayout.show(panelBackground,"panelCadastrarEleitor");
    }

    public void mostrarReiniciarEleicao() {
        cardLayout.show(panelBackground, "panelReiniciarEleicao");
    }

    public void gerarListaEleitores(JPanel listaPanels, String id, String nome, String nascimento, Boolean style, Boolean autorizado, Boolean votou) {
        //JLabel idEleitor = new JLabel(id);
        //idEleitor.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel nomeEleitor = new JLabel(nome);
        nomeEleitor.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel nascimentoEleitor = new JLabel(nascimento);
        nascimentoEleitor.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panelEleitor = new JPanel();
        panelEleitor.setLayout(new GridLayout(1,3));
        //panelEleitor.add(idEleitor);
        panelEleitor.add(nomeEleitor);
        panelEleitor.add(nascimentoEleitor);
        JButton btn = new JButton();

        if (!autorizado && !votou) {
            btn = new JButton("Autorizar");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    changeAutorization(btn, id, nome);
                }
            });
            panelEleitor.add(btn);
        } else if (autorizado && !votou) {
            btn = new JButton("Desautorizar");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    changeAutorization(btn, id, nome);
                }
            });
            panelEleitor.add(btn);
        } else {
            JLabel jaVotou = new JLabel("Votou");
            jaVotou.setHorizontalAlignment(SwingConstants.CENTER);
            panelEleitor.add(jaVotou);
        }


        listaPanels.add(panelEleitor);
        if (style) {
            panelEleitor.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void changeAutorization (JButton btn, String id, String nome) {
        if (ultimoBotaoClicadoAutorizarEleitor != null && ultimoBotaoClicadoAutorizarEleitor != btn) {
            ultimoBotaoClicadoAutorizarEleitor.setText("Autorizar");
            controller.desautorizarEleitor(ultimoIdAutorizado);
        }

        if (ultimoIdAutorizado != id) {
            if (controller.autorizarEleitor(id)) {
                btn.setText("Desautorizar");
                ultimoBotaoClicadoAutorizarEleitor = btn;
                ultimoIdAutorizado = id;
                MensagemDialogo.mostrarMensagemDialogo(nome + " foi autorizado a votar.");
            }
        } else {
            if (controller.desautorizarEleitor(id)) {
                btn.setText("Autorizar");
                ultimoBotaoClicadoAutorizarEleitor = null;
                ultimoIdAutorizado = "";
                MensagemDialogo.mostrarMensagemDialogo(nome + " foi desautorizado.");
            }
        }
    }

    private void enviarCadastroEleitor(String nome, String nascimento) {
        try {
            controller.inserirEleitor(nome, nascimento);
            MensagemDialogo.mostrarMensagemDialogo("Cadastrado novo eleitor.");
        } catch (Exception e) {
            e.printStackTrace();
            MensagemDialogo.mostrarMensagemDialogo(e.getMessage());
        }
    }

    private void setTamanhoFontText(Component componente, Float tamanho) {
        Font font = componente.getFont();
        Font fontGrande = font.deriveFont(font.getSize() + tamanho);
        componente.setFont(fontGrande);
    }
}
