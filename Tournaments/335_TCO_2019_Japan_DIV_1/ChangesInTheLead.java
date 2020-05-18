import java.util.*;
import java.math.*;

public class ChangesInTheLead {
	private static final long MOD = 1_000_000_007;
	
	public int count(int H, int A, int C) {
		long[][][][] dp = new long[2][2][A + 1][252];
		dp[0][0][0][0] = 1;
		int t = 1;
		for (int i = 1; i <= H; i++, t = 1 - t) {
			long[][][] cur = dp[1 - t];
			long[][][] next = dp[t];
			for (long[][] a : next) {
				for (long[] aa : a) {
					Arrays.fill(aa, 0);
				}
			}
			for (int j = 0; j <= A; j++) {
				for (int k = 0; k <= C; k++) {
					if (j < A) {
						if (i == j && i > 0) {
							cur[i][j + 1][k + 1] += cur[0][j][k];
							cur[i][j + 1][k + 1] %= MOD;
						} else {
							cur[i == j ? 1 : 0][j + 1][k] += cur[0][j][k];
							cur[i == j ? 1 : 0][j + 1][k] %= MOD;
						}
					}

					next[0][j][k] += cur[0][j][k];
					next[0][j][k] %= MOD;

					if (j < A) {
						cur[1][j + 1][k] += cur[i][j][k];
						cur[1][j + 1][k] %= MOD;
					}

					if (i == j && i > 0) {
						next[0][j][k + 1] += cur[1][j][k];
						next[0][j][k + 1] %= MOD;
					} else {
						next[i == j ? 0 : 1][j][k] += cur[1][j][k];
						next[i == j ? 0 : 1][j][k] %= MOD;
					}
				}
			}
		}
		return (int) ((dp[t][0][A][C] + dp[t][1][A][C]) % MOD);
	}
}