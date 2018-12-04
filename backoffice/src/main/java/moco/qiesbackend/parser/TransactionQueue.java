package moco.qiesbackend.parser;

import java.util.ArrayDeque;
import java.util.Queue;

import moco.qiesbackend.record.TransactionRecord;

/**
 * TransactionQueue
 */
public class TransactionQueue {

    private Queue<TransactionRecord> records;

    public TransactionQueue() {
        records = new ArrayDeque<>();
    }

    public void push(TransactionRecord element) {
        records.add(element);
    }

    public TransactionRecord pop() {
        return records.remove();
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }
}