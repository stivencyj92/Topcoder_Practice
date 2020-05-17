#include <vector>
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
#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cmath>
#include <cstdlib>
#include <ctime>
 
using namespace std;
 
#define forsn(i,s,n) for(int i = (s); i < (int) (n); i++)
#define forn(i,n) forsn(i, 0, n)
 
class Gauss {
  vector<bitset<3>> rows;
  vector<int> res;
 
  public:
  string in;
 
  void tryAddRow(bitset<3> row) {
    cerr << "Adding " << row << endl;
    
    rows.push_back(row);
    res.push_back(1);
    
    bool swapped = false;
    forn (i, rows.size() - 1) {
      if (!swapped && !rows[i][i] && rows.back()[i]) {
        swap(rows[i], rows.back()), swapped = true;
      }
      if (rows[i][i] && rows.back()[i]) {
        cerr << "Entre\n";
        rows.back() ^= rows[i];
        res.back() ^= res[i];
      }
    }
    
    cerr << "Quedo " << rows.back() << ' ' << res.back() << endl;
    if (rows.back() == 0 && res.back() != 0) {
      in += "0", res.back() = 0;
    } else {
      in += "1";
    }
  }
};
 
class EllysLightbulbs {
public:
  string getMax(int N, int L, vector <string> switches) {
    vector<bitset<3>> cosos(L);
    
    forn (i, N) {
      forn (j, L) {
        cosos[j][i] = switches[i][j] == '1';
      }
    }
    
    Gauss r;
  
    forn (i, L) {
      r.tryAddRow(cosos[i]);
    }
    
    return r.in;
  }
};