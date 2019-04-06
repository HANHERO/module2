package module2.huffman;

import java.io.*;


public class HuffmanInputStream extends FilterInputStream {
    protected byte[] segment;
    protected int bytesRead;
    protected boolean eof;


    public HuffmanInputStream(InputStream in) {
        super(in);
        bytesRead = 0;
        segment = null;
        eof = false;
    }

    @Override
    public int read() throws IOException {
        if (segment == null)
            readSegment();
        if (segment == null && eof)
            return -1;

        int ret = segment[bytesRead++];
        if (bytesRead == segment.length) {
            bytesRead = 0;
            segment = null;
        }

        return (ret & 0xff);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int rdLen;
        for (rdLen = 0; rdLen < len; rdLen++) {
            int val = read();
            if (val == -1) {
                if (rdLen == 0)
                    return -1;

                break;
            }

            b[off + rdLen] = (byte) val;
        }
        return rdLen;
    }


    protected void readSegment() throws IOException {
        DataInputStream dataIn = new DataInputStream(in);

        try {

            int magic = dataIn.readInt();
            if (magic != Constants.MAGIC1) {
                throw new IOException();
            }

            int segSz = dataIn.readInt();
            segment = new byte[segSz];
            FreqTable freqTable = FreqTable.restore(dataIn);

            magic = dataIn.readInt();
            if (magic != Constants.MAGIC2)
                throw new IOException();


            Decoder dec = new Decoder(freqTable);
            BitInputStream bitin = new BitInputStream(in);
            int bytesDecoded = 0;
            while (bytesDecoded < segSz) {
                int bitString = 0, length = 0;


                while (!dec.hasCode(bitString, length)) {
                    int bit = bitin.readBit();
                    if (bit == -1)
                        break;

                    bitString |= (bit << length);
                    length++;
                    if (length >= 32)
                        throw new IOException("Код Хаффмана слишком длинный");
                }
                if (length != 0)
                    segment[bytesDecoded++] = (byte) dec.decode(bitString, length);
                else {
                    eof = true;
                }
            }
        } catch (EOFException e) {
            eof = true;
        }
    }
}
