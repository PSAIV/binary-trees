import java.io.*;


public class AVLTreeProject 
{
	static class Book
	{
		public String ISBN;
		public String title;
		public String author;
		
		public Book(String ISBN, String title, String author)
		{
			this.ISBN = ISBN;
			this.title = title;
			this.author = author;
		}
	}
	
	static class AVLNode 
	{
		public String ISBN;
		public Book value;
		public int height;
		public AVLNode left;
		public AVLNode right;
		
		public AVLNode(String key, Book book)
		{
			ISBN = key;
			value = book;
			height = 0;
			left = null;
			right = null;
		}
	}
	
	static class AVLTree {
		private AVLNode root;
		public AVLTree() { root = null; }
		public AVLNode getRoot() { return root; }
		
		public AVLNode find(String key, AVLNode foo)
		{ //Recursively finds the node containing the specified ISBN
			if (foo == null)
			{
				System.out.println("Unable to find " + key);
				return null;
			}
			else if(foo.ISBN == key)
			{
				System.out.println("Found it! " + foo.ISBN);
				return foo;
			}
			else if(foo.ISBN.compareTo(key) < 0)
			{
				System.out.println("Branching right...");
				return find(key, foo.right);
			}
			else if(foo.ISBN.compareTo(key) > 0)
			{
				System.out.println("Branching left...");
				return find(key, foo.left);
			}
			else return null;
		}
		
		public int getHeight(AVLNode foo)
		{ //Returns the height of tree rooted at a given node
			if(foo == null) return -1;
			else return foo.height;
		}
		public String getName(AVLNode foo)
		{
			if(foo == null) return "empty";
			else return foo.ISBN;
		}
		
		public AVLNode rightRotation(AVLNode parent)
		{ //Executes when violation occurs on left side of left subtree
			AVLNode leftChild = parent.left;//Perform rotation
			parent.left = leftChild.right;
			leftChild.right = parent;
			
			parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) +1; //Adjust heights
			leftChild.height = Math.max(getHeight(leftChild.left), getHeight(leftChild.right)) +1;
					
			return leftChild; //return new parent node
		}
		
		public AVLNode leftRotation(AVLNode parent)
		{ //Executes when violation occurs on right side of right subtree
			AVLNode rightChild = parent.right;//Perform rotation
			parent.right = rightChild.left;
			rightChild.left = parent;
			
			parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) +1; //Adjust heights
			rightChild.height = Math.max(getHeight(rightChild.left), getHeight(rightChild.right)) +1;
			
			return rightChild; //return new parent node
		}
		
		public AVLNode leftRightRotation(AVLNode parent)
		{ //Executes when violation occurs on right subtree's left side
			AVLNode temp;
			
			parent.left = leftRotation(parent.left); //perform right rotation on left child
			temp = rightRotation(parent); // perform left rotation on parent
			
			return temp; // return new parent
		}
		
		public AVLNode rightLeftRotation(AVLNode parent)
		{ //Executes when violation occurs on left side's right subtree
			AVLNode temp;
			
			parent.right = rightRotation(parent.right); //perform left rotation on right child
			temp = leftRotation(parent); //perform right rotation on parent
			
			return temp; //return new parent
		}
		
		public AVLNode insert(AVLNode insertee, AVLNode root)
		{ //Recursively finds valid insertion point and re-balances tree as necessary
			AVLNode newroot = root;
			
			if(insertee.ISBN.compareTo(root.ISBN) < 0) // branch left
			{
				System.out.println(insertee.ISBN + " < " + root.ISBN + ", branching left");
				if(root.left == null)
				{
					root.left = insertee; //Inserts new node as leaf
				}
				else {
					root.left = insert(insertee, root.left); //branch left
					
					if((getHeight(root.left) - getHeight(root.right)) == 2)
					{
						System.out.println(getHeight(root.left) + " - " + getHeight(root.right) + " = 2, violation detected on left side!");
						if(getHeight(root.left.left) > getHeight(root.left.right))
						{
							System.out.println("Imbalance occured while inserting ISBN " + insertee.ISBN + "; fixed by Right rotation");
							newroot = rightRotation(root);
						}
						
						else 
						{
							System.out.println("Imbalance occured while inserting ISBN " + insertee.ISBN + "; fixed by LeftRight rotation");
							newroot = leftRightRotation(root);
						}
					}
				}		
			}
			else if(insertee.ISBN.compareTo(root.ISBN) > 0) // branch right
			{
				System.out.println(insertee.ISBN + " > " + root.ISBN + ", branching right");
				if(root.right == null)
				{
					root.right = insertee; //attach new node as leaf
				}
				else
				{
					root.right = insert(insertee, root.right); //branch right
					
					if((getHeight(root.left) - getHeight(root.right)) == -2)
					{
						System.out.println(getHeight(root.left) + " - " + getHeight(root.right) + " = -2, violation detected on right side!");
						if(getHeight(root.right.right) > getHeight(root.right.left))
						{
							System.out.println("Imbalance occured while inserting ISBN " + insertee.ISBN + "; fixed by Left rotation");
							newroot = leftRotation(root);
						}
						else 
						{
							System.out.println("Imbalance occured while inserting ISBN " + insertee.ISBN + "; fixed by RightLeft rotation");
							newroot = rightLeftRotation(root);
						}
					}
				}
			}
			else System.out.println("key " + insertee.ISBN + " already exists within tree.");
			
			if((root.left == null) && (root.right != null)) root.height = root.right.height + 1;
			else if((root.right == null) && (root.left != null)) root.height = root.left.height +1;
			else root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
			
			return newroot;
		}//end of insert method
		
	public void insertBook(Book book)
		{
			AVLNode insertee = new AVLNode(book.ISBN, book);
			
			if(root == null)
			{
				root = insertee;
			}
			else root = insert(insertee, root);
			System.out.println("Insertion of " + book.title + " complete.");
			System.out.println("");
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		AVLTree tree = new AVLTree();
		try // Builds the AVL tree by reading from file
		{
			FileInputStream fstream = new FileInputStream("books.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while((strLine = br.readLine()) != null){
				String[] tokens = strLine.split(" ");
				Book book = new Book(tokens[0], tokens[1], tokens[2]);
				tree.insertBook(book);
			}
			in.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("This tree's height is: " + tree.getHeight(tree.getRoot()));
	}
}