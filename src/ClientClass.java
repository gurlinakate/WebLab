import java.sql.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class Constants {
    public static final String authorName = new String(new String("Granzhe"));
    public static final Author name = new Author(authorName);
    public static final User user = new User(1, "Anna", "qwerty", "12345");
    public static final Book book = new Book("Last_hunter", 2020, 600, 499);
}

public class ClientClass {
    String url = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "megapassword";

    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

        public void createTables() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
            String sqlCommand = "CREATE TABLE customer (Id INT PRIMARY KEY AUTO_INCREMENT not null, name VARCHAR(20) not null, " +
                    "login VARCHAR(20) not null, password VARCHAR(20) not null)";
            String sqlCommand2 = "CREATE TABLE book (title  VARCHAR(20) PRIMARY KEY not null, year INT, pages INT, price INT)";
            String sqlCommand3 = "CREATE TABLE author (Id INT not null, authorName VARCHAR(20) PRIMARY KEY)";
            String sqlCommand4 = "CREATE TABLE orderBook (authorName  VARCHAR(20) PRIMARY KEY not null, title VARCHAR(20) not null)";
            statement.execute(sqlCommand);
            statement.execute(sqlCommand2);
            statement.execute(sqlCommand3);
            statement.execute(sqlCommand4);
            System.out.println("the tables have been created");
            statement.close();
            connection.close();
        }

        public void filltables()throws SQLException, ClassNotFoundException{
        Connection connection = this.connection();
        //Statement statement = connection.createStatement();
        String rows = "INSERT customer(id, name, login, password) VALUES (?,?,?,?)";
        String rows2 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";
        String rows3 = "INSERT author(id , authorName) VALUES (?,?)";
        String rows4 = "INSERT orderBook(authorName, title) VALUES (?,?)";
        /*(1, 'Anna', 'qwerty', '12345')," +
                    "(2, 'Petr', 'qwertyuiop', '098765'), (3, 'Kate', 'asdfgh', '456789'), (4, 'Yana', 'dffff', '0000'), (5, 'Egor', 'pppp', '6666')");*/
       /* //int rows2 = statement.executeUpdate("INSERT book(title, year, pages, price) VALUES ('Last_hunter', 2020, 600, 499 )," +
                        "('Korporatsia_geniev', 2019, 344, 644), ('Teremok', 2020, 900, 1440), " +
                "('Postscript',  2020, 256, 359)");
        //int rows3 = statement.executeUpdate("INSERT author(id , authorName) VALUES (1, 'Granzhe')," +
                    "(2, 'Ed Ketmel'), ( 3, 'Tolstoi A.N'), (4, 'Ahern Sesiliya')");
        //int rows4 = statement.executeUpdate("INSERT orderBook(authorName, title) VALUES ('Granzhe', 'Last_hunter')," +
                    "('Ed Ketmel','Korporatsia_geniev' ), ('Tolstoi A.N','Teremok' ), ('Ahern Sesiliya','Postscript' )");*/
        PreparedStatement preparedStatement = connection.prepareStatement(rows);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Anna");
        preparedStatement.setString(3, "qwerty");
        preparedStatement.setString(4, "12345");
        /*preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "Petr");
        preparedStatement.setString(3, "qwertyuiop");
        preparedStatement.setString(4, "098765");
        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, "Kate");
        preparedStatement.setString(3, "asdfgh");
        preparedStatement.setString(4, "456789");*/

        PreparedStatement preparedStatement2 = connection.prepareStatement(rows2);
            preparedStatement2.setString(1, "Last_hunter");
            preparedStatement2.setInt(2, 2020);
            preparedStatement2.setInt(3, 600);
            preparedStatement2.setInt(4, 499);
            /*preparedStatement2.setString(1, "Korporatsia_geniev");
            preparedStatement2.setInt(2, 2019);
            preparedStatement2.setInt(3, 344);
            preparedStatement2.setInt(4, 644);
            preparedStatement2.setString(1, "Teremok");
            preparedStatement2.setInt(2, 2020);
            preparedStatement2.setInt(3, 222);
            preparedStatement2.setInt(4, 644);
            preparedStatement2.setString(1, "Postscript");
            preparedStatement2.setInt(2, 2002);
            preparedStatement2.setInt(3, 212);
            preparedStatement2.setInt(4, 1000);*/

        PreparedStatement preparedStatement3 = connection.prepareStatement(rows3);
            preparedStatement3.setInt(1, 1);
            preparedStatement3.setString(2, "Granzhe");
           /* preparedStatement3.setInt(1, 2);
            preparedStatement3.setString(2, "Ed Ketmel");
            preparedStatement3.setInt(1, 3);
            preparedStatement3.setString(2, "Tolstoi A.N");*/

        PreparedStatement preparedStatement4 = connection.prepareStatement(rows4);
            preparedStatement4.setString(1, "Granzhe");
            preparedStatement4.setString(2, "Last_hunter");
            /*preparedStatement4.setString(1, "Ed Ketmel");
            preparedStatement4.setString(2, "Korporatsia_geniev");
            preparedStatement4.setString(1, "Tolstoi A.N");
            preparedStatement4.setString(2, "Teremok");*/

        int r = preparedStatement.executeUpdate();
        int r2 = preparedStatement2.executeUpdate();
        int r3 = preparedStatement3.executeUpdate();
        int r4 = preparedStatement4.executeUpdate();

        System.out.printf("Added %d rows in table /customer/", r );
        System.out.printf("\n"+" Added %d rows in table /book/", r2);
        System.out.printf("\n"+" Added %d rows in table /author/", r3);
        System.out.printf("\n" +" Added %d rows in table /orderBook/", r4);
        connection.close();
        }

        public void addUser() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
            System.out.print("Input name customer: ");
            String name = scanner.nextLine();
            System.out.print("Input login customer: ");
            String login = scanner.nextLine();
            System.out.print("Input password customer: ");
            String password = scanner.nextLine();
            System.out.print("Input id customer: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            String rows = "INSERT customer(id, name, login, password) VALUES (?,?,?,?)";
            String rows2 = "INSERT author(id, authorName) VALUES (?,?)";
            String rows3 = "INSERT orderBook(authorName, title) VALUES (?,?)";
            String rows4 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(rows);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, password);

            System.out.print("Input book title: ");
            String title = scanner.nextLine();
            System.out.print("Input book author: ");
            String author = scanner.nextLine();
            System.out.print("Input year: ");
            int year = scanner.nextInt();
            System.out.print("Input number of pages: ");
            int page = scanner.nextInt();
            System.out.print("Input price: ");
            int price = scanner.nextInt();
            PreparedStatement preparedStatement2 = connection.prepareStatement(rows4);
            preparedStatement2.setString(1, title);
            preparedStatement2.setInt(2, year);
            preparedStatement2.setInt(3, page);
            preparedStatement2.setInt(4, price);

            PreparedStatement preparedStatement3 = connection.prepareStatement(rows2);
            preparedStatement3.setInt(1, id);
            preparedStatement3.setString(2, author);

            PreparedStatement preparedStatement4 = connection.prepareStatement(rows3);
            preparedStatement4.setString(1, author);
            preparedStatement4.setString(2, title);

            int r = preparedStatement.executeUpdate();
            int r2 = preparedStatement2.executeUpdate();
            int r3 = preparedStatement3.executeUpdate();
            int r4 = preparedStatement4.executeUpdate();
        System.out.printf("Added %d rows in table /customer/", r+ "\n");
        System.out.printf("Added %d rows in table /book/", r2+ "\n");
        System.out.printf("Added %d rows in table /author/", r3+ "\n");
        System.out.printf("Added %d rows in table /orderBook/", r4+ "\n");
        statement.close();
        connection.close();
        }

        public void addAuthor() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
            System.out.print("Input book title: ");
            String title = scanner.nextLine();
            System.out.print("Input book author: ");
            String author = scanner.nextLine();
            System.out.print("Input year: ");
            int year = scanner.nextInt();
            System.out.print("Input number of pages: ");
            int page = scanner.nextInt();
            System.out.print("Input price: ");
            int price = scanner.nextInt();
            System.out.print("Input id customer: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            String rows2 = "INSERT author(id, authorName) VALUES (?,?)";
            String rows3 = "INSERT orderBook(authorName, title) VALUES (?,?)";
            String rows4 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";

            PreparedStatement preparedStatement2 = connection.prepareStatement(rows4);
            preparedStatement2.setString(1, title);
            preparedStatement2.setInt(2, year);
            preparedStatement2.setInt(3, page);
            preparedStatement2.setInt(4, price);

            PreparedStatement preparedStatement3 = connection.prepareStatement(rows2);
            preparedStatement3.setInt(1, id);
            preparedStatement3.setString(2, author);

            PreparedStatement preparedStatement4 = connection.prepareStatement(rows3);
            preparedStatement4.setString(1, author);
            preparedStatement4.setString(2, title);

            int r2 = preparedStatement2.executeUpdate();
            int r3 = preparedStatement3.executeUpdate();
            int r4 = preparedStatement4.executeUpdate();

        System.out.printf("Added %d rows in table /book/", r2+ "\n");
        System.out.printf("Added %d rows in table /author/", r3+ "\n");
        System.out.printf("Added %d rows in table /orderBook/", r4+ "\n");
        statement.close();
        connection.close();
        }

        public void addBook() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input book title: ");
        String title = scanner.nextLine();
        System.out.print("Input book author: ");
        String author = scanner.nextLine();
        System.out.print("Input year: ");
        int year = scanner.nextInt();
        System.out.print("Input number of pages: ");
        int page = scanner.nextInt();
        System.out.print("Input price: ");
        int price = scanner.nextInt();
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        String rows4 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";

        PreparedStatement preparedStatement2 = connection.prepareStatement(rows4);
        preparedStatement2.setString(1, title);
        preparedStatement2.setInt(2, year);
        preparedStatement2.setInt(3, page);
        preparedStatement2.setInt(4, price);

        int r2 = preparedStatement2.executeUpdate();

        System.out.printf("Added %d rows in table /book/", r2 +"\n");
        statement.close();
        connection.close();
        }

        public void deleteBook()throws SQLException, ClassNotFoundException{
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
        System.out.print("Input year: ");
        int year = scanner.nextInt();
        System.out.print("Input price: ");
        int price = scanner.nextInt();
        //first operation
        connection.setAutoCommit(false);
        try {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE year = ?");
        preparedStatement.setInt(1, year);
        int rows = preparedStatement.executeUpdate();
        System.out.printf("Deleted book with year " + year + "\n");
        //second operation
        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM book WHERE price = ?");
        preparedStatement2.setInt(1, price);
        int rows2 = preparedStatement2.executeUpdate();
        System.out.printf("Deleted book with price " + price + "\n");
        connection.commit();}
        catch(SQLException e){
            connection.rollback();
        }
        connection.close();
        }

        public boolean checkUsers() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE login = ? AND password = ?");
        statement.setString(1, Constants.user.getLogin());
        statement.setString(2, Constants.user.getPassword());
        ResultSet check = statement.executeQuery();
        boolean user = check.next();
        connection.close();
        return user;
        }

    public Author getBookByAuthorId(int id) throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE title IN(SELECT title FROM orderbook WHERE authorName in" +
                "(SELECT authorName FROM " + "author WHERE id = ?))" );
        statement.setObject(1, Constants.user.getId());
        ResultSet resultSet = statement.executeQuery();
        Author aut = new Author();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            double price = resultSet.getDouble(4);
            System.out.printf("title:"+String.valueOf(title)+ " ");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " ");
            System.out.printf("pages:"+String.valueOf(pages)+ " ");
            System.out.printf("price:"+String.valueOf(price)+ " ");
            aut.add(new Book(title,year,pages,price));
        }
        connection.close();
        return aut;
    }

        public List<Author> getUsersBook(User user) throws SQLException, ClassNotFoundException {
        int userid = user.getId();
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM author WHERE id = ?) ");
        statement.setObject(1, userid );
        ResultSet resultSet = statement.executeQuery();
        List<Author> books = new LinkedList<>();
        while (resultSet.next()) {
            getBookByAuthorId(resultSet.getInt(1));
            books.add(this.getBookByAuthorId(resultSet.getInt(1)));
        }
        connection.close();
        return books;
        }

        public List<Book> selectAll() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM book");
        ResultSet resultSet = statement.executeQuery();
        List<Book> b = new LinkedList<>();
        while(resultSet.next()){
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            int price = resultSet.getInt(4);
            System.out.printf("title:"+String.valueOf(title)+ " "+"\n");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " "+"\n");
            System.out.printf("pages:"+String.valueOf(pages)+ " "+"\n");
            System.out.printf("price:"+String.valueOf(price)+ " "+"\n");
            System.out.println();
            b.add(new Book(title,year,pages,price));
            }
        connection.close();
        return b;
        }

        public List<User> getUsersByBook() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE id IN(SELECT id FROM author WHERE authorName IN(" +
                "SELECT authorName FROM orderBook WHERE title = ?))");
        statement.setObject(1, Constants.book.getTitle());
        ResultSet resultSet = statement.executeQuery();
        List<User> b = new LinkedList<>();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                System.out.printf("id:"+ String.valueOf(id)+ " " +"\n");
                System.out.printf("name:"+String.valueOf(name)+ " "+"\n");
                System.out.printf("login:"+String.valueOf(login)+ " "+"\n");
                System.out.printf("password:"+String.valueOf(password)+ " "+"\n");
                b.add(new User(id,name,login,password));
            }
            connection.close();
            return b;
        }

    public static void main(String ...args) throws SQLException, ClassNotFoundException {
        ClientClass helper = new ClientClass();
        //helper.createTables(); //создание таблиц
        //helper.filltables(); // заполнение таблиц
        helper.deleteBook(); // удаление элементов таблицы
        //helper.addUser();
        //helper.addAuthor();
        //helper.addBook();

        //boolean check = helper.checkUsers(); //проверка наличия пользователя
        //System.out.println(check);

        //List<Author> a = helper.getUsersBook(Constants.user); // вывод всех книг пользователя
        //System.out.println(a);

        //Author a1 = helper.getBookByAuthorId(Constants.user.getId()); //вывод книг по заданному Id
        //System.out.println(a1);

        //List<Book> a2 = helper.selectAll(); // вывод содержимого списка Book
        //System.out.println(a2);

        //List<User> a3 = helper.getUsersByBook(); //вывод пользователя по заданной книге
        //System.out.println(a3);
    }
}
