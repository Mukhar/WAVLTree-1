/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *
 */


/**
 * 
 * @author Yotam Aharony - 308338169 - yotamaharony
 * 		 & Itay Cohen - 308213883 - itayc
 *
 */
public class WAVLTree {
	
	//WAVL tree properties
	private WAVLNode extLeaf; //external leaf
	private WAVLNode root; // the root node of the tree
	private WAVLNode sentinel; // sentinel is a "virtual" node, uses as a parent of the root

	//constructor
	public WAVLTree()
	{
		//root's rank = -2 ---> happens iff the tree is empty.
		//on a new tree creation, this is the way to mark that the tree is empty
		root = new WAVLNode();
		root.rank = -2;
		
		//by definition, the rank of external leaf is -1, and its size is 0
		//
		extLeaf = new WAVLNode();
		sentinel = new WAVLNode();
		extLeaf.rank = -1;
		extLeaf.setSubtreeSize(0);
		
		//link sentinel to the tree, as defined before.
		root.leftChild = extLeaf;
		root.rightChild = extLeaf;
		root.parent = sentinel;
		
		sentinel.rightChild = root;
		sentinel.leftChild = root;

	}

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	//by definition, according to this implementation:
	//root's rank = -2 ---> happens iff the tree is empty.
	  return (root.getRank()==-2);
	  
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  //the assisting function in this case is findNodeFromKey
  //findNodeFromKey finds the whole node, and search extracts the value from it
  public String search(int k)
  {
	  WAVLNode searchedNode = findNodeFromKey(k);
	  if(searchedNode==null)
		  return null;
	  return searchedNode.getValue();
  }

  //this function goes down to the leaves of the tree in order to find the desired key
  //if there is no node with the desired key, return null.
  public WAVLNode findNodeFromKey(int k)
  {
	  
 	if (this.empty())
 		return null;
 	
 	WAVLNode current = root;
 	while ((current != null) && (current != extLeaf))
 	{
 		if (current.getKey() == k)
 			return current;
 		
 		if (current.getKey() > k)
 			current = current.getLeft();
 		
 		if (current.getKey() < k)
 			current = current.getRight();
 	}
 	
 	return null;
  }
  
  /**
   * public String min()
   *
   * Returns the info of the item with the smallest key in the tree,
   * or null if the tree is empty
   */
  //go left until and external leaf encountered
  // if tree is empty return null
  public String min()
  {
	   if (empty())
		   return null;
	   else
	   {
		   WAVLNode currentNode = root;
		   while (currentNode.getLeft().getKey()!=-1) //while external leaf is not encountered
		   {
			   currentNode = currentNode.getLeft();
		   }
		   return currentNode.getValue();
	   }
  }

  
  /**
   * public String max()
   *
   * Returns the info of the item with the largest key in the tree,
   * or null if the tree is empty
   */
  //go right until and external leaf encountered
  // if tree is empty return null
  public String max()
  {
	   if (empty())
		   return null;
	   else
	   {
		   WAVLNode currentNode = root;
		   while (currentNode.getRight().getKey()!=-1) //while external leaf is not encountered
		   {
			   currentNode = currentNode.getRight();
		   }
		   return currentNode.getValue();
	   }
  }

 /**
  * public int[] keysToArray()
  *
  * Returns a sorted array which contains all keys in the tree,
  * or an empty array if the tree is empty.
  */
  // the assisting function returns a string with keys that scanned in order
  // this function turns the string into an ints array and returns the array
  public int[] keysToArray()
  {
		if (empty())
			return new int[0];
        String[] strArr = recKeysToArray(root).split(",");
        int [] arr = arrayStringToInt(strArr);
        return arr;
  }
  
  //recursive function that returns a string with keys that scanned in order 
  private String recKeysToArray(IWAVLNode node) {
	  if(node.getKey()==-1) //if node is not an external leaf
		  return "";
	  String res = recKeysToArray(node.getLeft()).toString() + node.getKey() + "," + recKeysToArray(node.getRight());
	  return res;
	  
  }
  //assisting function for keysToArray() - turns strings arrays in ints array
  private int[] arrayStringToInt(String[] arr) {
	  int[] intArr = new int[arr.length];
	  for(int i=0;i<arr.length;i++) {
		  intArr[i] = Integer.parseInt(arr[i]);
	  }
	  return intArr;
  }

