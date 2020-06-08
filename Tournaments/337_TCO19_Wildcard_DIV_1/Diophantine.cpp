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

class Diophantine {
public:
	long long countSolutions(int, int);
};

long long Diophantine::countSolutions(int n, int D) {
	vector<int> primes;
	int foo = 2;
	while ((int) primes.size() < n + D) {
		bool is_prime = true;
		for (int i = 2; i * i  <= foo; i++) {
			if (foo % i == 0) {
				is_prime = false;
				break;
			}
		}
		if (is_prime) {
			primes.push_back(foo);
		}
		foo++;
	}
	vector<int> q(n);
	for (int i = 0; i < n; i++) {
		q[i] = primes[i] * primes[i + D];
	}
	vector<pair<int, int> > a;
	for (int i = 0; i < n; i++) {
		for (int j = i; j < n; j++) {
			a.emplace_back(q[i] + q[j], j);
		}
	}
	sort(a.begin(), a.end());
	long long ans = 0;
	for (int i = 0; i < n; i++) {
		for (int j = i; j < n; j++) {
			int val = q[j] - q[i] - q[0];
			int from = (int) (lower_bound(a.begin(), a.end(), make_pair(val, -1)) - a.begin());
			int to = (int) (lower_bound(a.begin(), a.end(), make_pair(val, i + 1)) - a.begin());
			ans += to - from;
		}
	}
	return ans;
}