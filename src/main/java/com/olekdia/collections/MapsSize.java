package com.olekdia.collections;

import static com.olekdia.ObjectSizeCounter.printDeepSize;

public class MapsSize {

    public static void main(String[] arguments) {
        final Maps maps = new Maps(100_000);
        printDeepSize(maps.mArray);
        printDeepSize(maps.mHashMap);
        printDeepSize(maps.mLinkedHashMap);
        printDeepSize(maps.mTreeMap);
        printDeepSize(maps.mArrayMap);
        printDeepSize(maps.mSparseArray);
        printDeepSize(maps.mHashPMap);
        printDeepSize(maps.mIntTreePMap);
        printDeepSize(maps.mJImmutableHashMap);
        printDeepSize(maps.mJImmutableTreeMap);
        printDeepSize(maps.mEclipseMutableMap);
        printDeepSize(maps.mEclipseImmutableMap);
        printDeepSize(maps.mGuavaImmutableMap);
    }
}