 /**
  * public String[] infoToArray()
  *
  * Returns an array which contains all info in the tree,
  * sorted by their respective keys,
  * or an empty array if the tree is empty.
  */
//this function turns the string into an strings array and returns the array
 public String[] infoToArray()
 {
	 if (empty())
		 return new String[0];
     String[] arr = recInfoToArray(root).split(",");
     return arr;
 }
//assisting function for infoToArray()
//recursive function that returns a string with values that scanned in order 
 private String recInfoToArray(IWAVLNode node) {
	  if(node.getKey()==-1)
		  return "";
	  String res = recInfoToArray(node.getLeft()).toString() + node.getValue() + "," + recInfoToArray(node.getRight());
	  return res;
	  
 }
  /**
   * public int size()
   *
   * Returns the number of nodes in the tree.
   *
   * precondition: none
   * postcondition: none
   */
 //size is not a field in WAVLTree (It is a field in WAVLNode)
 //therefore, size() method return the subrtree of the tree's root
  public int size()
  {
	   if (empty())
		   return 0;
	   else
		   return root.getSubtreeSize(); //method that returns the subrtree of the tree's root
  }
  
    /**
   * public int getRoot()
   *
   * Returns the root WAVL node, or null if the tree is empty
   *
   * precondition: none
   * postcondition: none
   */
  public WAVLNode getRoot()
  {
	   return root;
  }
    /**
   * public int select(int i)
   *
   * Returns the value of the i'th smallest key (return -1 if tree is empty)
   * Example 1: select(1) returns the value of the node with minimal key 
	* Example 2: select(size()) returns the value of the node with maximal key 
	* Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor 	
   *
	* precondition: size() >= i > 0
   * postcondition: none
   */   
  //the info of the i'th smallest key will be returned
  public String select(int i)
  {
	   if (empty())
		   return "-1";
	   String[] infoArray = infoToArray(); //since we have a function that sorts items' values by their keys - we will use it.
	   return infoArray[i-1];
  }

  //==================================================================
  //=========================Insert Functions=========================
  //==================================================================
  
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
  //this is the general insert function. the general priciple is simple:
  //insert the new node to the tree
  //if the tree after insertion is not valid - make some rebalance opertions in order to make it valid
  //the returned value is the number of rebalance operations
   public int insert(int k, String i) 
   {
	   int counter = 0; // counts the number of rebalance opertions
	   //if the tree is empty - the inserted node is going to be the tree's root
	   if (empty())
	   {
		   InsertEmptyTree(k, i);
		   return counter;
	   }
	   //if there is a node with the same key - insert operation won't be performed
	   else if (search(k)!=null)
	   		return -1;
	    else //in case that the new key is exclusive to the tree 
	   	{
	   		WAVLNode newNode = new WAVLNode(k,i);
	   		//insert the node (like a normal binary tree insert)
	   		insertInPlace(newNode);
	   		WAVLNode parent = newNode.getParent();
	   		updateSizes(newNode); //update the relevant node's sizes
	   		//we will promote the root is we insert a child directly
	   		if (parent == root)
	   		{
	   			promote(root);
	   			counter++; //rebalance operation -->counter++
	   			return counter;
	   		}
	   		else if (parent.getRank()!=0)
	   		{
	   			return counter; //no rebalance operation --> counter = 0
	   		}
	   		else if (parent.getRank()-newNode.getRank()==0)
	   			return rebalanceInsert(newNode); //if the new tree is not valid, make some rebalance operations
	   	}
	   		
	   return 0; //if none of these happened, just return 0
   }
   
   //in case that the tree is empty and a new node is inserted
   private void InsertEmptyTree(int k, String i)
   {
 		  root.rank = 0; 
 		  root.info = i;
 		  root.key = k;
 		  //make sure that all the "special links" are well linked
 		  root.parent = sentinel;
 		  root.leftChild = extLeaf;
 		  root.rightChild = extLeaf;
   }
   
