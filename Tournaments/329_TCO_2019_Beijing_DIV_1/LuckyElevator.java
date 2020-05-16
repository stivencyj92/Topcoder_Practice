public class LuckyElevator {
	public int actualFloor(int buttonPressed) {
		int ret = 0;
		for (int i = 1; i <= buttonPressed; i++) {
			int p = i;
			while (p > 0) {
				int d = p % 10;
				if (d == 4) {
					break;
				}
				p /= 10;
			}
			if (p == 0) {
				ret++;
			}
		}
		return ret;
	}
}