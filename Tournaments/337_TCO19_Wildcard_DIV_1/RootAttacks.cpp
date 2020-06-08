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

class RookAttacks {
public:
	vector<long long> classify(int, vector<int> , vector<int>, vector<int>, int, int);
};

const int inf = (int) 1e9;

struct Rook {
	int x;
	int y;
	int t;
};

struct Event {
	int x;
	int ya;
	int yb;
	int type;
};

template<typename T>
class fenwick {
public:
	vector<T> fenw;
	int n;

	fenwick(int _n) : n(_n) {
		fenw.resize(n);
	}

	void modify(int x, T v) {
		while (x < n) {
			fenw[x] += v;
			x |= (x + 1);
		}
	}

	T get(int x) {
		T v{};
		while (x >= 0) {
			v += fenw[x];
			x = (x & (x + 1)) - 1;
		}
		return v;
	}
};

vector<long long> RookAttacks::classify(int D, vector<int> R0, vector<int> C0, vector<int> T0, int seed, int steps) {
	map<pair<int, int>, int> mp;
	for (int i = 0; i < (int) R0.size(); i++) {
		mp[make_pair(R0[i], C0[i])] = T0[i];
	}
	long long state = seed;
	for (int i = 0; i < steps; i++) {
		state = ((long long) state * 1103515245 + 12345) % (1LL << 31);
		int r = (int) (state % D);
		state = ((long long) state * 1103515245 + 12345) % (1LL << 31);
		int c = (int) (state % D);
		state = ((long long) state * 1103515245 + 12345) % (1LL << 31);
		int t = (int) (state / (1LL << 30));
		if (mp.find(make_pair(r, c)) == mp.end()) {
			mp[make_pair(r, c)] = t;
		}
	}
	vector<Rook> rooks;
	for (auto& p : mp) {
		rooks.push_back({p.first.first, p.first.second, p.second == 0 ? 5 : 1});
	}
	int n = (int) rooks.size();
	vector<long long> ret(25, 0);
	vector<Event> events;

	auto AddVertical = [&](int x, int ya, int yb, int type) {
		if (ya <= yb) {
			events.push_back({x, ya, yb, type});
			ret[type] += yb - ya + 1;
		}
	};
	sort(rooks.begin(), rooks.end(), [&](const Rook& a, const Rook& b) {
		return (a.x < b.x || (a.x == b.x && a.y < b.y));
	});
	for (int i = 0; i < n; i++) {
		if (i == 0 || rooks[i].x != rooks[i - 1].x) {
			AddVertical(rooks[i].x, 0, rooks[i].y - 1, rooks[i].t);
		}
		int R = ((i == n - 1 || rooks[i].x != rooks[i + 1].x) ? D - 1 : rooks[i + 1].y - 1);
		int T = ((i == n - 1 || rooks[i].x != rooks[i + 1].x) ? 0 : rooks[i + 1].t);
		AddVertical(rooks[i].x, rooks[i].y + 1, R, rooks[i].t + T);
	}

	auto AddHorizontal = [&](int y, int xa, int xb, int type) {
		if (xa <= xb) {
			events.push_back({xa, -inf, y, type});
			events.push_back({xb, inf, y, type});
			ret[type] += xb - xa + 1;
		}
	};
	sort(rooks.begin(), rooks.end(), [&](const Rook& a, const Rook& b) {
		return (a.y < b.y || (a.y == b.y && a.x < b.x));
	});
	for (int i = 0; i < n; i++) {
		if (i == 0 || rooks[i].y != rooks[i - 1].y) {
			AddHorizontal(rooks[i].y, 0, rooks[i].x - 1, rooks[i].t);
		}
		int R = ((i == n - 1 || rooks[i].y != rooks[i + 1].y) ? D - 1 : rooks[i + 1].x - 1);
		int T = ((i == n - 1 || rooks[i].y != rooks[i + 1].y) ? 0 : rooks[i + 1].t);
		AddHorizontal(rooks[i].y, rooks[i].x + 1, R, rooks[i].t + T);
	}

	vector<pair<int, int> > ys;
	vector<int> any(25, 0);
	for (int i = 0; i < (int) events.size(); i++) {
		auto& e = events[i];
		if (abs(e.ya) == inf) {
			ys.emplace_back(e.yb, i);
			any[e.type] = 1;
		}
	}
	sort(ys.begin(), ys.end());
	vector<int> coords;
	for (int i = 0; i < (int) ys.size(); i++) {
		if (i == 0 || ys[i].first != ys[i - 1].first) {
			coords.push_back(ys[i].first);
		}
		events[ys[i].second].yb = (int) coords.size() - 1;
	}
	int m = (int) coords.size();
	vector<fenwick<int> > fenw(25, fenwick<int>(m));

	sort(events.begin(), events.end(), [&](const Event& a, const Event& b) {
		if (a.x != b.x) {
			return a.x < b.x;
		}
		return a.ya < b.ya;
	});
	for (auto& e : events) {
		if (e.ya == -inf) {
			fenw[e.type].modify(e.ya, 1);
		} else {
			if (e.ya == inf) {
				fenw[e.type].modify(e.yb, -1);
			} else {
				for (int type = 0; type < 25; type++) {
					if (any[type]) {
						int from = (int) (lower_bound(coords.begin(), coords.end(), e.ya) - coords.begin());
						int to = (int) (lower_bound(coords.begin(), coords.end(), e.ya + 1) - coords.begin());
						int cnt = fenw[type].get(to - 1) - fenw[type].get(from - 1);
						ret[type + e.type] += cnt;
						ret[type] -= cnt;
						ret[e.type] -= cnt;
					}
				}
			}
		}
	}

	ret[0] = (long long) D * D - n;
	for (int i = 1; i < 25; i++) {
		ret[0] -= ret[i];
	}
	return ret;
}