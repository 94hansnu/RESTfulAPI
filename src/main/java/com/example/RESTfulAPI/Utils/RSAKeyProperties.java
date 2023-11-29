package com.example.RESTfulAPI.Utils;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// En komponentklass som hanterar RSA-nycklar och tillhandahåller tillgång till dem
@Component
public class RSAKeyProperties {

    // RSA-nyckel för offentlig användning
    private RSAPublicKey publicKey;

    // RSA-nyckel för privat användning
    private RSAPrivateKey privateKey;

    //konstruktor
    public RSAKeyProperties(){
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    // getter och setter
    public RSAPublicKey getPublicKey(){
        return this.publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey){
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey(){
        return this.privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey){
        this.privateKey = privateKey;
    }
}
