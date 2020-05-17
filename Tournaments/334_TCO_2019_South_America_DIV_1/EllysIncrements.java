import java.util.Arrays;

public class EllysIncrements {
	private static final int MAX = 1100005;
	private static final int DIFF = 20005;

	public int getMin(int[] A) {
		boolean[] primes = new boolean[MAX];
		int[] next = new int[MAX];
		int[] prev = new int[MAX];
		Arrays.fill(primes, true);
		primes[0] = primes[1] = false;
		for (int i = 2; i < MAX; i++) {
			if (primes[i]) {
				for (int j = 2 * i; j < MAX; j += i) {
					primes[j] = false;
				}
			}
		}
		next[MAX - 1] = MAX;
		for (int i = MAX - 2; i >= 0; i--) {
			if (primes[i]) {
				next[i] = 0;
			} else {
				next[i] = next[i + 1] + 1;
			}
		}
		prev[0] = prev[1] = MAX;
		for (int i = 2; i < MAX; i++) {
			if (primes[i]) {
				prev[i] = 0;
			} else {
				prev[i] = prev[i - 1] + 1;
			}
		}
		int n = A.length;
		int[][] minCost = new int[n][DIFF];
		for (int i = DIFF - 1; i >= 0; i--) {
			minCost[n - 1][i] = Math.max(next[A[n - 1]] - i, 0);
		}
		for (int pos = n - 2; pos >= 0; pos--) {
			for (int free = DIFF - 1; free >= 0; free--) {
				int real = next[A[pos] + free];
				int min = Math.min(DIFF - 1, real + free);
				minCost[pos][free] = real + minCost[pos + 1][min];
				int pp = prev[A[pos] + free];
				if (pp <= free) {
					minCost[pos][free] = Math.min(minCost[pos][free], minCost[pos + 1][free - pp]);
				}
			}
		}
		return minCost[0][0];
	}
}