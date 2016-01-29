package com.adjacentfrontier.linkedbinarytree;

import java.util.*;

public class LinkedBinaryTreeRunner {

	/**
	 * Welcome to the superfluous Linked Binary Tree implementation. 
	 * Initial version: 2002, updated 2015. Daniel Nicholson.
	 * 
	 * The linked binary tree operates simultaneously like a linked-list and a binary tree
	 * as you create a (Node) it is referenced both by BinaryNode and LinkNode. LinkNode
	 * has pointers for previous and next where as BinaryNode has pointers for leftChild
	 * and rightChild. The linked-list structure has pointers to first and last and is
	 * unsorted when traversed. The binary tree has a pointer to root and is inorder when 
	 * traversed.  
	 * 
	 * Currently getTreeDepth doesn't work correctly, it returns to many for height 1 and
	 * 1 too few for height > 2. Additionally recursiveInsert works a treat but doesn't yet
	 * handle the linked list portion (i.e. unimplemented), use the (much clunkier)
	 * iterativeInsert for data inserts.
	 * 
	 * Could be expanded for different data inserts (modify Node), debug switch should be 
	 * used/logger implemented, etc.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LinkedBinaryTree tree = new LinkedBinaryTree();
		
		System.out.println("------------------------------------------------");
		System.out.println("------------------- Insert ---------------------");
		System.out.println("------------------------------------------------\n");
		
		Random dataInserter = new Random();
		int limit = 50;
		int range = 100;
		
		for (int i = 1; i <= limit; i++) {
			tree.iterativeInsert(dataInserter.nextInt(range));
		}
		
//		for (int i = 1; i <= limit; i++) {
//			tree.recursiveInsert(dataInserter.nextInt(range));
//		}
		
		
// balanced tree test insert
//		tree.insert(4);
//		tree.insert(2);
//		tree.insert(3);
//		tree.insert(1);
//		tree.insert(6);
//		tree.insert(5);
//		tree.insert(7);
		
		System.out.println("------------------------------------------------");
		System.out.println("-------------------- FIND ----------------------");
		System.out.println("------------------------------------------------\n");
		
		int temp = 0;
		for (int i = 1; i <= limit/4; i++) {
			temp = dataInserter.nextInt(range);
			System.out.println("Found " + temp + " ? " + tree.exists(temp).toString());
		}

		System.out.println("------------------------------------------------");
		System.out.println("------------------- PRINT ----------------------");
		System.out.println("------------------------------------------------\n");
		System.out.println("\n***# of nodes: " + tree.getNodeCount());
		System.out.println("\n***tree depth: " + tree.getTreeDepth());
		System.out.println("\n***InOrder");
		tree.printBinaryTreeInorder();
		System.out.print("\n***Linked list forward:  ");
		tree.displayLinkedList(Boolean.TRUE);
		System.out.print("\n***Linked list Backward: ");
		tree.displayLinkedList(Boolean.FALSE);
		System.out.print("\n***Tree structure:  ");
		tree.displayBinaryTreeStructure();

		System.out.println("\n------------------------------------------------");
		System.out.println("------------------- DELETE ---------------------");
		System.out.println("------------------------------------------------\n");
		
		for (int i = 1; i <= limit/4; i++) {
			tree.delete(dataInserter.nextInt(range));
		}
		
		System.out.println("------------------------------------------------");
		System.out.println("------------------- PRINT ----------------------");
		System.out.println("------------------------------------------------\n");
		System.out.println("\n***# of nodes: " + tree.getNodeCount());
		System.out.println("\n***tree depth: " + tree.getTreeDepth());
		System.out.println("\n***InOrder");
		tree.printBinaryTreeInorder();
		System.out.print("\n***Linked list forward:  ");
		tree.displayLinkedList(Boolean.TRUE);
		System.out.print("\n***Linked list Backward: ");
		tree.displayLinkedList(Boolean.FALSE);
		System.out.print("\n***Tree structure:  ");
		tree.displayBinaryTreeStructure();

	}
}
