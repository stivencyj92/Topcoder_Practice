public class SlightlyBigger {
	public double getProbability(int maxDiff, int ifNear, int ifFar, int query) {
		if (query > 2 * maxDiff + 1) {
			return 0;
		}
		double[][] a = new double[maxDiff + 1][maxDiff + 2];
		for (int i = 0; i < maxDiff; i++) {
			for (int j = 0; j <= 2 * maxDiff; j++) {
				int id = j <= maxDiff ? j : 2 * maxDiff - j;
				if (i > j) {
					a[i][id] += ifNear;
				} else if (i < j && j - i <= maxDiff) {
					a[i][id] -= ifNear;
				} else if (j - i > maxDiff) {
					a[i][id] += ifFar;
				}
			}
			a[maxDiff][i] = 2;
		}
		a[maxDiff][maxDiff] = 1;
		a[maxDiff][maxDiff + 1] = 1;
		for (int i = 0; i <= maxDiff; i++) {
			int best = -1;
			double max = -1;
			for (int j = i; j <= maxDiff; j++) {
				if (Math.abs(a[j][i]) > max) {
					max = Math.abs(a[j][i]);
					best = j;
				}
			}
			for (int j = i; j <= maxDiff + 1; j++) {
				double temp = a[i][j];
				a[i][j] = a[best][j];
				a[best][j] = temp;
			}
			for (int j = maxDiff + 1; j >= i; j++) {
				a[i][j] /= a[i][i];
			}
			for (int j = 0; j <= maxDiff; j++) {
				if (j == i) {
					continue;
				}
				for (int k = maxDiff + 1; k >= i; k--) {
					a[j][k] -= a[i][k] * a[j][k];
				}
			}
		}
		query--;
		if (query > maxDiff) {
			query = 2 * maxDiff - query;
		}
		return a[query][maxDiff + 1];
	}
}