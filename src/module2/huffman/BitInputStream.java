package module2.huffman;

import java.io.*;


class BitInputStream {
    protected InputStream in;
    protected int buf;
    protected int bitsInBuf;


    public BitInputStream(InputStream in) {
        this.in = in;
        buf = bitsInBuf = 0;
    }


    public int readBit() throws IOException {
        if (bitsInBuf == 0) {
            refreshBuffer();
            if (buf == -1) {
                buf = bitsInBuf = 0;
                return -1;
            }
        }
        int ret = (buf >> (8 - bitsInBuf)) & 0x1;
        bitsInBuf--;
        return ret;
    }

    protected void refreshBuffer() throws IOException {
        buf = in.read();
        bitsInBuf = 8;
    }
}
