package code;

import java.util.ArrayList;
import java.util.List;

// 二叉树的锯齿形层序遍历
// 测试链接 : https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/
public class Video_036_2_ZigzagLevelOrderTraversal {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交以下的方法
	// 用每次处理一层的优化bfs就非常容易实现
	// 如果测试数据量变大了就修改这个值
	public static int MAXN = 2001;

	public static TreeNode[] queue = new TreeNode[MAXN];

	public static int l, r;

	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root != null) {
			l = r = 0;
			queue[r++] = root;
			boolean reverse = false;
			int start, end;
			while (l < r) {
				start = l;
				end = r - 1;
				ArrayList<Integer> list = new ArrayList<Integer>();
				if (!reverse) {
					for (int i = start; i <= end; i++) {
						TreeNode cur = queue[i];
						list.add(cur.val);
					}
				} else {
					for (int i = end; i >= start; i--) {
						TreeNode cur = queue[i];
						list.add(cur.val);
					}
				}
				for (int i = start; i <= end; i++) {
					TreeNode cur = queue[l++];
					if (cur.left != null) {
						queue[r++] = cur.left;
					}
					if (cur.right != null) {
						queue[r++] = cur.right;
					}
				}
				ans.add(list);
				reverse = !reverse;
			}
		}
		return ans;
	}

}
