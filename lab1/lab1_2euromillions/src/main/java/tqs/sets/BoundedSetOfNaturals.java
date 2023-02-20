package tqs.sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * offers a bounded set data structure, thus complying with:
 * - the set is created for a maximum number of elements (bounded to a limit), but can contain less
 * - no duplicate values allowed
 * - order of elements is not relevant
 * - assuming natural numbers (non-negatives)
 */
public class BoundedSetOfNaturals implements Iterable<Integer> {

    private ArrayList<Integer> collection;
    private int maxSize;

    public void add(int element) {
        if (this.collection.size() >= maxSize) {
            throw new IllegalArgumentException("bounded set is full. no more elements allowed.");
        }
        if (this.collection.contains(element)) {
            throw new IllegalArgumentException("duplicate value: " + element);
        }
        if (element <= 0) {
            throw new IllegalArgumentException("Illegal argument: not a natural number");
        }
        collection.add(element);
    }

    public void add(int[] numbers) {
        for (int number : numbers) {
            this.add(number);
        }
    }

    public void remove(int element) {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("bounded set is empty.");
        }
        if ( ! this.collection.contains(element)){
            throw new IllegalArgumentException("value not in the bounded set.");
        }
        collection.remove(element);
    }

    public BoundedSetOfNaturals(int maxSize) {
        this.maxSize = maxSize;
        this.collection = new ArrayList<>();
    }

    /**
     * create a new instance initialized with the values passed as argument.
     * The maxSize would be the size of the array
     *
     * @param values elements to initialize this new Set
     * @return the new Set
     */
    public static BoundedSetOfNaturals fromArray(int[] values) {
        BoundedSetOfNaturals newSet = new BoundedSetOfNaturals(values.length);
        for (int element : values) {
            newSet.add(element);
        }
        return newSet;
    }

    public boolean isEmpty(){
        return this.collection.isEmpty();
    }

    public int size() {
        return this.collection.size();
    }

    public boolean intersects(BoundedSetOfNaturals subset) {
        for(Integer integer : subset){
            if( ! this.collection.contains(integer)){
                return false;
            }
        }
        return true;
    }

    public boolean contains(Integer element) {
        return collection.contains(element);
    }

    @Override
    public Iterator<Integer> iterator() {
        return collection.iterator();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.collection);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final BoundedSetOfNaturals other = (BoundedSetOfNaturals) obj;
        return Objects.equals(this.collection, other.collection);
    }
}