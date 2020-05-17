import java.math.BigInteger;

public class PrefixComposite {
	public long[] minMax(long A, long B) {
		long v = nextPC(A, 1);
		if (v > B) {
			return new long[0];
		}
		return new long[] {v, nextPC(B, -1)};
	}

	private long nextPC(long v, int off) {
		long prefixes[] = new long[18];
		while (true) {
			prefixes[0] = v;
			for (int i = 1; i < 18; i++) {
				prefixes[i] = prefixes[i - 1] / 10;
			}
			for (int i = 17;; i--) {
				if (i < 0) {
					return v;
				}
				if (prefixes[i] != 0 && !isComposite(prefixes[i])) {
					v = prefixes[i] + off;
					for (int j = 0; j < i; j++) {
						v *= 10;
						if (off < 0) {
							v += 9;
						}
					}
					break;
				}
			}
		}
	}

	private boolean isComposite(long v) {
		if (v < 4) {
			return false;
		}
		long  g = gcd(v, 614889782588491410L);
		if (g != 1 && g != v) {
			return true;
		}
		return !BigInteger.valueOf(v).isProbablePrime(25);
	}

	private long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}