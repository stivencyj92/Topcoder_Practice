#include <iostream>
#include <list>
#include <map>
#include <set>
#include <queue>
#include <deque>
#include <stack>
#include <bitset>
#include <algorithm>
#include <functional>
#include <numeric>
#include <utility>
#include <sstream>
#include <vector>
#include <cstdio>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <cassert>

using namespace std;

typedef long long ll;
const ll MAX_ANS = 1000000000000000000LL;

ll pows[4][65];

class ReProduct {
public:
	inline ll getNext(ll n) {
		ll r = 1;
		while (n) {
			r *= n % 10;
			n /= 10;
		}
		return r;
	}

	void construct(vector<int>& v, vector<int>& amt) {
		assert(amt.size() == 4);
		while (amt[3] > 0) {
			amt[3]--;
			v.push_back(7);
		}
		while (amt[2] > 0) {
			amt[2]--;
			v.push_back(5);
		}
		while (amt[1] >= 2) {
			amt[1] -= 2;
			v.push_back(9);
		}
		while (amt[0] >= 3) {
			amt[0] -= 3;
			v.push_back(8);
		}
		while (amt[1] >= 1 && amt[0] >= 1) {
			v.push_back(6);
			amt[1]--;
			amt[0]--;
		}
		while (amt[0] >= 2) {
			v.push_back(4);
			amt[0] -= 2;
		}
		while (amt[0] > 0) {
			amt[0]--;
			v.push_back(2);
		}
		while (amt[1] > 0) {
			amt[1]--;
			v.push_back(3);
		}
		assert(amt[0] == 0);
		assert(amt[1] == 0);
		assert(amt[2] == 0);
		assert(amt[3] == 0);
		while (v.size() < 2) {
			v.push_back(1);
		}
		sort(v.begin(), v.end());
	}

	int getVal(vector<int>& base, ll x) {
		int ret = 0;
		while (x >= 10) {
			ret++;
			x = getNext(x);
		}
		return ret + base[x];
	}

	long long minimize(vector<int> base, int goal) {
		for (int i = 0; i < 100; i++) {
			if (getVal(base, i) == goal) {
				return i;
			}
		}
		ll ret = MAX_ANS;
		for (int i = 0; i < 4; i++) {
			pows[i][0] = 1;
			int inc = 2;
			if (i == 1) {
				inc = 3;
			}
			if (i == 2) {
				inc = 5;
			}
			if (i == 3) {
				inc = 7;
			}
			for (int j = 1; j <= 60; j++) {
				pows[i][j] = inc * pows[i][j - 1];
			}
		}
		for (int a = 0; a < 60 && pows[0][a] <= ret; a++) {
			for (int b = 0; b < 60 && pows[0][a] * pows[1][b] <= ret; b++) {
				for (int c = 0; c < 60 && pows[0][a] * pows[1][b] * pows[2][c] <= ret; c++) {
					for (int d = 0; d < 60 && pows[0][a] * pows[1][b] * pows[2][c] * pows[3][d] <= ret; d++) {
						ll val = pows[0][a] * pows[1][b] * pows[2][c] * pows[3][d];
						ll actual = 1;
						while (val >= 10) {
							val = getNext(val);
							actual++;
						}
						assert(val < 10);
						actual += base[val];
						if (actual == goal) {
							vector<int> v;
							vector<int> tt;
							tt.push_back(a);
							tt.push_back(b);
							tt.push_back(c);
							tt.push_back(d);
							construct(v, tt);
							ll realAns = 0;
							for (int out : v) {
								if (realAns > MAX_ANS) {
									realAns = MAX_ANS + 1;
								} else {
									realAns = 10 * realAns + out;
								}
							}
							ret = min(ret, realAns);
						}
					}
				}
			}
		}
		return ret;
	}
};