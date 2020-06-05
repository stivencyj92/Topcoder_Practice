#include <bits/stdc++.h>
#include <vector>
#include <list>
#include <map>
#include <set>
#include <deque>
#include <queue>
#include <stack>
#include <bitset>
#include <algorithm>
#include <functional>
#include <numeric>
#include <utility>
#include <valarray>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cmath>
#include <cstdlib>
#include <cctype>
#include <string>
#include <ctime>
#include <unordered_set>
#include <unordered_map>
#include <cerrno>
#include <cfloat>
#include <ciso646>
#include <climits>
#include <clocale>
#include <csetjmp>
#include <csignal>
#include <cstdarg>
#include <cstddef>
#include <typeinfo>
#include <stdexcept>
#include <streambuf>
#include <ios>
#include <iosfwd>

using namespace std;

using int64 = long long;
using uint64 = unsigned long long;
using ushort = unsigned short;
using uint = unsigned int;
#define two(X)			(1 << (X))
#define twoL(X)			(((int64)(1)) << (X))
#define contain(S, X)	(((S) & two(X)) != 0)
#define containL(S, X)	(((S) & twoL(X)) != 0)
const double pi = acos(-1.0);
const double eps = 1e-11;
template<class T> inline void ckmin(T &a, T b) {
	if (b < a) {
		a = b;
	}
}
template<class T> inline void ckmax(T &a, T b) {
	if (b > a) {
		a = b;
	}
}
template<class T> inline T sqr(T x) {
	return x * x;
}
typedef pair<int, int> ipair;
#define SIZE(A)			((int) A.size())
#define LENGTH(A)		((int) A.length())
#define MP(A, B)		make_pair(A, B)
#define PB(X)			push_back(X)
#define FOR(i, a, b)		for (int i = (a); i < (b); i++)
#define REP(i, a)		for (int i = 0; i < (a); i++)
#define ALL(A)			A.begin(), A.end()
using VI = vector<int>;
using VS = vector<string>;

class InsidePoints {
public:
	vector <int> construct(vector <int> px, vector <int> py) {
		VI g[64];
		REP(i, SIZE(px)) {
			g[px[i]].push_back(py[i]);
		}
		vector<ipair> ret;
		int W = 85;
		ret.push_back(MP(0, W));
		FOR (x, 1, 64) {
			VI p = g[x];
			if (SIZE(p) == 0) {
				ret.push_back(MP(x, W));
				continue;
			}
			vector<ipair> ranges;
			sort(ALL(p));
			for (int y : p) {
				if (ranges.empty() || ranges.back().second + 1 != y) {
					ranges.push_back(MP(y, y));
				} else {
					ranges.back().second = y;
				}
			}
			int H = -W;
			for (auto r : ranges) {
				int s = r.first;
				int t = r.second;
				if (ret.back() != MP(x, s - 1)) {
					ret.push_back(MP(x, s - 1));
				}
				H++;
				ret.push_back(MP(x + 1, H));
				ret.push_back(MP(x, t + 1));
			}
			ret.push_back(MP(x, W));
		}
		ret.push_back(MP(63, W + 1));
		ret.push_back(MP(0, W + 1));
		VI ret2;
		for (ipair p : ret) {
			ret2.push_back(p.first);
			ret2.push_back(p.second);
		}
		printf("%d\n", SIZE(ret));
		return ret2;
	}
};