import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        String url = ""D:\Employee"";
        String username = "adarsh_1901";
        String password = "adarsh@2003";
        String query = "SELECT EmpID, Name, Salary FROM Employee";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                System.out.println("EmpID: " + empID + ", Name: " + name + ", Salary: " + salary);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }
}
