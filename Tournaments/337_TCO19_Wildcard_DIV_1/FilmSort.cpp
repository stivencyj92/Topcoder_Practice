#include <vector>
#include <list>
#include <map>
#include <set>
#include <deque>
#include <stack>
#include <bitset>
#include <algorithm>
#include <functional>
#include <numeric>
#include <utility>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cmath>
#include <cstdlib>
#include <ctime>

using namespace std;

class FilmSort {
public:
	vector<int> sort(vector<int>);
};

vector<int> FilmSort::sort(vector<int> a) {
	vector<int> ret;
	int n = (int) a.size();
	auto Apply = [&](int x) {
		for (int i = 0; i < n; i++) {
			if (a[i] == 0) {
				if (i == x) {
					return;
				}
				ret.push_back(x);
				a[i] = -a[x];
				a[x] = 0;
				break;
			}
		}
	};
	for (int i = 0; i < n; i++) {
		if (abs(a[i]) == i) {
			continue;
		}
		vector<int> cycle;
		int p = i;
		do {
			cycle.push_back(p);
			p = abs(a[p]);
		} while (p != i);
		if (i == 0) {
			for (int j = (int) cycle.size() - 2; j >= 0; j--) {
				Apply(cycle[j]);
			}
		} else {
			for (int j = (int) cycle.size() - 1; j >= 0; j--) {
				Apply(cycle[j]);
			}
			Apply(0);
		}
	}
	int x = -1;
	for (int i = 1; i < n; i++) {
		if (a[i] < 0) {
			if (x == -1) {
				x = i;
			} else {
				Apply(x);
				Apply(i);
				Apply(0);
				Apply(x);
				Apply(i);
				Apply(0);
				x = -1;
			}
		}
	}
	if (x != -1) {
		return {-1};
	}
	return ret;
}