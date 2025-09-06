package com.example.biometric;

public class User {
    private long id;
    private String name;
    private String country;
    private String city;
    private String registrationDate;
    private String fingerprintHashes;
    private String irisHash;

    public User() {}

    public User(String name, String country, String city, String registrationDate, 
               String fingerprintHashes, String irisHash) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.registrationDate = registrationDate;
        this.fingerprintHashes = fingerprintHashes;
        this.irisHash = irisHash;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFingerprintHashes() {
        return fingerprintHashes;
    }

    public void setFingerprintHashes(String fingerprintHashes) {
        this.fingerprintHashes = fingerprintHashes;
    }

    public String getIrisHash() {
        return irisHash;
    }

    public void setIrisHash(String irisHash) {
        this.irisHash = irisHash;
    }
}
