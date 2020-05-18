import java.util.Arrays;

public class RealWithRooks {
	private static final int INF = 1012345678;

	public String[] construct(int R, int C, int N) {
		char[][] res = new char[R][C];
		for (char[] a : res) {
			Arrays.fill(a, '.');
		}
		for (int i = 0; i < Math.max(R, C); i++) {
			for (int j = 0; j < i && j < C && i < R && N > 0; j++, N--) {
				res[i][j] = "WB".charAt((i + j) % 2);
			}
			for (int j = 0; j <= i && i < C && j < R && N > 0; j++, N--) {
				res[i][j] = "WB".charAt((i + j) % 2);
			}
		}
		String[] ret = new String[R];
		for (int i = 0; i < R; i++) {
			ret[i] = new String(res[i]);
		}
		return ret;
	}
}