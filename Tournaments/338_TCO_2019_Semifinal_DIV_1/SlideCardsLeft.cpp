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

class SlideCardsLeft {
public:
	vector<int> getPosition(int, long long);
};

vector<int> SlideCardsLeft::getPosition(int N, long long steps) {
	vector<int> a(N);
	iota(a.begin(), a.end(), 0);
	for (int b = 60; b >= 0; b--) {
		if (steps & (1LL << b)) {
			if (b + 1 >= N) {
				return vector<int>();
			}
			swap(a[b + 1], a[0]);
		}
	}
	return a;
}