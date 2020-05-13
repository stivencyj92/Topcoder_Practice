import java.lang.Math;

public class Byes {
	public long getNumberOfPlayers(long lowerBound, int numberOfByes) {
		if (lowerBound == 1 && numberOfByes == 0) {
			return 1;
		}

		long ret = 1L << 62;
		for (int cnt=numberOfByes+1;cnt<62;cnt++) {
			long total = 2;
			for (int i=0;i<cnt-1;i++) {
				total = total * 2 - 1;
			}
			long diff = lowerBound - total;
			int left = cnt - 1 - numberOfByes;
			for (int i=cnt-2;i>=0&&left>=0;i--) {
				if (left == i+1 || ((((1L<<left)-1)<<(i-left))<diff)) {
					left--;
					total += 1L<<i;
					diff -= 1L<<i;
				}
			}
			if (left == 0 && diff <= 0) {
				ret = Math.min(ret, total);
			}
		}
		return ret;
	}
}