import java.util.Arrays;
import java.util.AbstractCollection;
import java.util.PriorityQueue;

public class TombExplorer {
	final int MAX_CHAMBERS = 6;
	int height;
	int width;
	char[][] s;
	final int[] dr = {-1, 0, 1, 0};
	final int[] dc = {0, -1, 0, 1};
	int[][] chamberDist;
	int ret;

	public int minimumDigging(String[] map) {
		height = map.length;
		width = map[0].length();
		s = new char[height][];
		for (int r = 0; r < height; r++) {
			s[r] = map[r].toCharArray();
		}
		int[][] chamberId = new int[height][width];
		for (int[] arr : chamberId) {
			Arrays.fill(arr, -1);
		}
		int numChambers = 0;
		int[] reprR = new int[MAX_CHAMBERS];
		int[] reprC = new int[MAX_CHAMBERS];
		for (int sr = 0; sr < height; sr++) {
			for (int sc = 0; sc < width; sc++) {
				if (chamberId[sr][sc] >= 0) {
					continue;
				}
				if (s[sr][sc] != '.') {
					continue;
				}
				reprR[numChambers] = sr;
				reprC[numChambers] = sc;
				int[][] dist = shortestPaths(sr, sc);
				for (int r = 0; r < height; r++) {
					for (int c = 0; c < width; c++) {
						if (dist[r][c] == 0) {
							chamberId[r][c] = numChambers;
						}
					}
				}
				numChambers++;
			}
		}
		reprR = Arrays.copyOf(reprR, numChambers);
		reprC = Arrays.copyOf(reprC, numChambers);
		chamberDist = new int[numChambers][numChambers];
		for (int i = 0; i < numChambers; i++) {
			int[][] dist = shortestPaths(reprR[i], reprC[i]);
			for (int j = 0; j < numChambers; j++) {
				chamberDist[i][j] = dist[reprR[j]][reprC[j]];
			}
		}
		ret = height * width;
		rec(0, numChambers, new boolean[numChambers], new int[numChambers]);
		return ret;
	}

	private void rec(int pos, int n, boolean[] used, int[] perm) {
		if (pos == n) {
			int cur = 0;
			for (int i = 0; i + 1 < n; i++) {
				int a = perm[i];
				int b = perm[i + 1];
				cur += chamberDist[a][b];
			}
			ret = Math.min(ret, cur);
			return;
		}

		for (int i = 0; i < n; i++) {
			if (!used[i]) {
				used[i] = true;
				perm[pos] = i;
				rec(pos + 1, n, used, perm);
				used[i] = false;
			}
		}
	}

	private int[][] shortestPaths(int sr, int sc) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		int[][] dist = new int[height][width];
		for (int[] arr : dist) {
			Arrays.fill(arr, height * width + 1);
		}
		if (s[sr][sc] != '.') {
			throw new AssertionError();
		}
		dist[sr][sc] = 0;
		pq.offer(new Vertex(sr, sc, dist[sr][sc]));
		while (!pq.isEmpty()) {
			Vertex ver = pq.poll();
			int r = ver.r;
			int c = ver.c;
			int d = ver.dist;
			if (d != dist[r][c]) {
				continue;
			}
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				if (nr < 0 || nr >= height || nc < 0 || nc >= width) {
					continue;
				}
				int nd = d + (s[nr][nc] == '.' ? 0 : 1);
				if (dist[nr][nc] > nd) {
					dist[nr][nc] = nd;
					pq.offer(new Vertex(nr, nc, dist[nr][nc]));
				}
			}
		}
		return dist;
	}

	class Vertex implements Comparable<Vertex> {
		int r;
		int c;
		int dist;

		Vertex(int r, int c, int dist) {
			this.r = r;
			this.c = c;
			this.dist = dist;
		}

		public int compareTo(Vertex o) {
			return dist = o.dist;
		}
	}
}