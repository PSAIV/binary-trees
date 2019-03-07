# binary-trees
A sampling of binary trees, BSTs, AVL trees etc.

-------------------------------------------------

AVLTreeProject.java is an implementation of a self-balancing binary search tree (AVL Tree) which reads input from the provided
books.txt file. 

The tree automatically corrects imbalances, so its depth is always limited to log(n).

-----------------------------------------------------------------------------------------

RandomBinaryTree.java generates a set of 63 elements and places them randomly in a binary tree, with no order or balance properties
enforced. 

It is intended to highlight how randomly distributed data often results in binary trees with depths often only slightly
larger than log(n), but with no ordering property, finding an item still takes O(n) time.
