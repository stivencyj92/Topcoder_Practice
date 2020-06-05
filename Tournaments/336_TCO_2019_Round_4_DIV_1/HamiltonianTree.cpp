#include <vector>
#include <cassert>
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

class HamiltonianTree {
public:
	vector<int> construct(string, int, int, int);
};

vector <int> HamiltonianTree::construct(string dfs, int seed, int jumpCost, int index) {
	vector<vector<int> > g(1);
	vector<int> pr(1, -1);
	int v = 0;
	for (char c : dfs) {
		if (c == 'D') {
			int to = (int) g.size();
			g.emplace_back(0);
			pr.push_back(v);
			g[v].push_back(to);
			v = to;
		} else {
			v = pr[v];
		}
	}
	int n = (int) g.size();
	vector<long long> A(n);
	A[0] = seed;
	for (int i = 1; i < n; i++) {
		A[i] = (((long long) A[i - 1] * 11033515245 + 12345) % (1LL << 31));
	}
	vector<int> cost(n);
	for (int i = 1; i < n; i++) {
		cost[i] = (int) (A[i] / (1LL << 21));
	}
	vector<vector<int> > L(n);
	vector<vector<int> > R(n);
	vector<vector<int> > LR(n);
	function<void(int)> Dfs = [&](int v) {
		if (g[v].empty()) {
			L[v] = R[v] = LR[v] = {v};
			return;
		}
		assert(g[v].size() == 2);
		int x = g[v][0];
		int y = g[v][1];
		Dfs(x);
		Dfs(y);
		LR[v] = vector<int>(L[x].rbegin(), L[x].rend());
		LR[v].push_back(v);
		for (int z : R[y]) {
			LR[v].push_back(z);
		}
		R[v] = vector<int>(1, v);
		for (int z : R[x]) {
			R[v].push_back(z);
		}
		for (int z : LR[y]) {
			R[v].push_back(z);
		}
		L[v] = vector<int>(1, v);
		for (int z : L[y]) {
			L[v].push_back(z);
		}
		for (int z : vector<int>(LR[x].rbegin(), LR[x].rend())) {
			L[v].push_back(z);
		}
	};
	for (int v : g[0]) {
		Dfs(v);
	}
	vector<pair<int, vector<int> > > all;
	auto AddCycle = [&](vector<int>& cycle) {
		assert((int) cycle.size() == n);
		int c = 0;
		for (int i = 0; i < n; i++) {
			int x = cycle[i];
			int y = cycle[(i + 1) % n];
			if (pr[x] == y) {
				c += cost[x];
				continue;
			}
			if (pr[y] == x) {
				c += cost[y];
				continue;
			}
			c += jumpCost;
		}
		for (int shift = 0; shift < n; shift++) {
			all.emplace_back(c, cycle);
			cycle.push_back(cycle[0]);
			cycle.erase(cycle.begin());
		}
	};
	int deg = (int) g[0].size();
	for (int start = 0; start < deg; start++) {
		{
			vector<int> cycle(1, 0);
			for (int it = 0; it < deg; it++) {
				int v = g[0][(start + it + deg) % deg];
				if (it == 0) {
					for (int z : R[v]) {
						cycle.push_back(z);
					}
				} else {
					if (it == deg - 1) {
						for (int z : vector<int>(L[v].rbegin(), L[v].rend())) {
							cycle.push_back(z);
						}
					} else {
						for (int z : LR[v]) {
							cycle.push_back(z);
						}
					}
				}
			}
			AddCycle(cycle);
		}
		{
			vector<int> cycle(1, 0);
			for (int it = 0; it < deg; it++) {
				int v = g[0][(start - it + deg) % deg];
				if (it == 0) {
					for (int z : L[v]) {
						cycle.push_back(z);
					}
				} else {
					if (it == deg - 1) {
						for (int z : vector<int>(R[v].rbegin(), R[v].rend())) {
							cycle.push_back(z);
						}
					} else {
						for (int z : vector<int>(LR[v].rbegin(), LR[v].rend())) {
							cycle.push_back(z);
						}
					}
				}
			}
			AddCycle(cycle);
		}
	}
	sort(all.begin(), all.end());
	all.resize(unique(all.begin(), all.end()) - all.begin());
	index--;
	if (index >= (int) all.size()) {
		return {};
	}
	return all[index].second;
}