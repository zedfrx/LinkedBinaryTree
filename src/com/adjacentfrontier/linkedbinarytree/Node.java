package com.adjacentfrontier.linkedbinarytree;

public class Node {

	private int data;
	
	public Node(int data) {
		this.data = data;
	}
	
	public String displayNodeData() {
		return "{" + data + "}";
	}
	
	public int getNodeData() {
		return this.data;
	}

}
