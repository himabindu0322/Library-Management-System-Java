import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class LibraryMain {

    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASSWORD = "mysql195";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Librarian Login");
            System.out.println("3. Student Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    librarianLogin();
                    break;
                case 3:
                    studentLogin();
                    break;
                case 4:
                    System.out.println("Exit...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
 // ================= ADMIN LOGIN =================
    public static void adminLogin() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Admin ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "SELECT * FROM admin WHERE admin_id=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Admin Login Successful!");
                adminMenu();
            } else {
                System.out.println("Invalid Admin Credentials!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= ADMIN MENU =================
    public static void adminMenu() {

        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Librarian");
            System.out.println("2. View Librarians");
            System.out.println("3. Delete Librarian");
            System.out.println("4. View All Students");
            System.out.println("5. View Books Report");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addLibrarian();
                    break;
                case 2:
                    viewLibrarians();
                    break;
                case 3:
                    deleteLibrarian();
                    break;
                case 4:
                    viewStudents();
                    break;
                case 5:
                    viewBooksReport();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ================= ADMIN FUNCTIONS =================
    public static void addLibrarian() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Librarian ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "INSERT INTO librarian VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);

            ps.executeUpdate();
            System.out.println("Librarian Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void viewLibrarians() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM librarian";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Librarian List ---");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getString("librarian_id") +
                        " | Name: " + rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void deleteLibrarian() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Librarian ID to Delete: ");
            String id = sc.nextLine();

            String sql = "DELETE FROM librarian WHERE librarian_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Librarian Deleted Successfully!");
            } else {
                System.out.println("Librarian ID Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void viewStudents() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM student";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Student List ---");

            while (rs.next()) {
                System.out.println(
                        "Roll No: " + rs.getString("roll_no") +
                        " | Name: " + rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= VIEW BOOKS REPORT =================
    public static void viewBooksReport() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM books";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.printf("\n%-8s %-25s %-20s %-10s %-10s%n",
                    "ID", "Title", "Author", "Price", "Qty");
            System.out.println("---------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-8d %-25s %-20s %-10.2f %-10d%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
// ================= LIBRARIAN LOGIN =================
    public static void librarianLogin() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Librarian ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "SELECT * FROM librarian WHERE librarian_id=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Librarian Login Successful!");
                librarianMenu();
            } else {
                System.out.println("Invalid Librarian Credentials!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= LIBRARIAN MENU =================
    public static void librarianMenu() {

        while (true) {
            System.out.println("\n===== LIBRARIAN MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    BookManager.addBook();
                    break;
                case 2:
                    BookManager.viewBooks();
                    break;
                case 3:
                    BookManager.searchById();
                    break;
                case 4:
                    BookManager.updateBook();
                    break;
                case 5:
                    BookManager.deleteBook();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
  // ================= STUDENT LOGIN =================
    public static void studentLogin() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Roll Number: ");
            String roll = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "SELECT * FROM student WHERE roll_no=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roll);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Student Login Successful!");
                studentMenu(roll);
            } else {
                System.out.println("Invalid Student Credentials!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= STUDENT MENU =================
    public static void studentMenu(String rollNo) {
        while (true) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book by Author");
            System.out.println("3. Search Book by Name");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Your Borrowed Books");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    searchBookByAuthor();
                    break;
                case 3:
                    searchBookByName();
                    break;
                case 4: borrowBook(rollNo); break;
                case 5: returnBook(rollNo); break;
                case 6: viewBorrowedBooks(rollNo); break;
                case 7: return;                
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
 // ================= VIEW AVAILABLE BOOKS =================
    public static void viewAllBooks() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM books WHERE quantity > 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.printf("\n%-10s %-30s %-25s %-10s%n",
                    "Book ID", "Title", "Author", "Quantity");
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-30s %-25s %-10d%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= SEARCH =================
    public static void searchBookByName() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book Name: ");
            String title = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + title + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("title") + " | " +
                        rs.getString("author") + " | Qty: " +
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void searchBookByAuthor() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            String sql = "SELECT * FROM books WHERE author LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + author + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("title") + " | " +
                        rs.getString("author") + " | Qty: " +
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= BORROW BOOK =================
    public static void borrowBook(String rollNo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            String checkSql = "SELECT quantity FROM books WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(checkSql);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next() || rs.getInt("quantity") <= 0) {
                System.out.println("Book not available!");
                return;
            }

            LocalDate takenDate = LocalDate.now();
            LocalDate returnDate = takenDate.plusDays(15);

            PreparedStatement insertPs = con.prepareStatement(
                    "INSERT INTO borrowed_books VALUES (?,?,?,?,?)");
            insertPs.setString(1, rollNo);
            insertPs.setInt(2, bookId);
            insertPs.setDate(3, Date.valueOf(takenDate));
            insertPs.setDate(4, Date.valueOf(returnDate));
            insertPs.setNull(5, java.sql.Types.DATE);
            insertPs.executeUpdate();

            PreparedStatement updatePs = con.prepareStatement(
                    "UPDATE books SET quantity = quantity - 1 WHERE book_id=?");
            updatePs.setInt(1, bookId);
            updatePs.executeUpdate();

            System.out.println("Book Borrowed! Return by: " + returnDate);

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= VIEW BORROWED BOOKS =================
    public static void viewBorrowedBooks(String rollNo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT bb.book_id, b.title, bb.taken_date, bb.return_date " +
                    "FROM borrowed_books bb JOIN books b ON bb.book_id=b.book_id WHERE roll_no=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollNo);

            ResultSet rs = ps.executeQuery();

            System.out.printf("\n%-10s %-30s %-15s %-15s%n",
                    "Book ID", "Title", "Taken", "Return");
            System.out.println("----------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-30s %-15s %-15s%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getDate("taken_date"),
                        rs.getDate("return_date"));
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= RETURN BOOK =================
    public static void returnBook(String rollNo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT return_date FROM borrowed_books WHERE roll_no=? AND book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollNo);
            ps.setInt(2, bookId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No record found!");
                return;
            }

            LocalDate returnDate = rs.getDate("return_date").toLocalDate();
            LocalDate today = LocalDate.now();

            if (today.isAfter(returnDate)) {
                System.out.println("Late return! Fine applicable.");
            } else {
                System.out.println("Returned on time.");
            }

            PreparedStatement deletePs = con.prepareStatement(
                    "DELETE FROM borrowed_books WHERE roll_no=? AND book_id=?");
            deletePs.setString(1, rollNo);
            deletePs.setInt(2, bookId);
            deletePs.executeUpdate();

            PreparedStatement updatePs = con.prepareStatement(
                    "UPDATE books SET quantity = quantity + 1 WHERE book_id=?");
            updatePs.setInt(1, bookId);
            updatePs.executeUpdate();

            System.out.println("Book Returned Successfully!");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
