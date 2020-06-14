package user;

import elements.Author;

import java.io.IOException;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlType
@XmlRootElement(name = "user")
public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private List<Author> book;

    public User() {
    }

    public User(int id, String name, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.book = new ArrayList<>();
    }



    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    @XmlElement(name = "login")
    public String getLogin() {
        return login;
    }

    @XmlElement(name = "name")
    public String getName() {
        return this.name;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    @XmlElementWrapper(nillable = true, name = "book")
    @XmlElement(name = "author")
    public List<Author> getBooks() {
        return this.book;
    }

    public void setBooks(List<Author> book) {
        this.book = book;
    }

    public void addBook(Author book) throws IOException, ClassNotFoundException {
        this.book.add(book);
    }

    public void deleteBook(Author book) throws IOException, ClassNotFoundException {
        this.book.remove(book);
    }

    public int book_size(){
        return this.book.size();
    }


    @Override
    public boolean equals(Object obj) {
        User person2 = (User) obj;
        return this.getName().equals(person2.getName()) &&
                this.getId()==person2.getId() &&
                this.getLogin().equals(person2.getLogin()) &&
                this.getPassword().equals(person2.getPassword());
    }}