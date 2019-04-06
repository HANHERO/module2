package module2.huffman;

import java.util.Map;
import java.util.PriorityQueue;

class Tree {
    protected Node root;

    public Tree(FreqTable freqTable) {
        if (freqTable.getNumSymbols() == 0)
            throw new IllegalArgumentException("Таблица частот пуста!");

        PriorityQueue<Node> pqueue = new PriorityQueue<Node>();
        for (Map.Entry<Integer, Integer> entry : freqTable.entrySet())
            pqueue.add(new Node(entry.getKey(), entry.getValue()));

        while (pqueue.size() > 1) {
            Node first = pqueue.poll();
            Node second = pqueue.poll();
            pqueue.add(new Node(first, second));
        }

        root = pqueue.peek();
    }


    public Node getRoot() {
        return root;
    }
}
