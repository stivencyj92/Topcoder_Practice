import java.util.Arrays;

public class SubsetXor {
	public long computeSmallest(long[] A) {
		long list[] = new long[64];
		Arrays.fill(list, -1);
		for (int i = 0; i < A.length; i++) {
			long p = A[i];
			for (int j = 63; j >= 0; j--) {
				if ((p & (1L << j)) == (1L << j)) {
					if (list[j] == -1) {
						list[j] = p;
						break;
					} else {
						p ^= list[j];
					}
				}
			}
		}
		long ret = 1L << 63;
		for (int i = 0; i < 64; i++ ){
			if (list[i] == -1) {
				ret = 1L << i;
				break;
			}
		}
		return ret;
	}
}