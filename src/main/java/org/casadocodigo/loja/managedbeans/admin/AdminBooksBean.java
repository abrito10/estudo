package org.casadocodigo.loja.managedbeans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.casadocodigo.loja.daos.AuthorDAO;
import org.casadocodigo.loja.daos.BookDAO;
import org.casadocodigo.loja.infra.MessagesHelper;
import org.casadocodigo.loja.models.Author;
import org.casadocodigo.loja.models.Book;

@ManagedBean
@Model
public class AdminBooksBean {

	@Inject
	private MessagesHelper messagesHelper;
	
	@Inject
	 private AuthorDAO authorDAO;
	 private List<Author> authors = new ArrayList<Author>();
	 private List<String> selectedAuthorsIds = new ArrayList<>(); 
	 //private List<Integer> selectedAuthorsIds = new ArrayList<>();

	private Book product = new Book();

    @Inject
    private BookDAO productDAO;
 
    @Inject
    public AdminBooksBean(AuthorDAO authorDAO,BookDAO bookDAO) {
    	this.productDAO = bookDAO;
    	this.authors = authorDAO.list();
    }

    @Inject
    private FacesContext facesContext;
    
  
    @PostConstruct
    public void loadObjects(){
    	this.authors = authorDAO.list();
    }
    
   
	public List<String> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public void setSelectedAuthorsIds(List<String> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	@Transactional
     public String save(){
		populateBookAuthor();
		productDAO.save(product);
		clearObjects();
		//FacesContext facesContext =FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage("Livro gravado com sucesso"));
		return "/livros/lista?faces-redirect=true";
		
		}
	 
	private void populateBookAuthor() {
    	 selectedAuthorsIds.stream().map( (strId) -> {
    		 //return new Author(id);
  			return new Author(Integer.parseInt(strId)); 
    	 }).forEach(product :: add);
    	 }    

	
	
     public Book getProduct() {
    	 return product;
     }	 
     
    
 	public void add(Author author) {
		this.authors.add(author);
	}
 	
     public List<Author> getAuthors() {
    	 return authors;
    	 }

     public AdminBooksBean(){
    		this.authors = authorDAO.list();
    	}
  
     private void clearObjects() {   
    	 this.product = new Book();
    	 this.selectedAuthorsIds.clear();
     }
    	 
}


// public void save(){

//	 System.out.println(" saida = " + product.getId());
//	 System.out.println(" saida = " + product.getTitle());
//	 System.out.println(" saida = " + product.getPrice());
//	 System.out.println(" saida = " + product.getDescription());
//	 System.out.println(" saida = " + product.getNumberOfPages());

// }
 