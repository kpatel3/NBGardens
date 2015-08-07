import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySQL {

	public static ArrayList<String> data = new ArrayList<String>();
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/ProductDB";
	final static String USER = "root";
	final static String PASS = "Netbuilder12";
	
	
	public static void accessBD(String task, String table, String field){

		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			if ((task != "") & (table != "") & (field != "")){
				switch(task){
				case "READ":
					//Read
					System.out.println("Creating statement...");
					stmt = conn.createStatement();
					String sql2 = "SELECT " + field + " FROM " + table;
					ResultSet rs = stmt.executeQuery(sql2);
					while (rs.next()) {
						int id = rs.getInt(field);
						System.out.println("ID: " + id);
						data.add(String.valueOf(id));
					}
					rs.close();
					break;
				default:
					System.out.println("No operation... Invalid input");
				}
			}
			
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
		accessBD("","","");
	}
	
	public static ArrayList<String> read_Database(){
		return data;
	}

}