   // inserts a new node to the tree (almost identical to a simple binary tree insert)
   private void insertInPlace(WAVLNode newNode){ 
 		 WAVLNode current = root;	 
 		 while(true)
 		 { 
 			 if (current.getKey() > newNode.getKey())
 			 { // go left
 				 if (current.getLeft() == extLeaf)
 				 { // if this is a leaf
 					 current.setLeft(newNode);
 					 newNode.setParent(current);
 					 break;
 				 }	 
 				 else
 					 current = current.getLeft(); // if we still have something on the left
 			 }
 			 if (current.getKey() < newNode.getKey())
 			 { // go right
 				 if (current.getRight() == extLeaf)
 				 {  // if this is a leaf
 					 current.setRight(newNode);
 					 newNode.setParent(current);
 					 break;
 				 }
 				 else
 					 current = current.getRight();  // if we still have something on the right
 			 }
 		 }
 		 newNode.setRank(0); //by definition, the rank of a new node will be 0
 		 //the new node is an internal leaf --> set two external leaves as its left and right children
 		 newNode.setLeft(extLeaf); 
 		 newNode.setRight(extLeaf);
 	 }

   //==================================================================
   //==============================END=================================
   //==================================================================
   
   //==================================================================
   //==========================Delete functions========================
   //==================================================================
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   //this is the general delete function. the general priciple is simple:
   //we treat directly only unary node deletion and leaf node deletion
   //if the node is none of these two -->replace with successor and then delete
   //if the new tree is not valid - make some rebalance operations
   //the returned value is the number of rebalance operations
   public int delete(int k)
   {
	   //in case that the key does not exist or the tree is empty
	  if (findNodeFromKey(k)==null || root.getRank()==-2)
		  return -1;
	  
	  WAVLNode current = findNodeFromKey(k);
	  
	  if (current==root) 
	  {
		//check for a "leaf" root and "unary" root
		  if (root.getLeft()==extLeaf && root.getRight()==extLeaf)
		  {
				root = new WAVLNode();
				root.rank = -2;
				
				
				root.leftChild = extLeaf;
				root.rightChild = extLeaf;
				root.parent = sentinel;
				
				return 0;
		  }
		  
		//if tree size = 2 and we want to delete the right child
		  if (root.getRight()!=extLeaf && root.getLeft()==extLeaf) 
		  {
			  root = current.getRight();
			  
			  root.parent = sentinel;
			  root.setSubtreeSize(0);
			  
			  return 0;
		  }
		//if tree size = 2 and we want to delete the left child
		  if (root.getRight()==extLeaf && root.getLeft()!=extLeaf)
		  {
			  root = current.getLeft();
			  
			  root.parent = sentinel;
			  root.setSubtreeSize(0);
			  
			  return 0;
		  } 
	  }
	  //if the node is note unary of leaf -->replace it with its successor
	  if (current.getRight()!=extLeaf && current.getLeft()!= extLeaf)
		  current = replaceWithSuccessor(current);
	  
	  WAVLNode parent = current.getParent();
	  //if the node is an internal leaf
	  if (current.getRight()==extLeaf && current.getLeft()== extLeaf)
		  deleteLeaf(current);
	  //if the node is an unary node
	  if (current.getRight()==extLeaf || current.getLeft()== extLeaf)
		  deleteUnary(current);
	  
	  int counter = 0; // counts number of rebalance steps
	  counter = rebalanceDelete(parent); //make rebalance operations if needed
	  return counter;
   }
   
   //if the node is a lead - just delete it and make no rebalance operations
   private void deleteLeaf(WAVLNode current)  
   {											
	   //we have to make sure that the children of the new leaf (after deletion) are external leaves.
 	  if (current.getParent().getLeft() == current)
 		  current.getParent().setLeft(extLeaf);
 	  if (current.getParent().getRight() == current)
 		 current.getParent().setRight(extLeaf);
 	  
 	  updateSizes(current.getParent());
   }
   
