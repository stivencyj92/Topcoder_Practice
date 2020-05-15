public class FixMultiplication {
	public int findDigit(String equation) {
		String[] split = equation.split("\\*");
		String[] split2 = split[1].split("\\=");
		String t_n1 = split[0];
		String t_n2 = split2[0];
		String t_n3 = split2[1];
		String temp, temp2;

		int n1, n2, n3;
		int type=-1;
		boolean is_divided = true;
		if (t_n1.contains("?")) {
			type = 0;
			n2 = Integer.parseInt(t_n2);
			n3 = Integer.parseInt(t_n3);
			n1 = n3 / n2;
			temp2 = String.valueOf(n1);
			temp = t_n1;
			if (n3 % n2 != 0 || String.valueOf(n1).length() != t_n1.length()) {
				is_divided = false;
			}
		} else if (t_n2.contains("?")) {
			type = 1;
			n1 = Integer.parseInt(t_n1);
			n3 = Integer.parseInt(t_n3);
			n2 = n3 / n1;
			temp2 = String.valueOf(n2);
			temp = t_n2;
			if (n3 % n1 != 0 || String.valueOf(n2).length() != t_n2.length()) {
				is_divided = false;
			}
		} else {
			type = 2;
			n1 = Integer.parseInt(t_n1);
			n2 = Integer.parseInt(t_n2);
			n3 = n1 * n2;
			temp2 = String.valueOf(n3);
			temp = t_n3;
			if (String.valueOf(n3).length() != t_n3.length()) {
				is_divided = false;
			}
		}
		if (!is_divided) {
			return -1;
		}
		int position = temp.indexOf("?");
		char new_num = temp2.charAt(position);
		int ret = Integer.parseInt(String.valueOf(new_num));
		StringBuilder comp = new StringBuilder(temp);
		comp.setCharAt(position, new_num);
		if (type == 0) {
			if (Integer.parseInt(comp.toString()) * n2 == n3) {
				return ret;
			}
		} else if (type == 1) {
			if (n1 * Integer.parseInt(comp.toString()) == n3) {
				return ret;
			}
		} else {
			if (n1 * n2 == Integer.parseInt(comp.toString())) {
				return ret;
			}
		}
		return -1;
	}
}