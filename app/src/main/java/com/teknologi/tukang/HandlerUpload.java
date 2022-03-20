package com.teknologi.tukang;

public class HandlerUpload {
    public String nama,harga,desc,pcs,url;

    public HandlerUpload(String nama, String pcs, String desc, String harga, String url){
        this.nama = nama;
        this.pcs = pcs;
        this.desc = desc;
        this.harga = harga;
        this.url = url;
    }
    public String getNama(){
        return nama;
    }
    public String getPcs(){
        return pcs;
    }
    public String getDesc(){
        return desc;
    }
    public String getHarga(){
        return harga;
    }
    public String getUrl(){return url;}
}
