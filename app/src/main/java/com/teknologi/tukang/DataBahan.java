package com.teknologi.tukang;

public class DataBahan {
    public String nama,harga,desc,pcs,namafile;

    public DataBahan(String nama,String pcs, String desc, String harga, String namafile){
        this.nama = nama;
        this.pcs = pcs;
        this.desc = desc;
        this.harga = harga;
        this.namafile = namafile;
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
    public String getNamafile(){return namafile;}
}
