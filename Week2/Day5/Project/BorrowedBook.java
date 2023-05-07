import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowedBook {
private Book book;
private Borrower borrower;
private LocalDate dueDate;
Connection con;
public BorrowedBook(Book book, Borrower borrower, LocalDate dueDate) {

    this.book = book;
    this.borrower = borrower;
    this.dueDate = dueDate;
    try {
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
        String sql = "INSERT INTO borrowed_books (book_title, borrower_name,  due_date) VALUES (?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, borrower.getName());
        statement.setDate(3, java.sql.Date.valueOf(dueDate));
        statement.executeUpdate();
        System.out.printf("BorrowedBook is insert into databses");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

public Book getBook() {
    return book;
}

public Borrower getBorrower() {
    return borrower;
}

public LocalDate getDueDate() {
    return dueDate;
}
}