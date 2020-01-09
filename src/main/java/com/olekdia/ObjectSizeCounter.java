package com.olekdia;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

public class ObjectSizeCounter {

    enum SizeUnit {
        BYTE(1),
        KBYTE(1024),
        MBYTE(1024 * 1024);

        private final int divider;

        private SizeUnit(final int divider) {
            this.divider = divider;
        }

        public long convert(final long value) {
            return value / divider;
        }

        public String getUnitText() {
            switch (this) {
                default:
                case BYTE: return "b";
                case KBYTE: return "kb";
                case MBYTE: return "mb";
            }
        }
    }

    public static void printFullSize(Object object) {
        printFullSize(object, SizeUnit.BYTE);
    }

    public static void printFullSize(Object object, SizeUnit unit) {
        final ObjectSizeCounter counter = new ObjectSizeCounter()
                .skipFlyweightObject(false);
        System.out.println(
                "Object type: " + object.getClass().getSimpleName() +
                ", shallowSize: " + counter.sizeOf(object, unit) +
                ", deepSize: " + counter.deepSizeOf(object, unit)
        );
    }

    public static void printDeepSize(Object object) {
        final ObjectSizeCounter counter = new ObjectSizeCounter()
                .skipFlyweightObject(false);
        System.out.println(
                "Object type: " + object.getClass().getSimpleName() +
                        " " + counter.deepSizeOf(object)
        );
    }

    private boolean isSkipFlyweightField = false;

    /**
     * If true flyweight objects has a size of 0.
     * Default value is false.
     */
    public ObjectSizeCounter skipFlyweightObject(final boolean value) {
        isSkipFlyweightField = value;
        return this;
    }

    public String sizeOf(Object obj, SizeUnit unit) {
        return unit.convert(sizeOf(obj)) + " " + unit.getUnitText();
    }

    /**
     * @return an implementation-specific approximation of the amount of storage consumed
     * by the specified object.
     */
    public long sizeOf(Object object) {
        if (isSkipFlyweightField && isSharedFlyweight(object))
            return 0;

        return InstrumentationAgent.getObjectSize(object);
    }

    public String deepSizeOf(Object obj, SizeUnit unit) {
        return unit.convert(deepSizeOf(obj)) + " " + unit.getUnitText();
    }

    /**
     * Compute an implementation-specific approximation of the amount of storage consumed
     * by objectToSize and by all the objects reachable from it
     */
    public long deepSizeOf(Object obj) {
        final Map<Object, Object> visited = new IdentityHashMap<>();
        final Stack<Object> stack = new Stack<>();
        stack.push(obj);

        long result = 0;
        do {
            result += internalSizeOf(stack.pop(), stack, visited);
        } while (!stack.isEmpty());
        return result;
    }

    private long internalSizeOf(
            Object obj,
            Stack<Object> stack,
            Map<Object, Object> visited
    ) {
        if (skipObject(obj, visited)) {
            return 0;
        }

        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            addArrayElementsToStack(clazz, obj, stack);
        } else {
            // add all non-primitive fields to the stack
            while (clazz != null) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())
                            && !field.getType().isPrimitive()) {
                        field.setAccessible(true);
                        try {
                            stack.add(field.get(obj));
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
        visited.put(obj, null);
        return sizeOf(obj);
    }

    private static void addArrayElementsToStack(
            final Class clazz,
            final Object obj,
            final Stack<Object> stack
    ) {
        if (!clazz.getComponentType().isPrimitive()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                stack.add(Array.get(obj, i));
            }
        }
    }

    private static boolean skipObject(Object obj, Map<Object, Object> visited) {
        return obj == null
                || visited.containsKey(obj)
                || isSharedFlyweight(obj);
    }

    /**
     * Returns true if this is a well-known shared flyweight.
     * For example, interned Strings, Booleans and Number objects.
     * <p>
     * thanks to Dr. Heinz Kabutz
     * see http://www.javaspecialists.co.za/archive/Issue142.html
     */
    private static boolean isSharedFlyweight(Object obj) {
        // optimization - all of our flyweights are Comparable
        if (obj instanceof Comparable) {
            if (obj instanceof Enum) {
                return true;
            } else if (obj instanceof String) {
                return (obj == ((String) obj).intern());
            } else if (obj instanceof Boolean) {
                return (obj == Boolean.TRUE || obj == Boolean.FALSE);
            } else if (obj instanceof Integer) {
                return (obj == Integer.valueOf((Integer) obj));
            } else if (obj instanceof Short) {
                return (obj == Short.valueOf((Short) obj));
            } else if (obj instanceof Byte) {
                return (obj == Byte.valueOf((Byte) obj));
            } else if (obj instanceof Long) {
                return (obj == Long.valueOf((Long) obj));
            } else if (obj instanceof Character) {
                return (obj == Character.valueOf((Character) obj));
            }
        }
        return false;
    }
}