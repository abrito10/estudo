package org.casadocodigo.loja.daos;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.casadocodigo.loja.models.Book;

public class BookDAO {

	@PersistenceContext
	private EntityManager manager;

	public BookDAO() {

	}

	public BookDAO(EntityManager manager) {
		super();
		this.manager = manager;
	}

	public void save(Book product) {
		manager.persist(product);
	}

	public List<Book> list() {
		return manager.createQuery("select distinct(b) from Book b join fetch b.authors",
				Book.class).getResultList();
	}

	

	public Book findById(Integer id) {
		return manager.find(Book.class, id);
	}

}