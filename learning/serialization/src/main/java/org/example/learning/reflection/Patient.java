package org.example.learning.reflection;

public class Patient {
    private String secret = "secret";
    private String nonSecret = "nonSecret";

    public Patient() {

    }

    public String getNonSecret() {
        return this.nonSecret;
    }

    public void setNonSecret(String nonSecret) {
        this.nonSecret = nonSecret;
    }

    private String getSecret() {
        return this.secret;
    }

    private void setSecret(String secret) {
        this.secret = secret;
    }
}
