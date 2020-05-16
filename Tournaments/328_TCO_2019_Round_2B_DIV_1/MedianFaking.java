import java.util.Arrays;

public class MedianFaking {
	public int[] minimize(int F, int M, int[] data, int goal) {
		int le = 0, gr = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] < goal) {
				le++;
			} else if (data[i] > goal) {
				gr++;
			}
		}
		if (le > (F * M - 1) / 2 || gr > (F * M) / 2) {
			int list[] = new int[F];
			for (int i = 0; i < data.length; i++) {
				if (le > (F * M - 1) / 2 && data[i] < goal || gr > (F * M) / 2 && data[i] > goal) {
					list[i / M]++;
				} else if (gr > (F * M) / 2 && data[i] > goal) {

				}
			}
			Arrays.sort(list);
			int i = F - 1, l;
			if (le > (F * M - 1) / 2) {
				l = le - (F * M - 1) / 2;
			} else {
				l = gr - (F * M) / 2;
			}

			for (; l > 0; i--) {
				l -= list[i];
			}
			if (le > (F * M - 1) / 2) {
				return new int[] {F - i - 1, le - (F * M - 1) / 2};
			} else {
				return new int[] {F - i - 1, gr - (F * M) / 2};
			}
		} else {
			return new int[] {0, 0};
		}
	}
}