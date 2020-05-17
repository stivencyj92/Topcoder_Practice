import java.util.*;

public class TwoLineRegions {
	private static final int mod = 1_000_000_007;
	public int count(int[] a, int[] b, int[] c) {
		int n = a.length;
		Line[] ls = new Line[n];
		for (int i = 0; i < n; i++) {
			ls[i] = new Line(a[i], b[i], c[i]);
			if (ls[i].b < 0) {
				ls[i].a = -ls[i].a;
				ls[i].b = -ls[i].b;
				ls[i].c = -ls[i].c;
			}
		}
		Arrays.sort(ls, (x, y) -> Long.compare(x.a * y.b, y.a * x.b));
		int[] pow2 = new int[n + 10];
		pow2[0] = 1;
		for (int i = 1; i < pow2.length; i++) {
			pow2[i] = pow2[i - 1] * 2 % mod;
		}
		long ret = 0;
		for (int i = 0; i < n; i++) {
			Intersection[] is = new Intersection[n - 1];
			for (int k = 1; k < n; k++) {
				int j = (k + i) % n;
				long x1 = ls[i].a * ls[j].b, y1 = ls[i].c * ls[j].b;
				long x2 = ls[j].a * ls[i].b, y2 = ls[j].c * ls[i].b;
				Fraction g = new Fraction(x2 - x1, y2 - y1);
				is[k - 1] = new Intersection(k - 1, g);
			}
			Arrays.sort(is, Comparator.comparing(x -> x.y));
			BIT bit = new BIT(n);
			for (int k = 0; k < n - 1; k++) {
				int a1 = bit.query(is[k].id);
				int a2 = k - a1;
				int a3 = is[k].id - a1;
				int a4 = n - 2 - a1 - a2 - a3;
				ret = (ret + pow2[a1] + pow2[a2] + pow2[a3] + pow2[a4]) % mod;
				bit.update(is[k].id, 1);
			}
		}
		return (int) (ret * ((mod + 1) / 2) % mod);
	}

	private long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	class Fraction implements Comparable<Fraction> {
		public long x, y;

		public Fraction(long x, long y) {
			if (x < 0) {
				x = -x;
				y = -y;
			}
			long g = gcd(x, Math.abs(y));
			if (g != 0) {
				x /= g;
				y /= g;
			}
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return new Long(x * 34029341L + y).hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Fraction)) {
				return false;
			}
			return ((Fraction) other).x == x && ((Fraction) other).y == y;
		}

		@Override
		public int compareTo(Fraction other) {
			return Long.compare(this.x * other.y, this.y * other.x);
		}
	}

	class Line {
		public int a, b, c;
		public Line(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	class Intersection {
		public int id;
		public Fraction y;

		public Intersection(int id, Fraction y) {
			this.id = id;
			this.y = y;
		}
	}

	class BIT {
		private int[] tree;
		private int N;

		public BIT(int N) {
			this.N = N;
			this.tree = new int[N + 3];
		}

		public int query(int K) {
			K += 2;
			int sum = 0;
			for (int i = K; i > 0; i -= (i & -i)) {
				sum += tree[i];
			}
			return sum;
		}

		public void update(int K, int val) {
			K += 2;
			for (int i = K; i < tree.length; i += (i & -1)) {
				tree[i] += val;
			}
		}
	}
}