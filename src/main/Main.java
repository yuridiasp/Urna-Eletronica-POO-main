package main;

import main.urnaeletronica.controle.*;
import main.urnaeletronica.visual.util.MensagemDialogo;
/**Para compilar é necessário estar na pasta src e executar:
javac main.Main.java
Para executar, também é necessário estar na pasta src e usar o mesmo formato:
java main.Main
Pode ser main/Main.java ou main.Main que funciona igualmente
O trecho abaixo imprime separadamente cada um dos elementos
Serve apenas para entender a sintaxe dos candidatos
**/

public final class Main implements iMain {
    public static void main(String[] args) {
        try {
            UrnaController controller = new UrnaController();
        } catch (Exception ex) {
            ex.printStackTrace();
            MensagemDialogo.mostrarMensagemDialogo(ex.getMessage());
        }
    }
}