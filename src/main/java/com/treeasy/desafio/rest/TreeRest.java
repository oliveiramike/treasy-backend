package com.treeasy.desafio.rest;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.ResponseWrapper;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.treeasy.desafio.entity.Tree;
import com.treeasy.desafio.persistence.PersistEngine;
import com.treeasy.desafio.utils.TreeUtils;

@Path("tree")
public class TreeRest {
	@POST
	@Path("/createNode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseWrapper
	public HashMap<String, Integer> createTree (String json)
	{
		HashMap<String, Integer> map = new HashMap<>();
		PersistEngine engine = new PersistEngine();
		
		Gson gson = new Gson();
		Tree tree = gson.fromJson(json, Tree.class); 
		
		engine.persist(tree);
		
		map.put("id", tree.getId());
		
		return map;	
	}
	
	@PUT
	@Path("/updateNode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseWrapper
	public HashMap<String, Integer> updateNode (String json)
	{
		HashMap<String, Integer> map = new HashMap<>();
		try
		{
			PersistEngine engine = new PersistEngine();
			
			Gson gson = new Gson();
			Tree tree = gson.fromJson(json, Tree.class); 
			
			if (engine.getSingleNode(tree.getId()) != null)
			{
				engine.update(tree);
			}
			else
			{
				throw new Exception("Este objeto não existe no banco de dados!");
			}
			
			map.put("id", tree.getId());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return map;
	}
	
	@GET
	@Path("/getTree")
	@Produces(MediaType.APPLICATION_JSON)
	public String TreeInfo ()
	{
		JSONArray array = new JSONArray();
		PersistEngine engine = new PersistEngine();
		
		List<Tree> trees = engine.getTree();
		
		for (Tree tree : trees)
		{
			if (tree.getParentId() == 0)
			{
				array.put(TreeUtils.parseTree(tree));
			}
		}
		
		return array.toString();	
	}
	
	@GET
	@Path("/getChildren/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String TreeInfo (@PathParam("id") int id)
	{
		JSONArray array = null;
		
		try
		{
			PersistEngine engine = new PersistEngine();
			
			Tree tree = engine.getSingleNode(id);
			
			if (tree != null)
			{
				array = TreeUtils.parseChildren(tree);
			}
			else
			{
				throw new Exception("Este objeto não existe no banco de dados!");
			}
		}
		catch (Exception e) {
			
		}
		
		return array.toString();	
	}
}
