package main.urnaeletronica.visual.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonUrna extends JButton {
    protected String keyName;
    protected String nameIcon;
    protected String nameIconPressed;
    protected ImageIcon icon;
    protected ImageIcon iconPressed;

    public ButtonUrna(String nome, String caminho) {
        super();
        if (nome.length() == 2) {
            keyName = nome.substring(1, 2);
        } else {
            keyName = nome;
        }
        nameIcon = nome + ".jpg";
        nameIconPressed = nome + "_down.jpg";
        icon = new ImageIcon(caminho + nameIcon);
        iconPressed = new ImageIcon(caminho + nameIconPressed);
        setIcon(icon);
        setIcon(iconPressed);
        setBorder(null);
    }

    public String getKeyName() {
        return keyName;
    }

    public void setImageButton(boolean pressed) {
        if (pressed) {
            setIcon(iconPressed);
        } else {
            setIcon(icon);
        }
    }

    public String getNameIcon() {
        return nameIcon;
    }

    public String getNameIconPressed() {
        return nameIconPressed;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getIconPressed() {
        return iconPressed;
    }
}