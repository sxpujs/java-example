package com.demo.hash;

import java.util.zip.CRC32;

public class CRCMaker {

    public static void main(String[] args) {
        String toBeEncoded = "foobar";
        CRC32 myCRC = new CRC32();
        myCRC.update(toBeEncoded.getBytes());
        System.out.println("foobar CRC32 hex value = " + Long.toHexString(myCRC.getValue()));
        System.out.println("foobar CRC32 dec value = " + Long.valueOf(myCRC.getValue()));
    }
}