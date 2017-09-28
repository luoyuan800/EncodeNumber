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
 * Use for Long
 * Created by gluo on 4/24/2017.
 */
public class EncodeLong implements EncodeNumber, Serializable {
    private final static long serialVersionUID = 1L;
    private byte[] value;
    private byte[] key;
    private Random random;
    private boolean negative;
    private ValueStringFormatter formatter;

    /**
     * Create instance of EncodeLong with a giving value and default toString formatter.
     * The default toString formatter will show the number directly while calling #toString()#
     * @param def Default Long value
     */
    public EncodeLong(long def) {
        this.random = new Random(System.currentTimeMillis());
        setValue(def);
    }

    /**
     * Create instance of EncodeLong with a value and a toString formatter
     * @param value Default Long value
     * @param formatter To String formatter
     */
    public EncodeLong(long value, ValueStringFormatter formatter){
        this(value);
        this.formatter = formatter;
    }
    public String toString(){
        return formatter!=null ? formatter.formatNumber(getValue()) : getValue().toString();
    }

    /**
     * Get the long value
     * @return Long value
     */
    public Long getValue() {
        byte[] value;
        byte[] key;
        boolean negative;
        synchronized (this){
            value = Arrays.copyOf(this.value, this.value.length);
            key = Arrays.copyOf(this.key, this.key.length);
            negative = this.negative;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            builder.append(value[i] ^ key[i]);
        }
        long number = Long.parseLong(builder.toString(), 2);
        return negative ? -number : number;
    }

    /**
     * Set the value
     * @param number Int value
     */
    public synchronized void setValue(Number number) {
        long value = number.longValue();
        if (value < 0) {
            negative = true;
            value = -value;
        }else{
            negative = false;
        }
        char[] chars = Long.toBinaryString(value).toCharArray();
        key = new byte[chars.length];
        random.randomBinary(key);
        this.value = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            byte b = Byte.parseByte(chars[i] + "");
            this.value[i] = (byte) (b ^ key[i]);
        }

    }

    @Override
    public void setFormatter(ValueStringFormatter formatter) {
        this.formatter = formatter;
    }
}
