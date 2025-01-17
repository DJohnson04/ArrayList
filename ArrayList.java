import java.util.NoSuchElementException;


public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     */
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY]; 
         
    }

    /**
     * Resizes the existing array
     * 
     * this method takes no params because it's a private method that just shifts the array.
     * @param location this param dictates where to start the shift from
     * @param shiftDirection if shiftDirection is true the array will shift to the right(adding new data), 
     * if false the array will be shifted to the left(removing data)
     */
    private void shiftBackingArray(int location, boolean shiftDirection) {
        if (shiftDirection) {
            //shifts right (adding elements)
            for (int i = size; i != location; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[location] = null;
        } else {
            //shifts left (removing elements)
            for (int i = location; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null; 
        }
    } 
    
    

    /**
     * Increases the Capacity by 2 of the array
     * 
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacityOfArray() {
        T[] tempArray = (T[]) new Object[backingArray.length * 2];
        for (int i = 0; i < size; i++) {
            tempArray[i] = backingArray[i];
        }

        backingArray = tempArray;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Is amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //checks if data or index is invalid and throws an exception if it is true
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The Index provided is invalid");
        } else if (data == null) {
            throw new IllegalArgumentException("Data is NULL");
        }
        //check if the array needs resizing.
        if (backingArray[backingArray.length - 1] != null) {
            increaseCapacityOfArray();
        }
        //add index
        shiftBackingArray(index, true);
        backingArray[index] = data;
        size += 1;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Is O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        //checks if data is null:
        if (data == null) {
            throw new IllegalArgumentException("The Data is NULL");
        }
        //check if array needs additional length
        if (backingArray.length - 1 == size) {
            increaseCapacityOfArray();
        }
        //shift all elements, then add the new data
        shiftBackingArray(0, true);
        backingArray[0] = data;
        size += 1;
        
    }

    /**
     * Adds the element to the back of the list.
     *
     * Is amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is Null");
        }
        //check if array needs resizing
        if (size == backingArray.length - 1) {
            increaseCapacityOfArray();
        }
        backingArray[size] = data;
        size += 1;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * is O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is smaller than index or larger than the size of array");
        }
        T temp = backingArray[index];
        shiftBackingArray(index, false);
        size -= 1;
        return temp;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Is O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException("The List is empty");
        }
        T temp = backingArray[0];
        backingArray[0] = null;
        shiftBackingArray(0, false);
        size -= 1;
        return temp;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Is O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T returnedValue = backingArray[size - 1]; //gets the value at the back so we can return it.
        backingArray[size - 1] = null; //sets the value at the back to null;
        size -= 1; 
        return returnedValue;
    }

    /**
     * Returns the element at the specified index.
     *
     * Is O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you listed is invalid because it's either below 0" 
                                                     + " or larger than the array you are trying to access");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Is O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (backingArray[0] == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Is O(1).
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        backingArray = null;
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
