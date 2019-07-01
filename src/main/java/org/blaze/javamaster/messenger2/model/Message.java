package org.blaze.javamaster.messenger2.model;


public class Message {

	private long id;
	private String message;
	private String created;
	private String author;
	
	
	public Message()
	{
		
	}
	
	public Message( String message, String created,String author ) {
		this.message = message;
		this.author = author;
		this.created = created;
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
