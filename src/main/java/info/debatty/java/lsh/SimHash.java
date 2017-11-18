package info.debatty.java.lsh;

import java.nio.ByteBuffer;

/**
 * @author Weizhou He
 */
public class SimHash {

    public boolean[] signature(String[] vectors) {
        int bitLen = 64;
        int[] bits = new int[bitLen];
        for (String t : vectors) {
            long v = murmurHash(t);
            for (int i = bitLen; i >= 1; --i) {
                if (((v >> (bitLen - i)) & 1) == 1)
                    ++bits[i - 1];
                else
                    --bits[i - 1];
            }
        }
        boolean[] sig = new boolean[bitLen];
        for (int i = 0; i < bitLen; i++) {
            sig[i] = bits[i] > 0;
        }
        return sig;
    }


    static long murmurHash(String doc) {
        byte[] buffer = doc.getBytes();
        ByteBuffer data = ByteBuffer.wrap(buffer);
        return murmurHash(data, 0, buffer.length, 0);
    }

    static long murmurHash(ByteBuffer key, int offset, int length, long seed) {
        long m64 = 0xc6a4a7935bd1e995L;
        int r64 = 47;

        long h64 = (seed & 0xffffffffL) ^ (m64 * length);

        int lenLongs = length >> 3;

        for (int i = 0; i < lenLongs; ++i) {
            int i_8 = i << 3;

            long k64 = ((long) key.get(offset + i_8 + 0) & 0xff)
                    + (((long) key.get(offset + i_8 + 1) & 0xff) << 8)
                    + (((long) key.get(offset + i_8 + 2) & 0xff) << 16)
                    + (((long) key.get(offset + i_8 + 3) & 0xff) << 24)
                    + (((long) key.get(offset + i_8 + 4) & 0xff) << 32)
                    + (((long) key.get(offset + i_8 + 5) & 0xff) << 40)
                    + (((long) key.get(offset + i_8 + 6) & 0xff) << 48)
                    + (((long) key.get(offset + i_8 + 7) & 0xff) << 56);

            k64 *= m64;
            k64 ^= k64 >>> r64;
            k64 *= m64;

            h64 ^= k64;
            h64 *= m64;
        }

        int rem = length & 0x7;

        switch (rem) {
            case 0:
                break;
            case 7:
                h64 ^= (long) key.get(offset + length - rem + 6) << 48;
            case 6:
                h64 ^= (long) key.get(offset + length - rem + 5) << 40;
            case 5:
                h64 ^= (long) key.get(offset + length - rem + 4) << 32;
            case 4:
                h64 ^= (long) key.get(offset + length - rem + 3) << 24;
            case 3:
                h64 ^= (long) key.get(offset + length - rem + 2) << 16;
            case 2:
                h64 ^= (long) key.get(offset + length - rem + 1) << 8;
            case 1:
                h64 ^= (long) key.get(offset + length - rem);
                h64 *= m64;
        }

        h64 ^= h64 >>> r64;
        h64 *= m64;
        h64 ^= h64 >>> r64;

        return h64;
    }
}