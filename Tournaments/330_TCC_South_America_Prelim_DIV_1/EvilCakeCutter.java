public class EvilCakeCutter {
	public double successProbability(int w, int h, int w1, int h1) {
		return 1 - func(w, w1) * func(h, h1);
	}

	public double func(int a, int b) {
		if (a == b) {
			return 1;
		}
		double l1 = 0;
		double r1 = b;
		double l2 = Math.max(a - 2 * b, 0);
		double r2 = Math.max(a - b, 0);
		double l = Math.max(l1, l2);
		double r = Math.min(r1, r2);
		return Math.max(0, r - 1) / (a - b);
	}
}