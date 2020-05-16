public class OrderlyString {
	public int longestLength(String s) {
		int n = s.length();
		int[] d = new int[26];
		for (char ch : s.toCharArray()) {
			int c = ch - 'A';
			int[] nd = d.clone();
			for (int i = 0; i < 26; i++) {
				if (i <= c) {
					nd[c] = Math.max(nd[c], d[i] + 1);
				}
			}
			d = nd;
		}
		int ret = 0;
		for (int x : d) {
			ret = Math.max(ret, x);
		}
		return ret;
	}
}