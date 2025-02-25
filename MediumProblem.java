import java.sql.*;
import java.util.Scanner;

public class ProductCRUDOperations {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Employee";
        String username = "adarsh_1901";
        String password = "adarsh@2003";
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);

            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("Enter Product Name:");
                        String productName = scanner.nextLine();
                        System.out.println("Enter Product Price:");
                        double price = scanner.nextDouble();
                        System.out.println("Enter Product Quantity:");
                        int quantity = scanner.nextInt();

                        String insertQuery = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setString(1, productName);
                            preparedStatement.setDouble(2, price);
                            preparedStatement.setInt(3, quantity);
                            preparedStatement.executeUpdate();
                            connection.commit();
                            System.out.println("Product added successfully.");
                        } catch (SQLException e) {
                            connection.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        String selectQuery = "SELECT * FROM Product";
                        try (Statement statement = connection.createStatement();
                             ResultSet resultSet = statement.executeQuery(selectQuery)) {
                            while (resultSet.next()) {
                                int productID = resultSet.getInt("ProductID");
                                String name = resultSet.getString("ProductName");
                                double productPrice = resultSet.getDouble("Price");
                                int productQuantity = resultSet.getInt("Quantity");
                                System.out.println("ProductID: " + productID + ", Name: " + name + ", Price: " + productPrice + ", Quantity: " + productQuantity);
                            }
                        } catch (SQLException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("Enter ProductID to update:");
                        int updateProductID = scanner.nextInt();
                        scanner.nextLine();  
                        System.out.println("Enter new Product Name:");
                        String newName = scanner.nextLine();
                        System.out.println("Enter new Price:");
                        double newPrice = scanner.nextDouble();
                        System.out.println("Enter new Quantity:");
                        int newQuantity = scanner.nextInt();

                        String updateQuery = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                            preparedStatement.setString(1, newName);
                            preparedStatement.setDouble(2, newPrice);
                            preparedStatement.setInt(3, newQuantity);
                            preparedStatement.setInt(4, updateProductID);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                connection.commit();
                                System.out.println("Product updated successfully.");
                            } else {
                                System.out.println("No product found with the given ProductID.");
                            }
                        } catch (SQLException e) {
                            connection.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("Enter ProductID to delete:");
                        int deleteProductID = scanner.nextInt();

                        String deleteQuery = "DELETE FROM Product WHERE ProductID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                            preparedStatement.setInt(1, deleteProductID);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                connection.commit();
                                System.out.println("Product deleted successfully.");
                            } else {
                                System.out.println("No product found with the given ProductID.");
                            }
                        } catch (SQLException e) {
                            connection.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        connection.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
