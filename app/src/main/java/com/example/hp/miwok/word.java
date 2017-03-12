package com.example.hp.miwok;

/**
 * Created by Hp on 28-02-2017.
 */

public class word {
    private String meng ;
    private String mmiwok;
    private int mimageid = No_image;
    private int maudioid ;
    private static final int No_image = -1;

    public word(String a, String b,int aid) {
        meng = a;
        mmiwok = b;
        maudioid = aid;
    }
    public word(String a,String b,int id,int aid){
        meng = a;
        mmiwok = b;
        mimageid = id;
        maudioid = aid;
    }
    public int getMimageid(){
        return mimageid;
    }
    public String getMmeng(){
        return meng;
    }

    public String getMmiwok(){
        return mmiwok;
    }
    public int hasimage(){
        return mimageid;
    }
    public int getMaudioid(){
        return maudioid;
    }


}
