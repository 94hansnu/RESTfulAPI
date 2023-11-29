package com.example.RESTfulAPI.Utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

// En hjälpklass för att generera RSA-nyckelpar
public class KeyGeneratorUtility {

    // Metod för att generera ett RSA-nyckelpar
    public static KeyPair generateRsaKey(){

        KeyPair keyPair;

        try{
            // Skapa en ny instans av KeyPairGenerator för RSA-algoritmen
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // Konfigurera generatorn med en nyckelstorlek på 2048 bitar
            keyPairGenerator.initialize(2048);
            // Generera ett RSA-nyckelpar
            keyPair = keyPairGenerator.generateKeyPair();
        } catch(Exception e){
            // Kasta IllegalStateException om något går fel vid nyckelgenereringen
            throw new IllegalStateException();
        }
        // Returnera det genererade nyckelparet
        return keyPair;
    }
}
