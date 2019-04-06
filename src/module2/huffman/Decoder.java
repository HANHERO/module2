package module2.huffman;

import java.util.HashMap;
import java.util.Map;


public final class Decoder {
	protected final FreqTable freqTable;
	protected final Tree htree;
	protected final Map<Long, Integer> symMap;

	public Decoder(FreqTable freqTable) {
		this.freqTable = freqTable;
		htree = new Tree(freqTable);
		symMap = new HashMap<Long, Integer>();
		fillSymMap(htree.getRoot(), 0, 0);
	}

	public boolean hasCode(int bitString, int length) {
		return symMap.containsKey(bitStringToKey(bitString, length));
	}

	public int decode(int bitString, int length) {
		return symMap.get(bitStringToKey(bitString, length));
	}
	
	protected long bitStringToKey(int bitString, int length) {
		return (long)bitString | ((long)length << 32); 
	}

	protected void fillSymMap(Node hnode, int bitString, int length) {
		if (!hnode.isLeaf()) {
			fillSymMap(hnode.getLeft(), bitString, length + 1);
			fillSymMap(hnode.getRight(), bitString | (1 << length), length + 1);
		}
		else {
			symMap.put(bitStringToKey(bitString, length), hnode.getSymbol());
		}
	}
}
