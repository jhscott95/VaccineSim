@SuppressWarnings("unchecked")
public class Deque<Item> {
    
	private static final int DEFAULT_CAPACITY = 10;
	
	private Item[] deque;
	private int size;
	private int maxCapacity;
	private int f;
	private int b;
	private static int access;

    /***
     *constructor: create an empty Deque with initial
     *capacity of 10
     */
    public Deque() {
		deque = (Item[])new Object[DEFAULT_CAPACITY];
		size = 0;
		f = 0;
		b = 0;
		maxCapacity = DEFAULT_CAPACITY;
		access = 0;
    }

    /***
     *constructor: create an empty Deque with initial
     *capacity of n
     */
    public Deque(int n) {
    	deque = (Item[])new Object[n];
		size = 0;
		f = 0;
		b = 0;
		maxCapacity = n;
		access = 0;
    }
    
    /***
     *add an item to the front of the Deque
     *double the array capacity if Deque is full
     */
    public void addToFront(Item item) {
    	if (size == maxCapacity) {
    		growArray();
    	}
    	if (f == 0 && size != 0) {
    		f = maxCapacity - 1;
    		deque[f] = item;
    		access++;
    	} else if (f == b && size == 0) {
    		deque[f] = item;
    		access++;
    	} else {
    		f--;
    		deque[f] = item;
    		access++;
    	}
    	size++;
    }

    /***
     *add an item to the back of the Deque;
     *double the array capacity if the Deque is full
     ***/
    public void addToBack(Item item) {
    	if (size == maxCapacity) {
    		growArray();
    	} 
    	if (size == 0 && f == b) {
    		deque[b] = item;
    		access++;
    	} else if (b == 0 && size != 0) {
    		b++;
    		deque[b] = item;
    		access++;
    	} else if (b == maxCapacity - 1) {
    		b = 0;
    		deque[b] = item;
    		access++;
    	} else {
    		b++;
    		deque[b] = item;
    		access++;
    	}
    	size++;
    }
    
    private void growArray() {
    	maxCapacity *= 2;
    	Item[] newDeque = (Item[])new Object[maxCapacity];

    	int j = 0;
    	if (f <= b) {
    		for (int i = f; i <= b; i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		}
    	} else {
    		for (int i = f; i < (maxCapacity / 2); i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		} for (int i = 0; i <= b; i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		}
    	}
    	deque = newDeque;
    	f = 0;
    	b = size - 1;
    }

    /***
     *remove and return the front item from the Deque;
     *throw EmptyDequeException if the Deque is empty;
     *reduce the array capacity by half if the size 
     *of the Deque size falls below floor(N/4) where
     *N is the array capacity, but do not resize it 
     *to be below the default capacity, which is 10
     ***/
    public Item getFirst() throws EmptyDequeException {
		if (size < 1) {
			throw new EmptyDequeException();
		}
		if (size <= (maxCapacity / 4)) {
			shrinkArray();
		}
		size--;
		Item first = deque[f];
		
		if (size == 0) {
			deque[f] = null;
			access++;
			return first;
		} else if (f == maxCapacity - 1) {
			deque[f] = null;
			access++;
			f = 0;
			return first;
		} else {
			deque[f] = null;
			access++;
			f++;
			return first;
		}
    }

     /***
     *remove and return the back item from the Deque;
     *throw EmptyDequeException if the Deque is empty;
     *reduce the array capacity by half if the size 
     *of the Deque size falls below floor(N/4) where
     *N is the array capacity, but do not resize it 
     *to be below the default capacity, which is 10
     ***/
    public Item getLast() throws EmptyDequeException {
    	if (size == 0) {
			throw new EmptyDequeException();
		}
    	if (size <= (maxCapacity / 4)) {
			shrinkArray();
		}
    	size--;
    	Item last = deque[b];
    	deque[b] = null;
    	access++;

    	if (size == 0) {
			return last;
		} else if (b == 0) {
			b = maxCapacity - 1;
			return last;
		} else {
			b--;
			return last;
		}
    }
    
    private void shrinkArray() {
    	if (maxCapacity == DEFAULT_CAPACITY) {
    		return;
    	}
    	if ((maxCapacity / 4) < DEFAULT_CAPACITY) {
    		maxCapacity = DEFAULT_CAPACITY;
    	} else {
    		maxCapacity /= 2;
    	}
    	
    	Item[] newDeque = (Item[])new Object[maxCapacity];
    	if (size == 1) {
    		return;
    	}
    	int j = 0;
    	if (f <= b) {
    		for (int i = f; i <= b; i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		}
    	} else {
    		for (int i = f; i < (maxCapacity * 2); i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		} for (int i = 0; i <= b; i++) {
    			newDeque[j] = deque[i];
                j++;
                access += 2;
    		}
    	}
    	deque = newDeque;
    	f = 0;
    	b = size - 1;
    }
    
    /***
     *return true if the Deque is empty;
     *return false if the Deque is not empty
     ***/
    public boolean isEmpty() {
		return size == 0;
    }

    /***
     *return the size of the Deque (i.e. the 
     *number of elements currently in the Deque)
     ***/
    public int size() {
    	return size;
    }

    /***
     *return but do not remove the front item in the Deque;
     *throw an EmptyDequeException if the Deque is empty
     */
    public Item peekFirst() throws EmptyDequeException {
    	if (size == 0) {
			throw new EmptyDequeException();
		}
    	access++;
    	return deque[f];
    }

    /***
     *return but do not remove the back item in the Deque;
     *throw an EmptyDequeException if the Deque is empty
     */
    public Item peekLast() throws EmptyDequeException {
    	if (size == 0) {
			throw new EmptyDequeException();
		}
    	access++;
    	return deque[b];
    }
    
    /***
     *return the underlying array
     ***/
    public Item[] getArray() {
		return deque;
    }

    /***
     *return the array accesses count
     ***/
    public int getAccessCount() {
		return access;
    }
    
    /***
     *reset the array access count to 0
     ***/
    public static void resetAccessCount() {
		access = 0;
    }
    
    public static void main(String[] args) throws EmptyDequeException {
		int N = Integer.parseInt(args[0]);
		int c = Integer.parseInt(args[1]);
		
		System.out.println("Number of operations: " + N);
		System.out.println();
		System.out.println("Starting capacity: " + c);
		System.out.println();
		
		Deque<Integer> d = new Deque<Integer>(c);
		
		for (int i = 0; i < N; i++) {
			d.addToFront(i);
		}
		
		int countAdd = access;
		System.out.println("Accesses after adding: ");
		System.out.println(countAdd);
		resetAccessCount();
		
		for (int i = 0; i < N; i++) {
			d.getFirst();
		}
		
		int countRemove = access;
		System.out.println("Accesses after remove: ");
		System.out.println(countRemove);
		
    }	
}




















