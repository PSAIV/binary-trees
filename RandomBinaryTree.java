
public class RandomBinaryTree 
{
	static class Node
	{
		public int key;
		public int height;
		public Node left;
		public Node right;
		
		public Node(int key)
		{
			this.key = key;
			height = 0;
			left = null;
			right = null;
		}
	}
	
	static class RandTree
	{
		private Node root;
		public RandTree() { root = null; }
		public Node getRoot() { return root; }
		
		public int getHeight(Node node)
		{
			if(node == null) return -1;
			else return node.height;
		}
		
		public Node insert(Node toInsert, Node root)
		{
			Node newroot = root;
			double coinflip = Math.random() - 0.5;
			
			if(coinflip < 0) //Use a coinflip to determine branch direction
			{
				System.out.println("Branching left from " + root.key + "...");
				
				if(root.left == null)
				{
					root.left = toInsert;
				}
				else
				{
					root.left = insert(toInsert, root.left);
				}
			}
			else if(coinflip > 0)
			{
				System.out.println("Branching right from " + root.key + "...");
				if(root.right == null)
				{
					root.right = toInsert;
				}
				else
				{
					root.right = insert(toInsert, root.right);
				}
			}
			
			if((root.left == null) && (root.right != null)) root.height = root.right.height +1;
			else if((root.right == null) && (root.left != null)) root.height = root.left.height +1;
			else root.height = Math.max(getHeight(root.left), getHeight(root.right)) +1;
			
			return newroot;
		}
		public void createNode(int key)
		{
			Node toInsert = new Node(key);
			System.out.println("Inserting node for " + key);
			if(root == null) root = toInsert;
			else root = insert(toInsert, root);
			System.out.println("Node created for " + key);
			System.out.println("");
		}
	}
	
	public static void main(String[] args)
	{
		RandTree tree = new RandTree();
		
		for(int i = 0; i < 64; i++)
		{
			int key = (int) (Math.random() * 99 + 1);
			tree.createNode(key);
		}
		
		System.out.println("This tree's height is: " +tree.getHeight(tree.getRoot()));
	}
}
