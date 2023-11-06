package main.urnaeletronica.modelos.utils;

import java.util.UUID;

public abstract class GeradorId {
    
    public static String getId() {
        // Gerar um novo UUID
        UUID uuid = UUID.randomUUID();
        
        // Obter o ID como uma String
        String id = uuid.toString();
        
        return id;
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}
