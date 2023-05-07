import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Book> books;
    Connection con;


    public Catalog() {
        books = new ArrayList<>();
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        books.add(book);
        String sql = "INSERT INTO books (title, author, genre, is_available) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setBoolean(4, book.isAvailable());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into the table.");
            }
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
        String sql = "DELETE FROM books WHERE title = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The book was deleted from the table.");
            }
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
//        for (Book book : books) {
//            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())
//                    || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
//                result.add(book);
//            }
//        }
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "%" + keyword.toLowerCase() + "%");
            statement.setString(2, "%" + keyword.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                boolean isAvailable = resultSet.getBoolean("is_available");
                Book book = new Book(title, author, genre);
                book.setAvailable(isAvailable);
                result.add(book);
            }
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> getAllBooks() {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                boolean isAvailable = resultSet.getBoolean("is_available");
                Book book = new Book(title, author, genre);
                book.setAvailable(isAvailable);
                result.add(book);
            }
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
        return result;
    }

    public void updateBook(Book book, String title, String author, String genre, boolean isAvailable) {

        String sql = "UPDATE books SET title = ?, author = ?, genre = ?, is_available = ? WHERE title = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, genre);
            statement.setBoolean(4, isAvailable);
            statement.setString(5, book.getTitle());
            statement.executeUpdate();
            System.out.println("Book record updated successfully.");
        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while updating book record.");
            e.printStackTrace();
        }
    }
}
