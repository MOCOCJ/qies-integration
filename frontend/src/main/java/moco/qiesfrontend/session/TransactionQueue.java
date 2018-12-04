package moco.qiesfrontend.session;

import java.util.ArrayDeque;
import java.util.Queue;

import moco.qiesfrontend.transaction.record.TransactionRecord;

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