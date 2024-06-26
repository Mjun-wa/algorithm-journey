package class117;

// 出现次数最多的数有几个
// 给定一个长度为n的数组arr，该数组一定是有序的
// 一共有m次查询，每次查询arr[l~r]上出现次数最多的数有几个
// 对数器验证

import java.util.Arrays;
import java.util.HashMap;

public class Code03_FrequentValues2 {

	public static int MAXN = 100001;

	// 100001不超过2的17次方
	public static int STEP = 17;

	public static int[] arr = new int[MAXN];

	public static int[] log2 = new int[MAXN];

	public static int[] bucket = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[][] stmax = new int[MAXN][STEP];

	public static void build(int n) {
		// 题目给定的数值范围-100000 ~ +100000
		// 把arr[0]设置成不会到达的数字即可
		arr[0] = -23333333;
		int cnt = 0;
		for (int i = 1; i <= n; i++) {
			if (arr[i - 1] != arr[i]) {
				right[cnt] = i - 1;
				left[++cnt] = i;
			}
			bucket[i] = cnt;
		}
		right[cnt] = n;
		log2[0] = -1;
		for (int i = 1; i <= cnt; i++) {
			log2[i] = log2[i >> 1] + 1;
			stmax[i][0] = right[i] - left[i] + 1;
		}
		for (int s = 1; s <= log2[cnt]; s++) {
			for (int i = 1; i + (1 << s) - 1 <= cnt; i++) {
				stmax[i][s] = Math.max(stmax[i][s - 1], stmax[i + (1 << (s - 1))][s - 1]);
			}
		}
	}

	public static int query(int l, int r) {
		if (l > r) {
			int tmp = l;
			l = r;
			r = tmp;
		}
		int lbucket = bucket[l];
		int rbucket = bucket[r];
		if (lbucket == rbucket) {
			return r - l + 1;
		}
		int a = right[lbucket] - l + 1, b = r - left[rbucket] + 1, c = 0;
		if (lbucket + 1 < rbucket) {
			int from = lbucket + 1, to = rbucket - 1, s = log2[to - from + 1];
			c = Math.max(stmax[from][s], stmax[to - (1 << s) + 1][s]);
		}
		return Math.max(Math.max(a, b), c);
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int n = 10000;
		int v = 100;
		int testTime = 5000;
		randomArray(n, v);
		build(n);
		for (int i = 1, l, r; i <= testTime; i++) {
			l = (int) (Math.random() * n) + 1;
			r = (int) (Math.random() * n) + 1;
			if (query(l, r) != checkQuery(l, r)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

	public static void randomArray(int n, int v) {
		for (int i = 1; i <= n; i++) {
			arr[i] = (int) (Math.random() * 2 * v) - v;
		}
		Arrays.sort(arr, 1, n + 1);
	}

	public static int checkQuery(int l, int r) {
		if (l > r) {
			int tmp = l;
			l = r;
			r = tmp;
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = l; i <= r; i++) {
			map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
		}
		int ans = 0;
		for (int v : map.values()) {
			ans = Math.max(ans, v);
		}
		return ans;
	}

}
