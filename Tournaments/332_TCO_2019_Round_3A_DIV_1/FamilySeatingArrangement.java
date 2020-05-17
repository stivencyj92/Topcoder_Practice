import java.util.*;

public class FamilySeatingArrangement {
	static final int P = 1_000_000_007;

	public int countWays(int[] a, int k) {
		int n = a.length;

		ModuloCombinatorics mc = new ModuloCombinatorics(2000, P);

		int[] dp = new int[k + 1];
		dp[0] = 1;

		for (int i = 0; i < n; i++) {
			int[] next = new int[k + 1];
			for (int j = 0; j <= k; j++) {
				next[j] += (int) ((long) dp[j] * j % P);
				if (next[j] >= P) {
					next[j] -= P;
				}
				if (j != k) {
					next[j + 1] += (int) ((long) dp[j] * (k - j) % P);
					if (next[j + 1] >= P) {
						next[j + 1] -= P;
					}
				}
			}
			dp = next;
		}
		long ret = 0;

		int kids = 0;
		for (int x : a) {
			kids += x;
		}

		for (int x : a) {
			kids += x;
		}

		for (int i = 1; i <= k; i++) {
			int seats = k - i + 1;
			ret += (int) ((long) dp[i] * mc.pow(seats, kids) % P);
			if (ret >= P) {
				ret -= P;
			}
		}

		return (int) ret;
	}

	static class ModuloCombinatorics {
		final int N, P;
		final int[] fact, inv, invFact;

		public ModuloCombinatorics(int N, int P) {
			this.N = N;
			this.P = P;
			fact = new int[N + 1];
			fact[0] = 1;
			for (int i = 1; i <= N; i++) {
				fact[i] = (int) ((long) i * fact[i - 1] % P);
			}

			inv = new int[N + 1];
			inv[1] = 1;
			for (int i = 2; i <= N; i++) {
				inv[i] = P - (int) ((long) (P / i) * inv[P % i] % P);
			}

			invFact = new int[N + 1];
			invFact[0] = 1;
			for (int i = 1; i <= N; i++) {
				invFact[i] = (int) ((long) invFact[i - 1] * inv[i] % P);
			}
		}

		public int choose(int n, int k) {
			return (n < 0 || k < 0 || k > n) ? 0 : (int) ((long) fact[n] * invFact[k] % P * invFact[n - k] % P);
		}

		static public int pow(int a, long b, int mod) {
			if (a < 0 || a >= mod || b < 0) {
				throw new IllegalArgumentException();
			}
			int ret = 1;
			for (; b > 0; b >>= 1) {
				if ((b & 1) == 1) {
					ret = (int) ((long) ret * a % mod);
				}
				a = (int) ((long) a * a % mod);
			}
			return ret;
		}

		public int pow(int a, long b) {
			return pow(a, b, P);
		}
	}
}