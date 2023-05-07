import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Borrower {
    private String name;

    private List<BorrowedBook> borrowedBooks;
    Connection con;
 
    public Borrower(String name) {
        this.name = name;
        borrowedBooks = new ArrayList<>();
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
            String sql = "INSERT INTO `borrowers` (`name`) VALUES (?);";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Borrower was inserted into the databases.");
            }
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book, LocalDate dueDate) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            String sql = "UPDATE `books` SET `is_available` = '0' WHERE `title` = ?;";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, book.getTitle());
                statement.executeUpdate();
                borrowedBooks.add(new BorrowedBook(book, this, dueDate));
            }
            catch (Exception e)
            {
                System.out.println("Oops, something went wrong!");
                e.printStackTrace();
            }

            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Sorry, the book is not available for borrowing.");
        }
    }

    public void returnBook(BorrowedBook borrowedBook) {
        Book book = borrowedBook.getBook();
        if (borrowedBooks.contains(borrowedBook)) {
            book.setAvailable(true);

            borrowedBooks.remove(borrowedBook);

            try {
                String sql = "DELETE FROM `borrowed_books` WHERE `borrowed_books`.`book_title` = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, book.getTitle());
                statement.executeUpdate();
                sql = "UPDATE `books` SET `is_available` = '1' WHERE `title` = ?;";
                statement = con.prepareStatement(sql);
                statement.setString(1, book.getTitle());
                statement.executeUpdate();
            }
            catch (Exception e)
            {
                System.out.println("Oops, something went wrong!");
                e.printStackTrace();
            }

            System.out.println("Book returned successfully!");
        } else {
            System.out.println("This book was not borrowed by this borrower.");
        }
    }
}
