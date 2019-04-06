package module2.huffman;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HuffmanOutputStream extends FilterOutputStream {
    protected byte[] segment;
    protected int bytesWritten;

    public HuffmanOutputStream(OutputStream out) {
        super(out);
        constructHuffman(Constants.DEFAULT_SEGMENT_SIZE_KB);
    }

    private void constructHuffman(int segmentSizeKb) {
        segment = new byte[segmentSizeKb * 1024];
        bytesWritten = 0;
    }

    @Override
    public void write(int b) throws IOException {
        segment[bytesWritten++] = (byte) b;
        if (bytesWritten == segment.length)
            writeSegment();
    }

    @Override
    public void flush() throws IOException {
        writeSegment();
    }

    protected void writeSegment() throws IOException {
        if (bytesWritten == 0)
            return;

        FreqTable freqTable = new FreqTable();
        for (int i = 0; i < bytesWritten; i++)
            freqTable.add(segment[i]);

        DataOutputStream dataOut = new DataOutputStream(out);
        dataOut.writeInt(Constants.MAGIC1);
        dataOut.writeInt(bytesWritten);
        freqTable.save(dataOut);
        dataOut.writeInt(Constants.MAGIC2);

        BitOutputStream bitout = new BitOutputStream(out);
        Encoder enc = new Encoder(freqTable);
        for (int i = 0; i < bytesWritten; i++) {
            Pair<Integer, Integer> bstr = enc.encode(segment[i]);
            bitout.write(bstr.getFirst(), bstr.getSecond());
        }

        bitout.flush();
        bytesWritten = 0;
    }
}