   //unary node is treated separately
   private void deleteUnary(WAVLNode current)
   {
	  //we have two different sub-cases:
	  //1. our unary node is a left child of its parent
 	  if (current.getParent().getLeft() == current)
 	  {
 		  if (current.getLeft() != extLeaf)
 		  {
 			  current.getParent().setLeft(current.getLeft());
 			  current.getLeft().setParent(current.getParent());
 		  }
 		  if (current.getRight() != extLeaf)
 		  {
 			  current.getParent().setLeft(current.getRight());
 			  current.getRight().setParent(current.getParent());
 		  }
 	  }
	  //2. our unary node is a right child of its parent
 	  if (current.getParent().getRight() == current)
 	  {
 		  if (current.getLeft() != extLeaf)
 		  {
 			  current.getParent().setRight(current.getLeft());
 			  current.getLeft().setParent(current.getParent());
 		  }
 		  if (current.getRight() != extLeaf)
 		  {
 			  current.getParent().setRight(current.getRight());
 			  current.getRight().setParent(current.getParent());
 		  }
 	  }
 	  //some node's sizes has changed, hence a size update is needed.
 	 updateSizes(current.getParent());
   }

   //if node is not a leaf or unary node - we will have to swap it with its successor
   //then, delete it a leaf
   private WAVLNode replaceWithSuccessor(WAVLNode current)
   { 
 	  WAVLNode origin = current;
 	  if ((current.getRight().getRank() != -1) && (current.getLeft().getRank() != -1)){
 		  current = current.getRight();
 		  //finding successor
 		  while (current.getLeft().getRank() != -1)
 			  current = current.getLeft();	  
 	  }
 	  //swap with successor
 	 swapNodes(origin, current);   
 	 

 	  return current;
   }
   //swap info and key for two given nodes - assisting function for replaceWithSuccessor 
   private void swapNodes(WAVLNode a, WAVLNode b){
 	  String s = a.getValue();
 	  int k = a.getKey();
 	  a.info = b.info;
 	  a.key = b.key;
 	  b.info = s;
 	  b.key = k;
   }
   
   
   //==================================================================
   //==============================END=================================
   //==================================================================
   
   //==================================================================
   //=========================Rebalance functions======================
   //==================================================================
   
   //after delete and insert operations, we have to go from a specific node up to the leaf, 
   //and update the size of all the node on that route.
   private void updateSizes(WAVLNode node)
   {
	 //we stop when a sentinel is encountered
	 //it means that we have already updated to root's size.
	   while (node != sentinel) 
		   
	   {
		   node.updateSubtreeSize();
		   node = node.getParent();
	   }
   }
   
   //increase the node's rank by 1
   private void promote(WAVLNode node)
   {
	   node.setRank(node.getRank()+1);
   }
   
