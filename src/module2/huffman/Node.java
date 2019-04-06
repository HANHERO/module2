package module2.huffman;


class Node implements Comparable<Node> {
	private int sym;
	private int freq;
	private boolean leaf;
	private Node left;
	private Node right;
	

	public Node(int sym, int freq) {
		this.sym = sym;
		this.freq = freq;
		leaf = true;
	}

	public Node(Node left, Node right) {
		freq = left.getFreq() + right.getFreq();
		leaf = false;
		this.left = left;
		this.right = right;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public int getSymbol() {
		if (!isLeaf())
			throw new UnsupportedOperationException("Узел не листовой!");

		return sym;
	}

	public int getFreq() {
		return freq;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		String ret = "[F: " + freq + "; L: " + leaf;
		if (leaf)
			ret += String.format("; C: %c", sym);
		
		return ret + "]";
	}

	public int compareTo(Node node) {
			if (freq > node.freq)
				return 1;
			else if (freq < node.freq)
				return -1;
			else
				return 0;

	}
}
