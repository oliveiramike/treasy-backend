package com.treeasy.desafio.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.treeasy.desafio.entity.Tree;

public class PersistEngine {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public PersistEngine()
	{
		emf = Persistence.createEntityManagerFactory("treeasy");
		em = emf.createEntityManager();
	}
	
	public void persist(Tree tree)
	{
		em.getTransaction().begin();
		em.persist(tree);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void update(Tree tree)
	{
		em.getTransaction().begin();
		em.merge(tree);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remove(Integer id)
	{
		Tree tree;
		
		em.getTransaction().begin();
		Query query = em.createQuery("select tree from Tree tree where tree.id = " + id);
		tree = (Tree) query.getSingleResult();
		em.remove(tree);
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tree> getTree ()
	{
		em.getTransaction().begin();
		Query query = em.createQuery("select tree from Tree tree");
		List<Tree> trees = query.getResultList();
		em.getTransaction().commit();
		emf.close();
		
		return trees;
	}
	
	@SuppressWarnings("unchecked")
	public Tree getSingleNode (int id)
	{
		em.getTransaction().begin();
		Query query = em.createQuery("select tree from Tree tree where tree.id = " + id);
		Tree tree = (Tree) query.getSingleResult();
		em.getTransaction().commit();
		
		return tree;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tree> getChildren (int id)
	{
		em.getTransaction().begin();
		Query query = em.createQuery("select tree from Tree tree where tree.parentId = " + id);
		List<Tree> trees = query.getResultList();
		em.getTransaction().commit();
		
		return trees;
	}
}