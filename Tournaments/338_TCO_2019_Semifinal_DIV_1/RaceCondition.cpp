#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <vector>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <map>
#include <algorithm>
#include <functional>
#include <utility>
#include <bitset>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <cstdio>

using namespace std;

class RaceCondition {
public:
	string minimize(vector<int> gates) {
		int N = gates.size();

		int pos = 0;
		int i, j;
		for (i = 0; i < N; i++) {
			if (gates[i] > 0) {
				pos++;
			}
		}

		string ans;

		if (pos <= 1) {
			for (i = 0; i < N; i++) {
				for (j = 0; j < gates[i]; j++) {
					ans += (char) ('a' + i);
					ans += (char) ('A' + i);
				}
			}
			return ans;
		}

		int special = -1;
		for (i = 0; i < N; i++) {
			if (gates[i] == 1) {
				special = i;
			}
		}

		if (special != -1) {
			ans += (char) ('a' + special);
			for (i = 0; i < N; i++) {
				if (i != special) {
					for (j = 0; j < gates[i]; j++) {
						ans += (char) ('a' + i);
						ans += (char) ('A' + i);
					}
				}
			}
			ans += (char) ('A' + special);
			return ans;
		}

		int a = -1, b = -1;
		for (i = 0; i < N; i++) {
			if (gates[i] > 0) {
				if (a == -1) {
					a = i;
					gates[i]--;
				} else if (b == -1) {
					b = i;
					gates[i]--;
				}
			}
		}

		string L, R;

		for (i = 0; i < N; i++) {
			for (j = 0; j < gates[i]; j++) {
				if (i == a) {
					R += (char) ('a' + i);
					R += (char) ('A' + i);
				} else {
					L += (char) ('a' + i);
					L += (char) ('A' + i);
				}
			}
		}

		ans += (char) ('a' + a);
		ans += L;
		ans += (char) ('A' + a);
		ans += (char) ('a' + b);
		ans += R;
		ans += (char) ('A' + b);

		return ans;
	}
};