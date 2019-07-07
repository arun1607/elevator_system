package com.app.learning;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class NoDuplicatesPriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {

    public NoDuplicatesPriorityBlockingQueue(int queueCapacity, Comparator<? super E> comparator) {
        super(queueCapacity, comparator);
    }

    public boolean add(E e) {
        boolean isAdded = false;
        if (!super.contains(e)) {
            isAdded = super.add(e);
        }
        return isAdded;
    }
}
