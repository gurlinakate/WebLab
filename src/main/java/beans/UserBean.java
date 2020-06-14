package beans;

import elements.Author;
import elements.Book;
import user.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {
    private User user;
    private UserEJB userEJB;
    private Book book;
    private int userId;
    private String authorn;

    public int getUserId() {
        return user.getId();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public UserEJB getUserEJB() {
        return userEJB;
    }


    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }


    public UserBean() {
        user = new User();
        userEJB = new UserEJB();
        book = new Book();
    }

    public Book getBook() {
        return book;
    }


    public void setBook(Book book) {
        this.book = book;
    }

   // public String getAuthor(){return author.getAuthor();}
   // public void setAuthor(String authorn) {
        //this.authorn = authorn;
   // }

    public String goToResultPage() throws SQLException, ClassNotFoundException {
        User userFromDB = userEJB.validateUserLogin(user.getLogin(), user.getPassword());
        if (userFromDB != null) {
            this.user = userFromDB;
            return "result";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Такого пользователя не существует"));
            return "index";
        }
    }

    public String seeXmlView() throws SQLException, ClassNotFoundException {
        return this.goToResultPage().equals("index") ? "index" : "resultXML";
    }


    public List<Author> getUsersBook() throws SQLException, ClassNotFoundException {
        return userEJB.getUsersBook();
    }


    public String logout() {
        userEJB.invalidateSession();
        return "index";
    }

    public String goToInsertPage() {
        return "insert";
    }

    public String insertAndGoToResultPage() {
        try {
            userEJB.addBook(book, userId);
            return "result";
        } catch (SQLException | ClassNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ошибка добавления"));
            return "insert";
        }
    }

    public String getResultXml() {
        return userEJB.getUserDataXMLRepresentation();
    }
}
