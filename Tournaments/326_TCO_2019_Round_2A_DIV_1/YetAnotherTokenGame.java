public class YetAnotherTokenGame {
	private static final int MAX_A = 301;
	private static final int MAX_B = 500;

	public String getWinner(int[] piles) {
		int[] x = new int[MAX_A];

		boolean[][] used = new boolean[MAX_A][MAX_B];
		boolean[][] dp = new boolean[MAX_A][MAX_B];

		for (int i=1;i<MAX_A;i++) {
			for (int j=0;j<i;j++) {
				used[i][x[j]] = true;
			}
			for (int j=1;j<i;j++) {
				for (int k=0;k<MAX_B;k++) {
					if (dp[i-j][k]) {
						dp[i][x[j]^k] = true;
					}
				}
			}
			while (used[i][x[i]] || dp[i][x[i]]) {
				x[i]++;
			}
			dp[i][x[i]] = true;
		}
		int ret = 0;
		for (int temp : piles) {
			ret ^= x[temp];
		}
		return ret == 0 ? "Xenia" : "William";
	}
}