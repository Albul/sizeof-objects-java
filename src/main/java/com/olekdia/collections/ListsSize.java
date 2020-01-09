package com.olekdia.collections;

import static com.olekdia.ObjectSizeCounter.printDeepSize;

public class ListsSize {

    public static void main(String[] arguments) {
        final Lists lists = new Lists(100_000);
        printDeepSize(lists.mArray);
        printDeepSize(lists.mArrayList);
        printDeepSize(lists.mLinkedList);
        printDeepSize(lists.mStack);
        printDeepSize(lists.mTreePVector);
        printDeepSize(lists.mConsPStack);
        printDeepSize(lists.mJImmutableList);
        printDeepSize(lists.mJImmutableStack);
    }
}