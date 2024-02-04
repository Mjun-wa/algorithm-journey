package class104;

// 最长双回文串长度
// 输入字符串s，求s的最长双回文子串t的长度
// 双回文子串就是可以分成两个回文串的字符串
// 比如"aabb"，可以分成"aa"、"bb"
// 测试链接 : https://www.luogu.com.cn/problem/P4555
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_LongestDoublePalindrome {

	public static int MAXN = 100002;

	public static char[] ss = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int[] left = new int[MAXN << 1];

	public static int[] right = new int[MAXN << 1];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		out.println(compute(in.readLine()));
		out.flush();
		out.close();
		in.close();
	}

	public static int compute(String s) {
		manacher(s);
		for (int i = 0; i < n; i++) {
			left[i - p[i] + 1] = Math.max(left[i - p[i] + 1], p[i] - 1);
			right[i + p[i] - 1] = Math.max(right[i + p[i] - 1], p[i] - 1);
		}
		for (int i = 2; i < n; i += 2) {
			left[i] = Math.max(left[i], left[i - 2] - 2);
		}
		for (int i = n - 1; i >= 2; i -= 2) {
			right[i] = Math.max(right[i], right[i + 2] - 2);
		}
		int ans = 0;
		for (int i = 2; i < n; i += 2) {
			if (left[i] > 0 && right[i] > 0) {
				ans = Math.max(ans, left[i] + right[i]);
			}
		}
		return ans;
	}

	public static int manacher(String str) {
		manacherss(str.toCharArray());
		int max = 0;
		for (int i = 0, c = -1, r = -1, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
				len++;
			}
			if (i + len > r) {
				r = i + len;
				c = i;
			}
			max = Math.max(max, len);
			p[i] = len;
		}
		return max - 1;
	}

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			ss[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

}