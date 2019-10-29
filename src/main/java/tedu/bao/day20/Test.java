package tedu.bao.day20;

public class Test {
	public static void main(String[] args) {
		AVLTree node = new AVLTree();
		node.add(2);
		node.add(23);
		node.add(11);
		node.add(7);
		node.add(9);
		System.out.println(node.getHeigth());
		System.out.println(node.getRightHeigth());
		System.out.println(node.getLeftHeigth());
		node.printTrea();
	}
	static class AVLTree {
		private Node root;
		
		public void add(int value) {
			if (root == null) {
				root = new Node(value);
			}else {
				root.add(value);
			}
		}
		public void printTrea(){
			root.println();
		}

		public int getHeigth() {
			return root == null?0:root.getHeigth();
		}
		public int getRightHeigth() {
			return root.getRightHeigth();
		}
		public int getLeftHeigth() {
			return root.getLeftHeigth();
		}

	}

	static class Node {
		private int value;
		private Node lNode;
		private Node rNode;

		public Node() {
		}

		public Node(int value) {
			this.value = value;
		}

		// 求节点高度
		private int getHeigth() {
			return Math.max(this.rNode == null ? 0 : this.rNode.getHeigth() + 1,
					this.lNode == null ? 0 : this.lNode.getHeigth() + 1);
		}

		// 求左节点的高度
		public int getLeftHeigth() {
			if (this.lNode == null) {
				return 0;
			} else {
				return this.lNode.getHeigth();
			}
		}

		// 求右节点的高度
		public int getRightHeigth() {
			if (this.rNode == null) {
				return 0;
			} else {
				return this.rNode.getHeigth();
			}
		}

		public void add(int value) {
			
			if (value < this.value) {
				if (this.lNode == null) {
					this.lNode = new Node(value);
				} else {
					this.lNode.add(value);
				}
			} else {
				if (this.rNode == null) {
					this.rNode = new Node(value);
				} else {
					this.rNode.add(value);
				}
			}
			
			if (this.getLeftHeigth() > this.getRightHeigth()) {
				if (this.lNode != null && this.lNode.getRightHeigth() > this.getRightHeigth()) {
					this.lNode.lNodeRotate();
				}
				this.rNodeRotate();
			}else if(this.getLeftHeigth()<this.getRightHeigth()) {
				if (this.rNode != null && this.rNode.getLeftHeigth() > this.getLeftHeigth()) {
					this.rNode.rNodeRotate();
				}
				this.lNodeRotate();
			}
			

		}

		public void remove(int value) {

		}

		// 左旋
		public void lNodeRotate() {

			// 创建新的左子树
			// 创建新的结点，以当前根结点的值
			Node node = new Node(this.value);

			// 把新的结点的左子树设置成当前结点的左子树
			node.lNode = this.lNode;

			// 把新的结点的右子树设置成带你过去结点的右子树的左子树
			node.rNode = this.rNode.lNode;

			// 创建新的根结点
			// 把当前结点的值替换成右子结点的值
			this.value = this.rNode.value;

			// （根结点+右子树）
			// 把当前结点的右子树设置成当前结点右子树的右子树
			this.rNode = this.rNode.rNode;

			// (根结点+右子树+左子树)
			// 把当前结点的左子树(左子结点)设置成新的结点
			this.lNode = node;
			
		}

		// 右旋
		public void rNodeRotate() {

			// 创建新的右节点
			// 创建的节点,以当前根节点的值为
			Node node = new Node(this.value);

			node.rNode = this.rNode;

			node.lNode = this.lNode.rNode;

			this.value = this.lNode.value;

			this.lNode = this.lNode.lNode;

			this.rNode = node;

		}
		public void println(){
			if (this.lNode != null)
				this.lNode.println();
			System.out.println(this.value);
			if (this.rNode != null)
				this.rNode.println();
		}

	}

}
