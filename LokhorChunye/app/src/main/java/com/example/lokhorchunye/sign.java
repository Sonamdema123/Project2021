package com.example.lokhorchunye;

public class sign {
    private String sign;
    private String laza;
    private String sogza;
    private String sheza;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLaza() {
        return laza;
    }

    public void setLaza(String laza) {
        this.laza = laza;
    }

    public String getSogza() {
        return sogza;
    }

    public void setSogza(String sogza) {
        this.sogza = sogza;
    }

    public String getSheza() {
        return sheza;
    }

    public void setSheza(String sheza) {
        this.sheza = sheza;
    }

    public sign(String sign, String laza, String sogza, String sheza) {
       this.sign = sign;
       this.laza = laza;
       this.sogza = sogza;
       this.sheza = sheza;
    }


}
