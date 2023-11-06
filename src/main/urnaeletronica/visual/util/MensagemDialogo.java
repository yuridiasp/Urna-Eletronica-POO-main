package main.urnaeletronica.visual.util;

import javax.swing.*;

public abstract class MensagemDialogo {
    private static boolean popupFechado = true;

    /**
     * Classe que exibe caixa de díalogo para exceções, mas verificando se já há uma janela sendo exibida
     * @param ex é a exception lançada e tratada em um bloco try-catch
     */
    public static boolean mostrarMensagemDialogo(String msg) {
        if (popupFechado) {
            popupFechado = false;
            int option = JOptionPane.showOptionDialog(null, msg, "Mensagem", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"OK"},null);
            
            popupFechado = true;
            
            if (option == JOptionPane.YES_OPTION) {
                // O botão "OK" foi pressionado
                return true;
            } else {
                // A janela foi fechada ou outro botão foi pressionado
                return false;
            }
        }
        return false;
    }
}
