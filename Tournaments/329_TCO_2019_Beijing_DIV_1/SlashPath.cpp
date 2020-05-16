#line 5 "SlashPath.cc"
#include <bits/stdc++.h>
using namespace std;

typedef vector<int> VI;
typedef vector<string> VS;
typedef vector<double> VD;
typedef long long ll;
typedef pair<int,int> PII;

const int MM = 1e9 + 7;
const double eps = 1e-8;

class SlashPath{
  public:
  void gao(vector<string>& ans, int&n, int&m, int step) {
    if (step <= 99) {
      n = (step - 1) / 2;
      m = 2;
      for (int i = 0; i < n; i++) {
        if (i % 2 == 0) {
          ans.push_back("\\\\");
        } else {
          ans.push_back("//");
        }
      }
      return;
    }
    if (step <= 399) {
      n = 15;
    } else {
      n = 49;
    }
    m = 2;
    int now = n * 2;
    while (step - 1 > now) {
      m += 2;
      now += (n - 1) * 4;
    }
    ans.push_back("\\");
    for (int i = 1; i < m; i++) {
      if (i % 2 == 1) {
        ans[0].push_back('\\');
      } else {
        ans[0].push_back('/');
      }
    }
    string p = "", q = "";
    for (int i = 0; i < m; i++) {
      p.push_back('\\');
      q.push_back('/');
    }
    for (int i = 1; i + 1 < n; i++) {
      if (i % 2 == 1) {
        ans.push_back(q);
      } else {
        ans.push_back(p);
      }
    }
    ans.push_back("");
    for (int i = 1; i < m; i++) {
      if (i % 2 == 1) {
        ans.back().push_back('\\');
      } else {
        ans.back().push_back('/');
      }
    }
    ans.back().push_back('\\');
    int u = 1;
    step -= 1;
    while (now != step) {
      now -= 4;
      if (u % 2 == 0) {
        ans[u][m - 2] = '/';
      } else {
        ans[u][m - 2] = '\\';
      }
      u++;
    }
  }
  vector <string> construct(int steps) {
    if (steps == 1 || steps % 2 == 0) {
      return vector<string> ();
    }
    vector<string> ans;
    int n, m;
    if (steps % 4 == 3) {
      gao(ans, n, m, steps);
    } else {
      gao(ans, n, m, steps - 2);
      for (int i = 0; i < n; i++) {
        ans[i].push_back('/');
      }
      ans.push_back("");
      for (int i = 0; i <= m; i++) {
        ans.back().push_back('\\');
      }
    }
    assert(n <= 50 && m <= 50);
    return ans;
  }
};