package hunt.webserver;

public class Book {

	public Book(String author, String title)
	{
		this.setAuthor(author);
		this.setTitle(title);
	}
	
	private String author;
	private String title;
	
	public String getAuthor() { return this.author; }
	public String getTitle() { return this.title; }
	
	public void setAuthor(String author) { this.author = author; }
	public void setTitle(String title) { this.title = title; }
	
}
