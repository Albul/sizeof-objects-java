package com.olekdia.collections;

import static com.olekdia.ObjectSizeCounter.printDeepSize;

public class SetsSize {

    public static void main(String[] arguments) {
        final Sets sets = new Sets(100_000);
        printDeepSize(sets.mArray);
        printDeepSize(sets.mHashSet);
        printDeepSize(sets.mTreeSet);
        printDeepSize(sets.mArraySet);
        printDeepSize(sets.mMapPSet);
        printDeepSize(sets.mJImmutableHashSet);
        printDeepSize(sets.mJImmutableTreeSet);
    }
}