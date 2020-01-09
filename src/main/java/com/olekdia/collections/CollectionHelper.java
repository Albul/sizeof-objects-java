package com.olekdia.collections;

import androidx.collection.SparseArrayCompat;
import com.google.common.collect.ImmutableMap.Builder;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.javimmutable.collections.*;
import org.pcollections.PCollection;
import org.pcollections.PMap;
import org.pcollections.PSequence;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionHelper {

    public static Insertable add(Insertable c, final Object[] list) {
        for (Object e : list) c = c.insert(e);
        return c;
    }

    public static PCollection plus(PCollection c, final Object[] list) {
        for (Object e : list) c = c.plus(e);
        return c;
    }

    public static <T extends Collection> T add(T c, final Object[] list) {
        for (Object e : list) c.add(e);
        return c;
    }

    public static PMap plus(PMap m, final Object[] list) {
        for (Object e : list) m = m.plus(e, e);
        return m;
    }

    public static <T extends Map> T put(final T m, final Object[] list) {
        for (Object e : list) m.put(e, e);
        return m;
    }

    public static MapIterable put(final MutableMap m, final Object[] list) {
        for (Object e : list) m.put(e, e);
        return m;
    }

    public static SparseArrayCompat put(final SparseArrayCompat m, final Integer[] list) {
        for (Integer e : list) m.put(e, e);
        return m;
    }

    public static JImmutableMap put(JImmutableMap m, final Object[] list) {
        for (Object e : list) m = m.assign(e, e);
        return m;
    }

    public static JImmutableSetMap put(JImmutableSetMap m, final Object[] list) {
        for (Object e : list) m = m.insert(e, e);
        return m;
    }

    public static JImmutableListMap put(JImmutableListMap m, final Object[] list) {
        for (Object e : list) m = m.insert(e, e);
        return m;
    }
}