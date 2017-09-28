package cn.luo.yuan.test;

import cn.luo.yuan.encode.number.EncodeFloat;
import cn.luo.yuan.encode.number.EncodeLong;
import org.testng.annotations.Test;

/**
 * Copyright @Luo
 * Created by Gavin Luo on 9/28/2017.
 */
public class TestEncodeFloat {
    @Test
    public void testReadSpeed(){
        EncodeFloat encodeFloat = new EncodeFloat(Float.MAX_VALUE /2);
        readTest(encodeFloat, 1000);
        readTest(encodeFloat, 10000);
        readTest(encodeFloat, 100000);
        readTest(encodeFloat, 1000000);
        readTest(encodeFloat, 10000000);
    }
    @Test
    public void testWriteSpeed(){
        EncodeFloat encodeFloat = new EncodeFloat(Float.MAX_VALUE /2);
        writeTest(encodeFloat, 1000);
        writeTest(encodeFloat, 10000);
        writeTest(encodeFloat, 100000);
        writeTest(encodeFloat, 1000000);
        writeTest(encodeFloat, 10000000);
    }

    private void readTest(EncodeFloat encodeFloat, long count) {
        long start = System.currentTimeMillis();
        for(int i = 0; i< count; i++){
            encodeFloat.getValue();
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " read: " + (end - start)/1000f);
    }

    private void writeTest(EncodeFloat encodeFloat, long count) {
        long start = System.currentTimeMillis();
        for(long i = count; i< count + count; i++){
            encodeFloat.setValue(i*0.1f);
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " write: " + (end - start)/1000f);
    }

}
