package main.urnaeletronica.visual.util;

import java.awt.Component;

public abstract class CalculadoraCoordenadas {
    
    /**
     * Calcula o tamanho e localização de um componente para centralizá-lo em relação ao componente pai
     * @param component é um componente Java Swing
     * @param container é um componente Java Swing que é elemento pai de component, ou seja, ele contem component
     * @return é um array de 4 inteiros, sendo composto pela coordenada x, coordenada y, larguta e altura, respectivamente
     */
    public static int [] calcularCoordenadasComponent(Component component, Component container) {
        // obtendo as dimensões do titulo
        int componentWidth = component.getPreferredSize().width;
        int componentHeight = component.getPreferredSize().height;
        int containerWidth = container.getWidth();
        int containerHeigth = container.getHeight();
        
        // calculando as coordenadas x e y do titulo para centralizá-lo
        int x = (containerWidth - componentWidth) / 2;
        int y = (containerHeigth - componentHeight) / 2;

        int [] coordenadas = { x , y, componentWidth, componentHeight };

        return coordenadas;
    }
}