   //decrease the node's rank by 1
   private void demote(WAVLNode node)
   {
	   node.setRank(node.getRank()-1);
   }
   //analyzing the type of a single node, according to rank differences between the node an its children
   // for example - "21" , means (node.rank - leftchild.rank) = 2 , (node.rank - rightchild.rank) = 1
   private String difType(WAVLNode node)
   {
	   String type = "";
	   
	   WAVLNode rightChild = node.getRight();
	   WAVLNode leftChild = node.getLeft();
	   //differences calculation
	   int rightDifRank = node.getRank()-rightChild.getRank();
	   int leftDifRank = node.getRank()-leftChild.getRank();
	   
	   type += Integer.toString(leftDifRank)+Integer.toString(rightDifRank);
	   return type;
	   
   }
   //for a specific node, we go over all the valid cases - regarding the ranks differences between the parent
   //and its children. only 12,21,11,22 node are allowed at WAVL.
   private boolean isValidWAVLNode(WAVLNode node)
   {
	   String difType = difType(node);
	   
	   if (difType.equals("12") || difType.equals("21") || difType.equals("11") || difType.equals("22"))
		   return true;
	   
	   return false; //if none of the valid case is detected, it is not a valid WAVLTree node.
   }
   //when a subtree is given (after insertion) - make some rebalance operations and count them
   //the returned value is the number of rebalancing steps
   private int rebalanceInsert(WAVLNode node)
   {
	   WAVLNode point=node;
		int counter=0;
		//in this case - make promote operation, then roll the problem up
		while( ((point!=root)) && ( (difType(point.getParent()).equals("01") )||( difType(point.getParent()).equals("10")))) 
		{
			promote(point.getParent());
			point=point.getParent();
			counter++;
		}//if problem is rolled up and now it is solved - it is a terminal case
		if((point==root)||(isValidWAVLNode((point.getParent()))))
		{
			return counter;
		}
		else	
		{
			while(point!=root)
			{
				//when parent is 02 node and the child is 12 node - rotation is terminal case
				if((difType(point.getParent()).equals("02")) & (difType(point).equals("12")))
				{
					point= rotateRight(point.getParent());
					counter+=1;
					return counter;
				}
				// symmetic case
				else if(difType(point.getParent()).equals("20") & difType(point).equals("12")) 
				{
					point= rotateRightLeft(point.getParent());	
					counter+=2;
					return counter;
				}
				//when parent is 02 node and the child is 21 node - double rotation is terminal case
				else if(difType(point.getParent()).equals("02") & difType(point).equals("21"))
				{
					point = rotateLeftRight(point.getParent());
					counter+=2;   //2 rotates, without- 2 demotes, 1 promote
					return counter;
				}
				// symmetric case
				else if(difType(point.getParent()).equals("20") & difType(point).equals("21")) 
				{
					point= rotateLeft(point.getParent());		
					counter+=1;  //1 rotate, without- 1 demote
					return counter;
				}
			}
			return counter;
		}
   }
   //when a subtree is given (after deletion) - make some rebalance operations and count them
   //the returned value is the number of rebalancing steps
   private int rebalanceDelete(WAVLNode current)
   {
	   int counter = 0;
	   //keep running on tree nodes until reaching a terminal case
	   while (true)
	   {	//current node is a "22" leaf
		    if((difType(current).equals("22")) && (current.getRight()==extLeaf) && (current.getLeft()==extLeaf))
			{
				demote(current);
				if(current==root)
					return counter++;
				else
				{
					current = current.getParent();
					counter++;
					continue;
				}
				
			}
		    	// if the current node is valid - there is nothing else to do
			 if(isValidWAVLNode(current)) 
				 return counter;
			  //in case of "23" or "32" node - demote current node and the problem might be rolled up
			  if((difType(current).equals("32"))||(difType(current).equals("23")))
			  {
				  demote(current);
				  if(current==root)
						return counter++;
					else
					{
						current = current.getParent();
						counter++;
						continue;
					}
			  }
			  //in case of "31" node and a "22" right child - perform double demote the problem might be rolled up
			  if((difType(current).equals("31"))&&(difType(current.getRight()).equals("22")))
			  {
				  demote(current);
				  demote(current.getRight());
				  if(current==root)
						return counter+2;
					else
					{
						current = current.getParent();
						counter+=2;
						continue;
					}
			  }
			//symmetric case
			  if((difType(current).equals("13"))&&(difType(current.getLeft()).equals("22")))
			  {
				  demote(current);
				  demote(current.getLeft());
				  if(current==root)
						return counter+2;
					else
					{
						current = current.getParent();
						counter+=2;
						continue;
					}
			  }
			//in case of "31" node and a "21" right child - make a rotation to the left and it is a terminal case
			  if((difType(current).equals("31"))&&((difType(current.getRight()).equals("21"))||(difType(current.getRight()).equals("11"))))	// if 3-1&&R$-1 RL ret
			  {
				  current = rotateLeft(current);
				  promote(current);
				  WAVLNode temp=current.getLeft();
				  if((difType(temp).equals("22")) && (temp.getRight()==extLeaf) && (temp.getLeft()==extLeaf))
				  {
					  demote(temp);
				  }
				  
					  
				  counter++;   
				  return counter;
			  }  
			//symmetric case
			  if((difType(current).equals("13"))&&((difType(current.getLeft()).equals("12"))||(difType(current.getLeft()).equals("11"))))	  // if 1-3&&L1-$ RR ret symetric
			  {
				  current = rotateRight(current);
				  promote(current);
				  WAVLNode temp=current.getRight();
				  if((difType(temp).equals("22")) && (temp.getRight()==extLeaf) && (temp.getLeft()==extLeaf))
				  {
					  demote(temp);
				  }
				  
				  counter++;  
				  return counter;
			  } 
			//in case of "31" node and a "12" right child - make a double rotation, and it is a terminal case
			  if((difType(current).equals("31"))&&(difType(current.getRight()).equals("12"))) 
			  {
				  demote(current);
				  current= rotateRightLeft(current);
				  promote(current);			  
				  counter+=2;   
				  return counter;
			  }  
			//symmetric case
			  if((difType(current).equals("13"))&&(difType(current.getLeft()).equals("21")))
			  {
				  demote(current);
				  current= rotateLeftRight(current);
				  promote(current);		
				  counter+=2;  
				  return counter;
			  }  
	   }
   }
   //rotating a subtree to the right
   private WAVLNode rotateRight(WAVLNode node)
   { 
	   boolean isRoot = false;
	   WAVLNode subTreeParent = null;
	   //indicates if we are dealing with root. in case of root,
	   //the node that going to represent the new subtree root will be the the tree's root.
	   if (node == root)
		   isRoot = true;
	   else
		   subTreeParent = node.getParent();
	   
	   WAVLNode y = node;
	   WAVLNode x = y.getLeft();
	   WAVLNode b = x.getRight();
	   
	   y.setLeft(b);
	   x.setRight(y);
	   demote(y);
	   
	   if(y.getLeft().getParent() != extLeaf)
		   y.getLeft().setParent(y);
	   //make sure that parent hirarchy after rotation is correct
	   subTreeParent = y.getParent();
	   x.setParent(subTreeParent);
	   y.setParent(x);
	   //"is root case' - make sure the root of the tree is the current subtree, and the sentinel is the parent
	   if(isRoot)
	   {
		   root = x;
		   root.setParent(sentinel);
	   }
	   else
	   {
			 if (subTreeParent.getLeft() == y)
				 subTreeParent.setLeft(x);
			 if (subTreeParent.getRight() == y)
				 subTreeParent.setRight(x);
	   }
	   
	   y.updateSubtreeSize();
	   x.updateSubtreeSize();
	   return x;

   }
   //symmetric to rotateRight
   private WAVLNode rotateLeft(WAVLNode node)
   {
	   boolean isRoot = false;
	   WAVLNode subTreeParent = null;
	   
	   if (node == root)
		   isRoot = true;
	   else
		   subTreeParent = node.getParent();
	   
	   WAVLNode y = node;
	   WAVLNode x = y.getRight();
	   WAVLNode b = x.getLeft();
	   
	   y.setRight(b);
	   x.setLeft(y);
	   demote(y);
	   
	   if(y.getRight().getParent() != extLeaf)
		   y.getRight().setParent(y);
	   
	   subTreeParent = y.getParent();
	   x.setParent(subTreeParent);
	   y.setParent(x);
	   
	   if(isRoot)
	   {
		   root = x;
		   root.setParent(sentinel);
	   }
	   else
	   {
			 if (subTreeParent.getRight() == y)
				 subTreeParent.setRight(x);
			 if (subTreeParent.getLeft() == y)
				 subTreeParent.setLeft(x);
	   }

	   y.updateSubtreeSize();
	   x.updateSubtreeSize();
	   return x;
	   
   
   }
   
