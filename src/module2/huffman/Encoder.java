package module2.huffman;

import java.util.HashMap;
import java.util.Map;

public final class Encoder {
	protected final FreqTable freqTable;
	protected final Tree htree;
	protected final Map<Integer, Long> codeMap;
	

	public Encoder(FreqTable freqTable) {
		this.freqTable = freqTable;
		htree = new Tree(freqTable);
		codeMap = new HashMap<Integer, Long>();
		mapHuffmanNode(htree.getRoot(), 0, 0);
	}
	

	public Pair<Integer, Integer> encode(int sym) {
		long code = codeMap.get(sym);
		return new Pair<Integer, Integer>((int)code, (int)(code >> 32));
	}
	

	public final Tree getTree() {
		return htree;
	}


	protected void mapHuffmanNode(Node hnode, int bitString, int length) {
		if (!hnode.isLeaf()) {
			mapHuffmanNode(hnode.getLeft(),  bitString, length + 1);
			mapHuffmanNode(hnode.getRight(), bitString | (1 << length), length + 1);
		}
		else {

			long code = ((long)length << 32) | bitString;
			codeMap.put(hnode.getSymbol(), code);
		}
	}
}
