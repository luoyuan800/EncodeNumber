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
package cn.luo.yuan.utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Copyright 2015 gluo.
 * ALL RIGHTS RESERVED.
 * Created by gluo on 9/17/2015.
 */
public class Random extends java.util.Random {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong seedUniquifier
            = new AtomicLong(8682522807148012L);

    public Random(long seed) {
        super(seedUniquifier() ^ seed);
    }

    private static long seedUniquifier() {
        // L'Ecuyer, "Tables of Linear Congruential Generators of
        // Different Sizes and Good Lattice Structure", 1999
        for (; ; ) {
            long current = seedUniquifier.get();
            long next = current * 181783497276652981L;
            if (seedUniquifier.compareAndSet(current, next))
                return next;
        }
    }

    public long nextLong(long num) {
        try {
            if (num <= 0) {
                return 0;
            }
            if (num < Integer.MAX_VALUE) {
                return nextInt((int) num);
            } else {
                int j;
                long k;
                j = Integer.MAX_VALUE - 1;
                k = (num - Integer.MAX_VALUE);
                if (k > Integer.MAX_VALUE) {
                    k = k - nextInt(Integer.MAX_VALUE - 1);
                }
                if (k > Integer.MAX_VALUE / 2 && nextBoolean()) {
                    k = nextInt(Integer.MAX_VALUE);
                }
                return (long) nextInt(j) + k / 2;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    public int nextInt(int num) {
        if (num <= 0) {
            return 0;
        }
        return super.nextInt(num);
    }

    public final <T> T randomItem(T[] ts) {
        if (ts == null || ts.length == 0) {
            return null;
        }
        return ts[nextInt(ts.length)];
    }

    public void randomBinary(byte[] binary) {
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) nextInt(2);
        }
    }


    public long randomRange(long min, long max) {
        boolean negative = min < 0 && max < 0;
        if (negative) {
            min = -min;
            max = -max;
        }
        if (max < min) {
            max = max ^ min;
            min = max ^ min;
            max = max ^ min;
        }

        long value = min + nextLong(max - min);
        if (negative) {
            value = -value;
        }
        return value;
    }

    public float randomRange(float min, float max) {
        if (max < min) {
            return 0;
        }
        return min + nextFloat(max - min);
    }

    public float nextFloat(float max) {
        String value = String.valueOf(max);
        int dotIndex = value.indexOf(".");
        int multiple = 1;
        if (dotIndex >= 0) {
            int length = value.length() - dotIndex - 1;
            while (length-- > 0) {
                multiple *= 10;
            }
        }
        return nextLong((long) (max * multiple)) / multiple;
    }

    public long reduceToSpecialDigit(Long number, int digit) {
        long maxValue = (long) Math.pow(10, digit);
        while (number >= maxValue) {
            String numStr = String.valueOf(number);
            number = 0L;
            for (int i = 0; i < numStr.length(); i++) {
                number += nextLong(Integer.parseInt(numStr.charAt(i) + ""));
            }
        }
        return number;
    }

    public <T> T randomItem(List<T> list) {
        if (list.size() <= 0) {
            return null;
        }
        return list.get(nextInt(list.size()));
    }
}
