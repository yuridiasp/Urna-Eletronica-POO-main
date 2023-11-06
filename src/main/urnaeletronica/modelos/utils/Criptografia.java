package main.urnaeletronica.modelos.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Criptografia {
    
    public static String generateHashString(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(text.getBytes());
        StringBuilder hexString = new StringBuilder();
    
        for (byte b : hashBytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
        }
    
        return hexString.toString();
    }

    public static String generateHashFile(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (DigestInputStream inputStream = new DigestInputStream(new FileInputStream(file), digest)) {
        // Lê o arquivo para calcular a hash
        byte[] buffer = new byte[8192];
        while (inputStream.read(buffer) != -1) {}
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
    
        for (byte b : hashBytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
        }
    
        return hexString.toString();
    }

    public static String getHashCandidatos() throws NoSuchAlgorithmException, IOException {
        return calcularHashArquivo("candidatos.txt");
    }

    public static String getHashEleitores() throws NoSuchAlgorithmException, IOException {
        return calcularHashArquivo("eleitores.txt");
    }

    public static String getHashVotos() throws NoSuchAlgorithmException, IOException {
        return calcularHashArquivo("votos.txt");
    }

    private static String calcularHashArquivo(String arquivoTxt) throws NoSuchAlgorithmException, IOException {
        String localDeTrabalho = System.getProperty("user.dir");
        String caminho = localDeTrabalho+"/main/"+ arquivoTxt;
        File file = new File(caminho);
        return generateHashFile(file);
    }

    public static boolean Validarhash(String hash, String linha) throws NoSuchAlgorithmException {
        String newHash = Criptografia.generateHashString(linha);

        if (hash.equals(newHash)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String hash = generateHashString("Candidato:Biscoito,Id:157");
        System.out.println(hash);
    }
}
