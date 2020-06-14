package elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.util.Objects;

@XmlType
@XmlRootElement(name = "book")
public class Book implements Serializable {//book
    private static final long serialVersionUID = 1L;

    private String title;
    private int publishYear;
    private int numOfPages;
    private double price;//double

    public Book(){}

    public Book(String title, int publishYear,  int numOfPages, double price) {
        if(title != null) {
            this.title = title;
        }
        if (publishYear >= 0) {
            this.publishYear = publishYear;
        }
        if (numOfPages >= 0) {
            this.numOfPages = numOfPages;
        }
        if (price >= 0) {
            this.price = price;
        } else new IOException();
    }


    @XmlElement(name = "title")
    public String getTitle() {
        return this.title;
    }


    @XmlElement(name = "publishYear")
    public int getYear() {
        return this.publishYear;
    }

    @XmlElement(name = "numOfPages")
    public int getPage() {
        return this.numOfPages;
    }

    @XmlElement(name = "price")
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public void setPage(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public void setTitle(String title) {
        this.title = title;
    }//check


    //Write to file.
    public static void Writer(Object Author) throws IOException {
        FileOutputStream fos = new FileOutputStream("file");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Author);
        oos.close();
    }

    //Read from file.
    public static void Reader(Object Author) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("file2");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Book booksFromSavedFile = (Book) ois.readObject();
        ois.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publishYear == book.publishYear &&
                numOfPages == book.numOfPages &&
                price == book.price &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publishYear, numOfPages, price);
    }
}
