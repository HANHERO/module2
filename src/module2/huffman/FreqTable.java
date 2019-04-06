package module2.huffman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class FreqTable {
	protected LinkedHashMap<Integer, Integer> freqTable;
	protected int numSyms;
	

	public FreqTable() {
		freqTable = new LinkedHashMap<Integer, Integer>();
		numSyms = 0;
	}
	
	protected FreqTable(LinkedHashMap<Integer, Integer> map, int numSyms) {
		freqTable = map;
		this.numSyms = numSyms; 
	}

	public void add(int code) {
		int freq = 0;
		if (freqTable.containsKey(code))
			freq = freqTable.get(code);
	
		freqTable.put(code, freq + 1);
		numSyms++;
	}

	public Set<Map.Entry<Integer,Integer>> entrySet() {
		return freqTable.entrySet();
	}

	public int getNumSymbols() {
		return numSyms;
	}

	public void save(DataOutputStream out) throws IOException {

		out.writeInt(numSyms);
		out.writeInt(freqTable.size());
		for (Map.Entry<Integer, Integer> entry : freqTable.entrySet()) {
			out.writeInt(entry.getKey());
			out.writeInt(entry.getValue());
		}
	}

	public static FreqTable restore(DataInputStream in) throws IOException {
		try {
			int numSyms, tableSize;

			numSyms = in.readInt();
			tableSize = in.readInt();
			LinkedHashMap<Integer, Integer> freqTable = new LinkedHashMap<Integer, Integer>();
			for (int symsRead = 0; symsRead < tableSize; symsRead++)
				freqTable.put(in.readInt(), in.readInt());
			
			return new FreqTable(freqTable, numSyms);
		} catch (EOFException e) {
			throw new IOException("Неверно сформированная таблица частот");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		FreqTable ftbl = (FreqTable) obj;
		if (numSyms != ftbl.numSyms)
			return false;

		return freqTable.equals(ftbl.freqTable);
	}
}
