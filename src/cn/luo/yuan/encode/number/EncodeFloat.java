/*
  Copyright (c) 2017, luoyuan800
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 * Neither the name of the copyright holder nor the names of its
 contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package cn.luo.yuan.encode.number;

import cn.luo.yuan.utils.Random;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by gluo on 4/24/2017.
 * Use for float number.
 */
public class EncodeFloat implements EncodeNumber, Serializable {
    private final static long serialVersionUID = 1L;
    private ValueStringFormatter formatter;
    private int[] value;
    private byte[] key;
    private Random random;
    private boolean negative;

    /**
     * Create instance of EncodeFloat with a giving value and default toString formatter.
     * The default toString formatter will show the number directly while calling #toString()#
     * @param def Default value
     */
    public EncodeFloat(float def) {
        this.random = new Random(System.currentTimeMillis());
        setValue(def);
    }

    /**
     * Create instance of EncodeFloat with a value and a toString formatter
     * @param value Default value
     * @param formatter To String formatter
     */
    public EncodeFloat(float value, ValueStringFormatter formatter){
        this(value);
        this.formatter = formatter;
    }

    public String toString(){
        return formatter!=null ? formatter.formatNumber(getValue()) : getValue().toString();
    }

    /**
     * Get the float value
     * @return float value
     */
    public Float getValue() {
        int[] value;
        byte[] key;
        boolean negative;
        synchronized (this){
            value = Arrays.copyOf(this.value, this.value.length);
            key = Arrays.copyOf(this.key, this.key.length);
            negative = this.negative;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            builder.append((char)(value[i] - key[i]));
        }
        float number = Float.parseFloat(builder.toString());
        return negative ? -number : number;
    }

    /**
     * Set the value
     * @param value float value
     */
    public synchronized void setValue(Number value) {
        float fv = value.floatValue();
        if (fv < 0) {
            negative = true;
            fv = -fv;
        }else{
            negative = false;
        }
        char[] chars = Float.toHexString(fv).toCharArray();
        key = new byte[chars.length];
        random.randomBinary(key);
        this.value = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int b = chars[i];
            this.value[i] = (byte) (b + key[i]);
        }

    }

    /**
     * Set a to string formatter
     * @param formatter To String formatter
     */
    public void setFormatter(ValueStringFormatter formatter) {
        this.formatter = formatter;
    }
}
