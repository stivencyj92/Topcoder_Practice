public class TheUnexpectedSwap {
	public int findExpectedResult(int digits, String prefN, int seed) {
		long[] list = new long[digits];
		list[0] = seed;
		for (int i = 1; i < digits; i++) {
			list[i] = (list[i - 1] * 1009 + 10009) % 100019;
		}
		long[] list2 = new long[digits];
		for (int i = 0; i < prefN.length(); i++) {
			list2[i] = prefN.charAt(i) - '0';
		}
		for (int i = prefN.length(); i < digits; i++) {
			list2[i] = list2[(int) (list[i] % i)];
		}
		long MOD = 1000000007;
		long num = 1L * digits * (digits - 1) / 2;
		long ret = 0, deg = 1, sum = 0;
		for (int i = 0; i < digits; i++) {
			sum += list2[i];
		}
		for (int i = digits - 1; i >= 0; i--) {
			long p = ((sum - list2[i]) + (num - digits + 1) * list2[i]) % MOD;
			ret = (ret + p * deg) % MOD;
			deg = deg * 10 % MOD;
		}
		ret = ret * 2 % MOD;
		return (int) ret;
	}
}