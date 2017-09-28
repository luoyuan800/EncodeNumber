package cn.luo.yuan.test;

import cn.luo.yuan.encode.number.EncodeLong;
import org.testng.annotations.Test;

/**
 * Copyright @Luo
 * Created by Gavin Luo on 9/28/2017.
 */
public class TestEncodeLong {
    @Test
    public void testReadSpeed(){
        EncodeLong encodeLong = new EncodeLong(Long.MAX_VALUE /2);
        readTest(encodeLong, 1000);
        readTest(encodeLong, 10000);
        readTest(encodeLong, 100000);
        readTest(encodeLong, 1000000);
        readTest(encodeLong, 10000000);
    }
    @Test
    public void testWriteSpeed(){
        EncodeLong encodeLong = new EncodeLong(Long.MAX_VALUE /2);
        writeTest(encodeLong, 1000);
        writeTest(encodeLong, 10000);
        writeTest(encodeLong, 100000);
        writeTest(encodeLong, 1000000);
        writeTest(encodeLong, 10000000);
    }

    private void readTest(EncodeLong encodeLong, long count) {
        long start = System.currentTimeMillis();
        for(int i = 0; i< count; i++){
            encodeLong.getValue();
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " read: " + (end - start)/1000f);
    }

    private void writeTest(EncodeLong encodeLong, long count) {
        long start = System.currentTimeMillis();
        for(long i = count; i< count + count; i++){
            encodeLong.setValue(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " write: " + (end - start)/1000f);
    }

}
