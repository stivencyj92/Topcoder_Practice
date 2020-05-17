import java.util.*;
import java.lang.Math;
import java.math.*;

public class TwoLadders {
	public Map<Pair, Long> memo;

	public long minSteps(int sx, int lx1, int lx2, int[] X, int[] Y) {
		memo = new HashMap<Pair, Long>();
		int[][] a = new int[X.length][2];
		int n = X.length;
		for (int i = 0; i < n; i++) {
			a[i][0] = X[i];
			a[i][1] = Y[i];
		}
		Arrays.sort(a, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[1], o2[1]);
			}
		});
		return getMinSteps(0, sx, lx1, lx2, a, 0);
	}

	public long getMinSteps(int startY, int startX, int lx1, int lx2, int[][] a, int startIndex) {
		if (startIndex == a.length) {
			return 0;
		}
		if (startY != a[startIndex][1]) {
			long val1 = 0L + Math.abs(startX - lx1) + (a[startIndex][1] - startY) + getMinSteps(a[startIndex][1], lx1, lx1, lx2, a, startIndex);
			long val2 = 0L + Math.abs(startX - lx2) + (a[startIndex][1] - startY) + getMinSteps(a[startIndex][1], lx2, lx1, lx2, a, startIndex);
			return Math.min(val1, val2);
		}
		if (memo.containsKey(new Pair(startX, startIndex))) {
			return memo.get(new Pair(startX, startIndex));
		}
		int pointer = startIndex;
		while ((pointer < a.length) && (a[pointer][1] == a[startIndex][1])) {
			pointer++;
		}
		ArrayList<Integer> xVals = new ArrayList<Integer>();
		for (int i = startIndex; i < pointer; i++) {
			xVals.add(a[i][0]);
		}
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i : xVals) {
			if (i < min) {
				min = i;
			}
			if (i > max) {
				max = i;
			}
		}
		if (pointer == a.length) {
			long val1 = 0L + Math.abs(startX - min) + Math.abs(max - min);
			long val2 = 0L + Math.abs(startX - max) + Math.abs(max - min);
			long ret = Math.min(val1, val2);
			memo.put(new Pair(startX, startIndex), ret);
			return ret;
		}
		long val1 = 0L + Math.abs(startX - min) + Math.abs(max - min) + Math.abs(lx1 - max) + getMinSteps(startY, lx1, lx1, lx2, a, pointer);
		long val2 = 0L + Math.abs(startX - min) + Math.abs(max - min) + Math.abs(lx2 - max) + getMinSteps(startY, lx2, lx1, lx2, a, pointer);
		long val3 = 0L + Math.abs(startX - max) + Math.abs(min - max) + Math.abs(lx1 - min) + getMinSteps(startY, lx1, lx1, lx2, a, pointer);
		long val4 = 0L + Math.abs(startX - max) + Math.abs(min - max) + Math.abs(lx2 - min) + getMinSteps(startY, lx2, lx1, lx2, a, pointer);
		long ret = Math.min(Math.min(val1, val2), Math.min(val3, val4));
		memo.put(new Pair(startX, startIndex), ret);
		return ret;
	}

	class Pair implements Comparable<Pair> {
		public int a, b;

		public Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			Pair p = (Pair) o;
			return (this.compareTo(p) == 0);
		}

		@Override
		public int hashCode() {
			return (int) ((1L * a * 103948902382L + b) % 1_000_000_007);
		}

		@Override
		public int compareTo(Pair p) {
			if (this.a < p.a) {
				return -1;
			}
			if (this.a > p.a) {
				return 1;
			}
			if (this.b < p.b) {
				return -1;
			}
			if (this.b > p.b) {
				return 1;
			}
			return 0;
		}
	}
}