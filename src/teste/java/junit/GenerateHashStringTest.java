package teste.java.junit;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import main.urnaeletronica.modelos.utils.Criptografia;

public class GenerateHashStringTest {
    private final String texto = "Teste";
    private String textoHash = "89f308210c7c7820bad0974f31e751bfa433d2066a93e808947c3188dedba6e3";

    @Test
    public void generateHashString() throws NoSuchAlgorithmException {
        assertEquals(textoHash, Criptografia.generateHashString(texto));
    }
}
