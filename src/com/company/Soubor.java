package com.company;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Soubor {


    public void stahni (String zdroj, String cil) {

        try {

        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(zdroj).openStream());
        FileOutputStream fileOS = new FileOutputStream(cil);
        FileChannel fileChannel = fileOS.getChannel();
        fileChannel.transferFrom(readableByteChannel,0,Long.MAX_VALUE);
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nenalezen: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static final int BUFFER_SIZE = 4096;

    public void rozbal(String zdroj, String cil) {

        File destDir = new File(cil);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zdroj));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = cil + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

}
