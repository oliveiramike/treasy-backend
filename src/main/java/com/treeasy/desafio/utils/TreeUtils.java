package com.treeasy.desafio.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.treeasy.desafio.entity.Tree;
import com.treeasy.desafio.persistence.PersistEngine;

public class TreeUtils {
	public static JSONObject parseTree (Tree tree) {
		JSONObject node = new JSONObject();
		JSONArray children = new JSONArray();
		PersistEngine engine = new PersistEngine();
		
		node.put("id", tree.getId());
		node.put("code", tree.getCode());
		node.put("description", tree.getDescription());
		node.put("parentId", tree.getParentId());
		node.put("detail", tree.getDetail());
		
		List<Tree> childrenList = engine.getChildren(tree.getId());
		
		if (childrenList.size() > 0)
		{
			for (Tree childrenTree : childrenList)
			{
				JSONObject childrenNode = parseTree(childrenTree);
				children.put(childrenNode);
			}
		}
		
		node.put("children", children);
		
		
		return node;
	}
	
	public static JSONArray parseChildren (Tree tree) {
		JSONArray array = new JSONArray();
		PersistEngine engine = new PersistEngine();
		
		List<Tree> childrenList = engine.getChildren(tree.getId());
		
		if (childrenList.size() > 0)
		{
			for (Tree childrenTree : childrenList)
			{
				JSONObject node = new JSONObject();
				node.put("id", childrenTree.getId());
				node.put("code", childrenTree.getCode());
				node.put("description", childrenTree.getDescription());
				node.put("parentId", childrenTree.getParentId());
				node.put("detail", childrenTree.getDetail());
				
				List<Tree> grandchildrenList = engine.getChildren(childrenTree.getId());
				
				if (grandchildrenList.size() > 0)
				{
					node.put("hasChildren", true);
				}
				else
				{
					node.put("hasChildren", false);
				}
				
				array.put(node);
			}
		}
		
		return array;
	}
}
