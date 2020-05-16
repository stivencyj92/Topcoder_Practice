import java.lang.Math;
import java.util.Random;
import java.util.NoSuchElementException;

public class DivisorSequences {
	IntHashMap map = new IntHashMap();

	public int longest(int N) {
		return find_longest(N);
	}

	private int find_longest(int n) {
		if (n <= 2) {
			return 1;
		}
		if (map.contains(n)) {
			return map.get(n);
		}
		int ret = 1;
		for (int i = 1; i * i <= n; i++) {
			if (n % i == 0) {
				ret = Math.max(ret, find_longest(i));
				if (i != 1) {
					ret = Math.max(ret, find_longest(n / i));
				}
			}
			if ((n - 1) % i == 0) {
				ret = Math.max(ret, find_longest(i) + 1);
				if (i != 1) {
					ret = Math.max(ret, i + find_longest((n - 1) / i));
				}
			}
		}
		map.put(n, ret);
		return ret;
	}
}

class IntHashMap {
	private static final Random RND = new Random();
	private static final int[] SHIFTS = new int[4];
	private static final byte PRESENT_MASK = 1;
	private int size;
	private int realSize;
	private int[] keys;
	private int[] values;
	private byte[] present;
	private int step;
	private int ratio;
	private int lastKey = 0;

	static {
		for (int i = 0; i < 4; i++) {
			SHIFTS[i] = RND.nextInt(31) + 1;
		}
	}

	public IntHashMap() {
		this(3);
	}

	public IntHashMap(int capacity) {
		capacity = Math.max(capacity, 1);
		keys = new int[capacity];
		present = new byte[capacity];
		values = new int[capacity];
		ratio = 2;
		initStep(capacity);
	}

	private void initStep(int capacity) {
		step = RND.nextInt(capacity - 2) + 1;
		while (IntegerUtils.gcd(step, capacity) != 1) {
			step++;
		}
	}

	public void put(int key, int value) {
		ensureCapacity((realSize + 1) * ratio + 2);
		int current = getHash(key);
		while (present[current] != 0) {
			if ((present[current] & PRESENT_MASK) != 0 && keys[current] == key) {
				values[current] = value;
				return;
			}
			current += step;
			if (current >= values.length) {
				current -= values.length;
			}
		}
		while ((present[current] & PRESENT_MASK) != 0) {
			current += step;
			if (current >= keys.length) {
				current -= keys.length;
			}
		}
		if (present[current] == 0) {
			realSize++;
		}
		present[current] = PRESENT_MASK;
		keys[current] = key;
		values[current] = value;
		size++;
		lastKey = current;
	}

	private int getHash(int key) {
		int result = key;
		for (int i : SHIFTS) {
			result ^= key >> i;
		}
		result %= keys.length;
		if (result < 0) {
			result += keys.length;
		}
		return result;
	}

	private void ensureCapacity(int capacity) {
		if (keys.length < capacity) {
			capacity = Math.max(capacity * 2, keys.length);
			rebuild(capacity);
		}
	}

	private void rebuild(int capacity) {
		initStep(capacity);
		int[] oldKeys = keys;
		byte[] oldPresent = present;
		int[] oldValues = values;
		keys = new int[capacity];
		present = new byte[capacity];
		values = new int[capacity];
		size = 0;
		realSize = 0;
		for (int i = 0; i < oldKeys.length; i++) {
			if ((oldPresent[i] & PRESENT_MASK) == PRESENT_MASK) {
				put(oldKeys[i], oldValues[i]);
			}
		}
	}

	public boolean contains(int key) {
		int current = getHash(key);
		while (present[current] != 0) {
			if (keys[current] == key && (present[current] & PRESENT_MASK) != 0) {
				lastKey = current;
				return true;
			}
			current += step;
			if (current >= keys.length) {
				current -= keys.length;
			}
		}
		return false;
	}

	public int get(int key) {
		if ((present[lastKey] & PRESENT_MASK) != 0 && keys[lastKey] == key) {
			return values[lastKey];
		}
		int current = getHash(key);
		while (present[current] != 0) {
			if (keys[current] == key && (present[current] & PRESENT_MASK) != 0) {
				return values[current];
			}
			current += step;
			if (current >= keys.length) {
				current -= keys.length;
			}
		}
		throw new NoSuchElementException();
	}
}

class IntegerUtils {
	public static int gcd(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		while (b != 0) {
			int temp = a % b;
			a = b;
			b = temp;
		}
		return a;
	}
}