package com.company;


import java.io.IOException;

public class Main {

    public static void main(String[] args) {


    Soubor soubor = new Soubor();

    //URL with file,Path to save
    soubor.stahni("https://vdp.cuzk.cz/vymenny_format/soucasna/20200930_OB_573060_UZSZ.xml.zip","20200930_OB_573060_UZSZ.xml.zip");

    //File u want to extract,Path to save extracted file
    soubor.rozbal("20200930_OB_573060_UZSZ.xml.zip",System.getProperty("user.dir"));

    //SAX reading
    KolekceDat kolekceDat = new KolekceDat("20200930_OB_573060_UZSZ.xml");

    //creating SQLite database and save collection
    Database database = new Database("20200930_OB_573060_UZSZ.db");
    database.vytvor();
    database.vlozKolekci(kolekceDat);



    }
}
