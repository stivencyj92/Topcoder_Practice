public class RulerMaker {
	public int[] placeStickers(int n) {
		int[] ret = new int[n];

		ret[0] = 1;
		int cnt = 1;
		if (n % 2 == 0) {
			for (int i=0;i<n/2-1;i++,cnt++) {
				ret[cnt] = ret[cnt-1]+1;
			}
			for (int i=0;i<n/2;i++,cnt++) {
				ret[cnt] = ret[cnt-1]+n/2;
			}
		} else {
			for (int i=0;i<n/2;i++,cnt++) {
				ret[cnt] = ret[cnt-1]+1;
			}
			for (int i=0;i<n/2;i++,cnt++) {
				ret[cnt] = ret[cnt-1]+n/2+1;
			}
		}
	}
}