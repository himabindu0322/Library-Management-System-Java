import java.sql.*;
import java.util.Scanner;

public class BookManager {

    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASSWORD = "mysql195";

    static Scanner sc = new Scanner(System.in);

    // ================= ADD BOOK =================
    public static void addBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            if (price <= 0 || quantity <= 0) {
                System.out.println("Price and Quantity must be positive!");
                return;
            }

            String sql = "INSERT INTO books(title, author, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);

            ps.executeUpdate();
            System.out.println("Book Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= VIEW BOOKS =================
    public static void viewBooks() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM books";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Header
            System.out.printf("%-5s %-60s %-25s %-10s %-5s%n",
                    "ID", "Title", "Author", "Price", "Qty");

            System.out.println("-------------------------------------------------------------------------------------------------");

            // Data rows
            while (rs.next()) {
                System.out.printf("%-5d %-60s %-25s %-10.2f %-5d%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // ================= SEARCH BY ID =================
    public static void searchById() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT * FROM books WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n--- Book Found ---");

                System.out.printf("%-8s %-30s %-25s %-10s %-10s%n",
                        "ID", "Title", "Author", "Price", "Qty");
                System.out.println("---------------------------------------------------------------------");

                System.out.printf("%-8d %-30s %-25s %-10.2f %-10d%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));

            } else {
                System.out.println("Book ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= UPDATE BOOK =================
    public static void updateBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID to Update: ");
            int id = sc.nextInt();

            System.out.print("Enter New Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            String sql = "UPDATE books SET  quantity=? WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Book Updated Successfully!");
            else
                System.out.println("Book ID not found!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= DELETE BOOK =================
    public static void deleteBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID to Delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM books WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Book Deleted Successfully!");
            else
                System.out.println("Book ID not found!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
