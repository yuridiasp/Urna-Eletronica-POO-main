package main.urnaeletronica.visual;

import javax.swing.JFrame;

import main.urnaeletronica.controle.UrnaController;

import java.awt.event.*;

public abstract class WindowView extends JFrame {
    protected static UrnaController controller;
    /**
     * Variável de controle para impedir duas ou mais janelas simultâneas
     */
    protected static Boolean janelaAberta = false;

    public WindowView() {
        super();
        adicionarListenerJanelaFechando();
    }

    /**
     * Sobrescrita do método dispose para acrescentear um reset a variável de controle
     */
    @Override
    public void dispose() {
        super.dispose();
        janelaAberta = false;
    }

    /**
     * Reseta a variável de controle quando a janela é fechada
     */
    public void adicionarListenerJanelaFechando() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                janelaAberta = false;
            }
        });
    }
}
