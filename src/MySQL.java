import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQL {

	
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/ProductDB";
	final static String USER = "root";
	final static String PASS = "Netbuilder12";
			
	public static void accessBD(){

		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Create
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();
			String sql = "INSERT INTO Products (product_Name, product_Location) " + "VALUES ('Gnome','1')";
			stmt.executeUpdate(sql);
			System.out.println("Inserted records into the table...");
			
			//Read
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql2 = "SELECT product_ID, product_Name, product_Location FROM Products";
			ResultSet rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				int id = rs.getInt("product_ID");
				String name = rs.getString("product_Name");
				int location = rs.getInt("product_Location");
				System.out.println("ID: " + id + ",name: " + name + ", data: " + location);
			}
			rs.close();	
			
			//Update
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql3 = "UPDATE Products " + "SET product_Name = 'Fatty' WHERE product_ID = 2";
			stmt.executeUpdate(sql3);
			
			//Delete
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql4 = "DELETE FROM Products " + "WHERE product_Name = 'Gnome'";
			stmt.executeUpdate(sql4);
			
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (stmt != null){
					conn.close();
				}
			}catch (SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}
	public static void main(String[]args){
		accessBD();
	}

}