   //double rotation - the minor rotation is a left rotation, and the major one is a right rotation
   private WAVLNode rotateLeftRight(WAVLNode node)
   {
	   boolean isRoot = false; 
	   if (node == root)
	   {
		 //indicates if we are dealing with root. in case of root,
		 //the node that going to represent the new subtree root will be the the tree's root
		   isRoot = true; 
		   
	   }
	   //we have to remember the original parent of the subtree before we rotate.
	   WAVLNode originalParent = node.getParent();
	   //minor rotation to the left
	   node.setLeft(rotateLeft(node.getLeft()));
	   //major rotation to the right
	   WAVLNode newNode = (rotateRight(node));
	   //place the new subtree to be the correct child of the original parent
	   if (originalParent.getLeft() == node)
		   originalParent.setLeft(newNode);
	   if (originalParent.getRight() == node)
		   originalParent.setRight(newNode);
	   
	   newNode.setParent(originalParent);
	   promote(newNode);
	   //dealing with the "is root case"
	   if (isRoot)
		   root = newNode;
	   
	   return newNode;
   }
   
   //symmetric to rotateLeftRight
   private WAVLNode rotateRightLeft(WAVLNode node)
   {
	   boolean isRoot = false;
	   
	   if (node == root)
	   {
		   isRoot = true;
	   }
	   
	   WAVLNode originalParent = node.getParent();
	   node.setRight(rotateRight(node.getRight()));
	   WAVLNode newNode = (rotateLeft(node));
	   
	   if (originalParent.getLeft() == node)
		   originalParent.setLeft(newNode);
	   if (originalParent.getRight() == node)
		   originalParent.setRight(newNode);
	   
	   newNode.setParent(originalParent);
	   promote(newNode);
	   if (isRoot)
		   root = newNode;
	   return newNode;
   }


