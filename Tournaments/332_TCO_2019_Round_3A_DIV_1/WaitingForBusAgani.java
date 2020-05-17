import java.lang.Math;

public class WaitingForBusAgain {
	private static final int MAX = 1100;

	public double expectedBus(int[] f) {
		int N = 0;
		double[][] prob = new double[MAX][MAX];
		double[][] ev = new double[MAX][MAX];

		double lo = 1e9;
		for (int i = 0; i < f.length; i++) {
			lo = Math.min(lo, (double) f[i]);
		}

		N = f.length;
		for (int i = 0; i < MAX; i++) {
			for (int j = 0; j< MAX; j++) {
				prob[i][j] = ev[i][j] = 0.0;
			}
		}

		prob[0][0] = 1.0;
		for (int i = 0; i < N; i++) {
			double cp = lo / f[i];
			for (int j = 0; j <= i; j++) {
				double p = prob[i][j] * cp / (j + 1.);
				prob[i + 1][j + 1] += p;
				ev[i + 1][j + 1] += p * i;

				p = cp * ((double) j) / (j + 1.);
				prob[i + 1][j + 1] += p * prob[i][j];
				ev[i + 1][j + 1] += p * ev[i][j];

				prob[i + 1][j] += prob[i][j] * (1 - cp);
				ev[i + 1][j] += (1 - cp) * ev[i][j];
			}
		}

		double ret = 0.0;
		for (int i = 0; i <= N; i++) {
			ret += ev[N][i];
		}
		return ret;
	}
}