package main.urnaeletronica.visual;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import main.urnaeletronica.modelos.Resultado;

public class ResultadosView extends WindowView {
    ArrayList<JLabel> jlResultados;
    JLabel jlTituloResultado;
    ArrayList<Resultado> resultados;
    JPanel lista;
    JButton sair;

    public ResultadosView() {
        super();
        constructResultadosView("Bolacha x Biscoito");
    }
    
    public ResultadosView(String titulo) {
        super();
        constructResultadosView("Bolacha x Biscoito");
    }

    public void constructResultadosView(String titulo) {
        
        if (!janelaAberta) {
            setTitle("Urna Eletrônica: Resultados");
            setSize(500,500);
            setExtendedState(MAXIMIZED_BOTH);
            try {
                resultados = controller.carregarResultados();
            } catch (Exception ex) {
                throw ex;
            }
            jlTituloResultado = new JLabel(titulo);
            jlTituloResultado.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = jlTituloResultado.getFont();
            Font fontGrande = font.deriveFont(font.getSize() + 10f);
            jlTituloResultado.setFont(fontGrande);
            add(jlTituloResultado);
            setLayout(new GridLayout(resultados.size()+1,1));
            generatePanelResultados(resultados);

            lista = new JPanel();
            add(lista);
            lista.setLayout(new GridLayout(resultados.size()*2,1));
            lista.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 50, 0, 50));

            for (JLabel jLabel : jlResultados) {
                lista.add(jLabel);
                lista.add(new JPanel());
            }

            sair = new JButton("Fechar");
            sair.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            JPanel jpSair = new JPanel();
            jpSair.add(sair);
            jpSair.setLayout(new GridLayout(1,1));
            jpSair.setBorder(javax.swing.BorderFactory.createEmptyBorder(100, 50, 100, 50));
            add(jpSair);

            janelaAberta = true;
            setVisible(true);
        }
    }
    
    public void generatePanelResultados(ArrayList<Resultado> resultados) {
        jlResultados = new ArrayList<JLabel>();
        for (Resultado resultado : resultados) {
            JLabel jl = new JLabel(resultado.getCandidato()+": "+resultado.getVotosContagem() + " voto(s)");
            jl.setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
            jl.setBorder(border);
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jlResultados.add(jl);
        }
    }
}
