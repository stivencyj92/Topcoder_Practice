import java.lang.Math;

public class ChocolateBreaking {
	public long minDiff(int rows, int cols, long pieces) {
		long ret = -1;
		int r = Math.min(rows, cols);
		int c = Math.max(rows, cols);
		if (pieces == 1) {
			ret = 0;
		} else if (pieces>1) {
			long v = (long) Math.sqrt(pieces);
			for (int i = 1; i <= v; i++) {
				if (pieces % i == 0) {
					long v1 = i;
					long v2 = pieces / i;
					if (v1 <= r && v2 <= c) {
						long a1 = 0, a2 = 0, a3 = 0, a4 = 0;
						if (r % v1 == 0) {
							a1 = r / v1;
							a2 = a1;
						} else {
							long v3 = r / v1;
							long rem = r - v3 * (v1 - 2);
							if (rem == r) {
								a2 = rem / 2;
								a1 = a2 + 1;
							} else {
								if (rem % 2 != 0) {
									a1 = Math.max(rem / 2 + 1, v3);
								} else {
									a1 = Math.max(rem / 2, v3);
								}
								a2=Math.min(v3, rem/2);
							}
						}
						if (c % v2 == 0) {
							a3 = c / v2;
							a4 = a3;
						} else {
							long v3 = c / v2;
							long rem = c - v3 * (v2 - 2);
							if (rem == r) {
								a4 = rem / 2;
								a3 = a4 + 1;
							} else {
								if (rem % 2 != 0) {
									a3 = Math.max(rem / 2 + 1, v3);
								} else {
									a3 = Math.max(rem / 2, v3);
								}
								a4 = Math.min(v3, rem / 2);
							}
						}
						long val = a1 * a3 - a2 * a4;
						if (ret == -1) {
							ret = val;
						} else {
							ret = Math.min(ret, val);
						}
					}
					if (v1 <= c && v2 <= r) {
						long a1 = 0, a2 = 0, a3 = 0, a4 = 0;
						if (r % v2 == 0) {
							a1 = r / v2;
							a2 = a1;
						} else {
							long v3 = r / v2;
							long rem = r - v3 * (v2 - 2);
							if (rem == r) {
								a2 = rem / 2;
								a1 = a2 + 1;
							} else {
								if (rem % 2 != 0) {
									a1 = Math.max(rem / 2 + 1, v3);
								} else {
									a1 = Math.max(rem / 2, v3);
								}
								a2 = Math.min(v3, rem / 2);
							}
						}
						if (c % v1 == 0) {
							a3 = c / v1;
							a4 = a3;
						} else {
							long v3 = c / v1;
							long rem = c - v3 * (v1 - 2);
							if (rem == r) {
								a4 = rem / 2;
								a3 = a4 + 1;
							} else {
								if (rem % 2 != 0) {
									a3 = Math.max(rem / 2 + 1, v3);
								} else {
									a3 = Math.max(rem / 2, v3);
								}
								a4 = Math.min(v3, rem / 2);
							}
						}
						long val = a1 * a3 - a2 * a4;
						if (ret == -1) {
							ret = val;
						} else {
							ret = Math.min(ret, val);
						}
					}
				}
			}
		}
		return ret;
	}
}