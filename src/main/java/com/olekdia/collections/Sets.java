package com.olekdia.collections;

import androidx.collection.ArraySet;
import org.javimmutable.collections.JImmutableMultiset;
import org.javimmutable.collections.JImmutableSet;
import org.javimmutable.collections.util.JImmutables;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;

import java.util.HashSet;
import java.util.TreeSet;

public class Sets {
    public final Integer[] mArray;
    public final HashSet<Integer> mHashSet;
    public final TreeSet<Integer> mTreeSet;
    public final ArraySet<Integer> mArraySet;
    public final MapPSet<Integer> mMapPSet;
    public final JImmutableSet<Integer> mJImmutableHashSet;
    public final JImmutableSet<Integer> mJImmutableTreeSet;

    public Sets(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mHashSet = CollectionHelper.add(new HashSet(size), mArray);
        mTreeSet = CollectionHelper.add(new TreeSet(), mArray);
        mArraySet = CollectionHelper.add(new ArraySet<>(size), mArray);
        mMapPSet = (MapPSet) CollectionHelper.plus(HashTreePSet.empty(), mArray);
        mJImmutableHashSet = (JImmutableSet) CollectionHelper.add(JImmutables.set(), mArray);
        mJImmutableTreeSet = (JImmutableSet) CollectionHelper.add(JImmutables.sortedSet(), mArray);
    }
}
