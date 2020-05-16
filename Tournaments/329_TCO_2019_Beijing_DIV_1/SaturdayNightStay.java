import java.util.ArrayList;
import java.util.List;

public class SaturdayNightStay {
	private static int[] MONTH_DAYS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static List<Integer> SATURDAYS = new ArrayList<>();

	static {
		int dayOfWeek = 2;
		for (int i = 0; i < 365; i++) {
			if ((dayOfWeek++ % 7) == 6) {
				SATURDAYS.add(i);
			}
		}
	}

	public int countOptions(int firstDay, int firstMonth, int lastDay, int lastMonth) {
		int firstDayOfYear = calDayOfYear(firstMonth, firstDay);
		int lastDayOfYear = calDayOfYear(lastMonth, lastDay);
		if (firstDayOfYear > lastDayOfYear) {
			return 0;
		}
		int ret = 0;
		for (int i = firstDayOfYear; i < lastDayOfYear; i++) {
			for (int j = i + 1; j <= lastDayOfYear; j++) {
				for (int saturday : SATURDAYS) {
					if (saturday + 1 >= i && saturday + 1 < j) {
						ret++;
						break;
					}
				}
			}
		}
		return ret;
	}

	private static int calDayOfYear(int month, int dayOfMonth) {
		int ret = 0;
		for (int i = 0; i < month - 1; i++) {
			ret += MONTH_DAYS[i];
		}
		ret += dayOfMonth;
		return ret;
	}
}