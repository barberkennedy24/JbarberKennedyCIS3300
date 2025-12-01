package driver;

public class Driver {
    /**
     * Main method that demonstrates all database operations
     */
    public static void main(String[] args) {
        // Create database instance
        Database db = new Database();
        
        try {
            // Drop tables if they exist (clean start)
            db.dropTables();
        } catch (Exception e) {
            System.out.println("Tables may not exist yet, continuing...");
        }
        
        // Create tables
        db.createTables();
        
        // Insert sample data
        db.insertSampleData();
        
        // Test all query methods
        System.out.println("\n=== TESTING QUERY METHODS ===");
        db.queryStudents();
        db.queryCheckouts();
        db.queryStudentCheckout();
        db.queryStudentsByName("Alice Johnson");
        db.queryCheckoutsByStudentId(1);
        db.queryBooksByMajor();
        
        // Test insert methods with new data
        System.out.println("\n=== TESTING INSERT METHODS ===");
        db.insertStudent(4, "John Doe", "CIS");
        db.insertCheckout(104, 4, "Database Systems", "2024-10-05");
        
        // Show updated data
        System.out.println("\n=== UPDATED DATA ===");
        db.queryStudents();
        db.queryCheckouts();
        db.queryBooksByMajor();
        
        System.out.println("\nAll operations completed successfully!");
    }
}