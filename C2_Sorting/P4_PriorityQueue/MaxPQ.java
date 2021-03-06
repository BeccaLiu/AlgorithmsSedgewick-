package P4_PriorityQueue;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Created by rliu on 10/23/16.
 */
public class MaxPQ<Key extends Comparable> implements Iterable<Key> {
    Key[] pq;
    int N;
    Key min = null;//update it every time when using insert

    public MaxPQ() {
        pq = (Key[]) new Comparable[2];
        N = 0;
    }

    public MaxPQ(Key[] a) {
        N = a.length;
        pq = (Key[]) new Comparable[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            pq[i + 1] = a[i];
        }
        for (int k = N / 2; k >= 1; k--) {
            Arrays.sink(pq, N, k);
        }
    }

    public static void main(String[] args) {
        int size = 25;
        MaxPQ<Integer> pq = new MaxPQ<Integer>();
        IntStream.range(0, size).forEach(i -> {
            int in = StdRandom.uniform(100);
            StdOut.print(in + "/");
            pq.insert(in);
            StdOut.println(pq + " :min:" + pq.min());
            if (i % 5 == 0) {
                StdOut.println(" remove largest" + pq.delMax());
            }
        });

        StdOut.println("min" + pq.min());

    }

    public void insert(Key k) {
        if (N + 1 == pq.length)
            pq = (Key[]) Arrays.resize(pq, 2 * (N + 1));
        pq[++N] = k;
        if (min == null || Arrays.less(k, min))
            min = k;
        Arrays.swim(pq, N);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key rt = pq[1];
        Arrays.exch(pq, 1, N--);
        Arrays.sink(pq, N, 1);
        if (N > 0 && N == (pq.length - 1) / 4) pq = (Key[]) Arrays.resize(pq, pq.length / 2);
        return rt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(pq[i] + " ");
        }
        return sb.toString();
    }

    public Key min() {
        if (!isEmpty())
            return min;
        else
            return null;
    }

    public Iterator<Key> iterator() {
        return new heapIterator();
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public class heapIterator implements Iterator<Key>

    {
        private MaxPQ<Key> copy;

        public heapIterator() {
            copy = new MaxPQ<>();
            for (int i = 1; i <= N; i++) {
                copy.insert(pq[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Key next() {

            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }
}
