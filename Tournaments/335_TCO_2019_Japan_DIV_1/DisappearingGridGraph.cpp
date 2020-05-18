#include <bits/stdc++.h>
using namespace std;

struct UF {
	int n;
	vector<int> par;
	UF(int n) : n(n) {
		for (int i = 0; i < n; i++) {
			par.push_back(i);
		}
	}
	int find(int a) {
		if (a != par[a]) {
			par[a] = find(par[a]);
		}
		return par[a];
	}
	void join(int a, int b) {
		par[find(a)] = find(b);
	}
};

class DisappearingGridGraph {
public:
	vector<int> keepErasing(int R, int C, int N, int seed) {
		using ll = long long;
		const ll A = 1103515245;
		const ll B = 12345;
		ll state = seed;
		vector<vector<int> > exists(R + 2, vector<int>(C + 2, 0));
		for (int i = 1; i <= R; i++) {
			for (int j = 1; j <= C; j++) {
				exists[i][j] = 1;
			}
		}
		UF uf((R + 1) * (C + 1));
		for (int i = 0; i <= R; i++) {
			for (int j = 0; j <= C; j++) {
				if (i > 0 && i < R && j > 0 && j < C) {
					continue;
				}
				uf.join(0, i * (C + 1) + j);
			}
		}
		int ncc = 1;
		for (int i = 0; i < N; i++) {
			state = (state * A + B) % (111 << 31);
			int rx = (state / (111 << 10)) % R + 1;
			state = (state * A + B) % (111 << 31);
			int cx = (state / (111 << 10)) % C + 1;
			if (exists[rx][cx]) {
				exists[rx][cx] = 0;
				if (!exists[rx - 1][cx]) {
					ncc--;
				}
				if (!exists[rx + 1][cx]) {
					ncc--;
				}
				if (!exists[rx][cx - 1]) {
					ncc--;
				}
				if (!exists[rx][cx + 1]) {
					ncc--;
				}
				int a = (rx - 1) * (C + 1) + (cx - 1);
				int b = a + 1;
				int c = a + (C + 1);
				int d = c + 1;
				for (int x : vector<int>({b, c, d})) {
					if (uf.find(a) == uf.find(x)) {
						ncc += 1;
					}
					uf.join(a, x);
				}
			}
			state = (state + ncc) % (111 << 31);
		}
		UF uf2(R * C);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (!exists[i + 1][j + 1]) {
					continue;
				}
				if (exists[i + 2][j + 1]) {
					uf2.join(i * C + j, (i + 1) * C + j);
				}
				if (exists[i + 1][j + 2]) {
					uf2.join(i * C + j, i * C + (j + 1));
				}
			}
		}
		vector<int> sz(R * C, 0);
		for(int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (!exists[i + 1][j + 1]) {
					continue;
				}
				sz[uf2.find(i * C + j)] += 1;
			}
		}
		int num = 0;
		int minc = 1e8;
		int maxc = 0;
		for (int i = 0; i < R * C; i++) {
			if (sz[i] > 0) {
				num += 1;
				maxc = max(sz[i], maxc);
				minc = min(sz[i], minc);
			}
		}
		if (num == 0) {
			return {0, 0, 0};
		} else {
			return {num, minc, maxc};
		}
	}
};