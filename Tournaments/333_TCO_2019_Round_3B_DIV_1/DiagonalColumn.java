import java.lang.Math;

public class DiagonalColumn {
	private static final int P = 1_000_000_007;
	public int countGrids(int H, int W) {
		int mul[] = new int[110000];
		int dp[][] = new int[50][50];

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				dp[i][j] = 0;
			}
		}
		mul[0] = 1;
		for (int i = 1; i <= 100000; i++) {
			mul[i] = mul[i - 1] * 2 % P;
		}
		dp[0][0] = 1;
		for (int i = 1; i <= W; i++) {
			for (int j = 0; j < i; j++) {
				for (int k = 0; k <= H; k++) {
					int rem = k - i + j;
					if (rem < 0) {
						for (int p = 0; p < H; p++) {
							dp[i][P] = (int) (dp[i][P] + (1L * dp[j][k] * (j == 0 ? 1 : mul[Math.max(0, Math.min(H - k - 1, -rem - 1))])) % P) % P;
						}
					} else {
						dp[i][rem] = (dp[i][rem] + dp[j][k]) % P;
					}
				}
			}
		}
		int ret = 0;
		for (int i = 0; i <= W; i++) {
			for (int j = 0; j <= H; j++) {
				ret = (int) (ret + 1L * dp[i][j] * (i == 0 ? 1 : mul[Math.max(0, H - j - 1)])) % P;
			}
		}
		return ret;
	}
}