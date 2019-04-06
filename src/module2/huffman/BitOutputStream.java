package module2.huffman;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    protected OutputStream out;
    protected int bitsWritten;
    protected int buf;


    public BitOutputStream(OutputStream out) {
        this.out = out;
        bitsWritten = 0;
        buf = 0;
    }

    public void write(int word, int nbits) throws IOException {
        while (nbits-- > 0) {
            writeBit(word);
            word >>= 1;
        }
    }

    public void writeBit(int bit) throws IOException {
        buf |= (bit & 0x1) << bitsWritten;
        bitsWritten++;
        if (bitsWritten == 8)
            flushBitBuffer();
    }


    public void flush() throws IOException {
        if (bitsWritten > 0)
            flushBitBuffer();

        out.flush();
    }

    protected void flushBitBuffer() throws IOException {
        out.write(buf);
        buf = 0;
        bitsWritten = 0;
    }
}
