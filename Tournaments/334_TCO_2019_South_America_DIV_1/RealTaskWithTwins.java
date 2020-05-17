import java.util.Arrays;

public class RealTaskWithTwins {
	public int[] solve(int[] ages) {
		int max = 0;
		Arrays.sort(ages);
		max = ages[ages.length - 1];
		int a = 0, t = 0;
		int[] c = new int[201];
		for (int i = 0; i < ages.length; i++) {
			c[ages[i]]++;
		}
		for (int i = 0; i < c.length; i++) {
			t += c[i] / 2;
			if (c[i] % 2 == 1) {
				a++;
			}
		}
		if (a >= 1) {
			t++;
		}
		return new int[] {max, t};
	}
}