package com.treeasy.tests;

import com.treeasy.desafio.entity.Tree;
import com.treeasy.desafio.persistence.PersistEngine;

public class Test {

	public static void main(String[] args) {	
		PersistEngine engine = new PersistEngine();
		Tree tree = new Tree();
		tree.setId(1);
		tree.setCode("teste");
		
		engine.persist(tree);
	}
}
