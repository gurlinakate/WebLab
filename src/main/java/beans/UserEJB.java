package beans;

import elements.Author;
import elements.Book;
import user.ClientClass;
import user.User;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Stateless
public class UserEJB {
    private HttpSession session;
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }


    public UserEJB() {
        this.updateSession();
    }


    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
    }


    public HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getResponse();
    }



    public User getUser() {
        if (session != null) {
            return (User) session.getAttribute("user");
        } else return null;
    }


    public void setUser(User user) {
        session.setAttribute("user", user);
    }


    public User validateUserLogin(String login, String password) throws SQLException, ClassNotFoundException {
        User user = new ClientClass().getUser(login, password);
        this.setUser(user);
        return user;
    }


    public List<Author> getUsersBook() throws SQLException, ClassNotFoundException {
        User user = (User) session.getAttribute("user");
        return new ClientClass().getUsersBook(user);
    }


    public void addBook(Book book, int userId) throws SQLException, ClassNotFoundException {
        new ClientClass().addBook(book, userId);
    }


    public void updateSession() {
        session = (HttpSession) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(false);
    }


    public void invalidateSession() {
        this.updateSession();
        this.getSession().invalidate();
    }


    public void setUserId(String author) {
        session.setAttribute("userId", author);
    }


    public String getUserId() {
        if (session != null) {
            return (String) session.getAttribute("userId");
        } else return null;
    }


    public String getUserDataXMLRepresentation() {
        User user = getUser();
        try {
            user.setBooks(getUsersBook());
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(getUser(), writer);
            return writer.toString();
        } catch (ClassNotFoundException | SQLException | JAXBException e) {
            e.printStackTrace();
            return "";
        }
    }
}
