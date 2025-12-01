package driver;

import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection conn;
    
    /**
     * Constructor that establishes connection to Derby database
     */
    public Database() {
        try {
            // Connect to Derby database
            String url = "jdbc:derby:libraryDB;create=true";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
    
    /**
     * Returns the database connection
     */
    public Connection getConnection() {
        return conn;
    }
    
    /**
     * Creates Students and Checkouts tables
     */
    public void createTables() {
        try (Statement stmt = conn.createStatement()) {
            // Create Students table
            String createStudents = "CREATE TABLE Students (" +
                    "student_id INT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "major VARCHAR(50))";
            stmt.execute(createStudents);
            
            // Create Checkouts table
            String createCheckouts = "CREATE TABLE Checkouts (" +
                    "checkout_id INT PRIMARY KEY, " +
                    "student_id INT, " +
                    "book_title VARCHAR(100), " +
                    "checkout_date DATE, " +
                    "FOREIGN KEY (student_id) REFERENCES Students(student_id))";
            stmt.execute(createCheckouts);
            
            System.out.println("Tables created successfully");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
    
    /**
     * Drops Students and Checkouts tables
     */
    public void dropTables() {
        try (Statement stmt = conn.createStatement()) {
            // Drop tables in correct order due to foreign key constraint
            stmt.execute("DROP TABLE Checkouts");
            stmt.execute("DROP TABLE Students");
            System.out.println("Tables dropped successfully");
        } catch (SQLException e) {
            System.out.println("Error dropping tables: " + e.getMessage());
        }
    }
    
    /**
     * Inserts sample data into Students and Checkouts tables
     */
    public void insertSampleData() {
        try (Statement stmt = conn.createStatement()) {
            // Insert sample students
            stmt.execute("INSERT INTO Students VALUES (1, 'Alice Johnson', 'CIS')");
            stmt.execute("INSERT INTO Students VALUES (2, 'Marcus Bell', 'Accounting')");
            stmt.execute("INSERT INTO Students VALUES (3, 'Tina Lee', 'Marketing')");
            
            // Insert sample checkouts
            stmt.execute("INSERT INTO Checkouts VALUES (101, 1, 'Clean Code', '2024-10-01')");
            stmt.execute("INSERT INTO Checkouts VALUES (102, 1, 'Effective Java', '2024-10-03')");
            stmt.execute("INSERT INTO Checkouts VALUES (103, 2, 'The Lean Startup', '2024-10-02')");
            
            System.out.println("Sample data inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error inserting sample data: " + e.getMessage());
        }
    }
    
    /**
     * Inserts a new student using prepared statement
     * @param studentId the student ID
     * @param name the student name
     * @param major the student major
     */
    public void insertStudent(int studentId, String name, String major) {
        String sql = "INSERT INTO Students (student_id, name, major) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setString(2, name);
            pstmt.setString(3, major);
            pstmt.executeUpdate();
            System.out.println("Student inserted successfully: " + name);
        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }
    }
    
    /**
     * Inserts a new checkout record using prepared statement
     * @param checkoutId the checkout ID
     * @param studentId the student ID
     * @param bookTitle the book title
     * @param checkoutDate the checkout date (YYYY-MM-DD format)
     */
    public void insertCheckout(int checkoutId, int studentId, String bookTitle, String checkoutDate) {
        String sql = "INSERT INTO Checkouts (checkout_id, student_id, book_title, checkout_date) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, checkoutId);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, bookTitle);
            pstmt.setString(4, checkoutDate);
            pstmt.executeUpdate();
            System.out.println("Checkout inserted successfully for book: " + bookTitle);
        } catch (SQLException e) {
            System.out.println("Error inserting checkout: " + e.getMessage());
        }
    }
    
    /**
     * Queries and displays all students
     */
    public void queryStudents() {
        String sql = "SELECT * FROM Students";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- All Students ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") + 
                                 ", Name: " + rs.getString("name") + 
                                 ", Major: " + rs.getString("major"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying students: " + e.getMessage());
        }
    }
    
    /**
     * Queries and displays all checkouts
     */
    public void queryCheckouts() {
        String sql = "SELECT * FROM Checkouts";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- All Checkouts ---");
            while (rs.next()) {
                System.out.println("Checkout ID: " + rs.getInt("checkout_id") + 
                                 ", Student ID: " + rs.getInt("student_id") + 
                                 ", Book: " + rs.getString("book_title") + 
                                 ", Date: " + rs.getDate("checkout_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying checkouts: " + e.getMessage());
        }
    }
    
    /**
     * Queries and displays student checkout information using JOIN
     */
    public void queryStudentCheckout() {
        String sql = "SELECT s.name, s.major, c.book_title, c.checkout_date " +
                     "FROM Students s JOIN Checkouts c ON s.student_id = c.student_id";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- Student Checkouts (Join) ---");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name") + 
                                 ", Major: " + rs.getString("major") + 
                                 ", Book: " + rs.getString("book_title") + 
                                 ", Date: " + rs.getDate("checkout_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying student checkouts: " + e.getMessage());
        }
    }
    
    /**
     * Queries students by name using prepared statement
     * @param name the student name to search for
     */
    public void queryStudentsByName(String name) {
        String sql = "SELECT * FROM Students WHERE name = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- Students with name: " + name + " ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") + 
                                 ", Name: " + rs.getString("name") + 
                                 ", Major: " + rs.getString("major"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying students by name: " + e.getMessage());
        }
    }
    
    /**
     * Queries checkouts by student ID using prepared statement
     * @param studentId the student ID to search for
     */
    public void queryCheckoutsByStudentId(int studentId) {
        String sql = "SELECT * FROM Checkouts WHERE student_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- Checkouts for Student ID: " + studentId + " ---");
            while (rs.next()) {
                System.out.println("Checkout ID: " + rs.getInt("checkout_id") + 
                                 ", Book: " + rs.getString("book_title") + 
                                 ", Date: " + rs.getDate("checkout_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying checkouts by student ID: " + e.getMessage());
        }
    }
    
    /**
     * Queries and displays count of books checked out by each major
     */
    public void queryBooksByMajor() {
        String sql = "SELECT major, COUNT(*) AS total_books_checked_out " +
                     "FROM Students s JOIN Checkouts c ON s.student_id = c.student_id " +
                     "GROUP BY major";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- Books Checked Out by Major ---");
            while (rs.next()) {
                System.out.println("Major: " + rs.getString("major") + 
                                 ", Total Books: " + rs.getInt("total_books_checked_out"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying books by major: " + e.getMessage());
        }
    }
}