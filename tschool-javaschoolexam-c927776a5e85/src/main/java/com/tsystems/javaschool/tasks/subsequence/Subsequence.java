package com.tsystems.javaschool.tasks.subsequence;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @AUTHOR powerlord1990
 */
public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if( x == null || y == null ) throw new IllegalArgumentException();

        if(x.isEmpty()) return true;

        if(x.size() > y.size()) return false;

        Queue yQueue = new LinkedList<>(y);
        Queue xQueue = new LinkedList<>(x);
        Object xItem;
        Object yItem;

        xItem = xQueue.remove();

        while(!yQueue.isEmpty())
        {
            yItem = yQueue.remove();

            if(yItem.equals(xItem))
            {
                if(xQueue.isEmpty()) return true;

                xItem = xQueue.remove();
            }
        }

        return false;
    }
}