   //==================================================================
   //==============================END=================================
   //==================================================================
   
   //==================================================================
   //==================Node Interface and Implementation===============
   //==================================================================
	/**
	   * public interface IWAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IWAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public IWAVLNode getLeft(); //returns left child (if there is no left child return null)
		public IWAVLNode getRight(); //returns right child (if there is no right child return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a sentinal)
		public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be implemented in O(1))
	}

   /**
   * public class WAVLNode
   *
   * If you wish to implement classes other than WAVLTree
   * (for example WAVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IWAVLNode)
   */
  public class WAVLNode implements IWAVLNode{
	    //WAVLNode's properties
	  	private int key;
	  	private String info;
	  	private int rank;
	  	private WAVLNode leftChild = null;
	  	private WAVLNode rightChild = null;
	  	private WAVLNode parent = null;
	  	private int subTreeSize; //subtree size of the current node (including the root)
	  	
	  	public WAVLNode ()
	  	{

	  	}
	  	//constructor of WAVLNode
	  	public WAVLNode(int key, String info)
	  	{
	  		//insertion of key, info and rank (rank is 0 because it is going to be an internal leaf)
	  		this.key = key;
	  		this.info = info;
	  		this.rank=0;
	  		//intern leaf will always have two external leaves as children.
	  		this.leftChild = extLeaf;
	  		this.rightChild = extLeaf;
	  	}
	  //get the node's key
		public int getKey()
		{
			if (rank!=-1)
				return key;
			
			return -1;
		}
		//get the node's info
		public String getValue()
		{
			if (rank!=-1)
				return info;
			
			return null;
			
		}
		//get the left child of this node
		public WAVLNode getLeft()
		{
			return leftChild;
		}
		//get the right child of this node
		public WAVLNode getRight()
		{
			return rightChild;
		}
		//get the parent of this node
		public WAVLNode getParent()
		{
			return parent;
		}
		//get the rank of this node
		public int getRank()
		{
			return rank;
		}
		//link new left child for this node
		public void setLeft(WAVLNode leftChild)
		{
			this.leftChild = leftChild;
		}
		//link new right child for this node
		public void setRight(WAVLNode rightChild)
		{
			this.rightChild = rightChild;
		}
		//link a new parent for this node
		public void setParent(WAVLNode parent)
		{
			this.parent = parent;
		}
		//set a rank of this node
		public void setRank(int rank)
		{
			this.rank = rank;
		}
		
		// Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a sentinel)
		public boolean isRealNode()
		{
			return (rank>-1);
		}
		//return the subtree size of this node
		public int getSubtreeSize()
		{
			return subTreeSize;
		}
		//set subtree size of this node
		public void setSubtreeSize(int size)
		{
			this.subTreeSize = size;
		}
		
		//right subtree size + left subtree size + 1
		public void updateSubtreeSize()
		{
			if (this.rank!=-1) 
				this.subTreeSize = this.getLeft().subTreeSize+this.getRight().subTreeSize + 1;   
		}
  }
}

