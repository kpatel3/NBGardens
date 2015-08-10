import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySQL {

	public static ArrayList<String> data = new ArrayList<String>();
	public static ArrayList<String> data2 = new ArrayList<String>();
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/ProductDB";
	final static String USER = "root";
	final static String PASS = "Netbuilder12";
	
	
	public static void accessBD(String task, String table, String field, String field2, String field3, String field4, String table2, int id){

		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			data.clear();
			data2.clear();
			stmt = conn.createStatement();
			if ((task != "") & (table != "") & (field != "")){
				switch(task){
				case "READ":
					//Read
					System.out.println("Creating read statement...");
					String sql1 = "SELECT " + field + " FROM " + table;
					ResultSet rs = stmt.executeQuery(sql1);
					while (rs.next()) {
						String details = rs.getString(field);
						data.add(details);
					}
					rs.close();
					break;
				case "Search":
					//Search for other fields
					System.out.println("Creating search statement...");
					String sql2 = "SELECT " + field + " FROM " + table + " WHERE " + field2 + "=" + id;
					ResultSet rs2 = stmt.executeQuery(sql2);
					while (rs2.next()){
						String details = rs2.getString(field);
						data.add(details);

					}
					rs2.close();
					break;
				case "Refine":
					System.out.println("Creating refine statement...");
					String sql3 = "SELECT " + field + " FROM " + table + " WHERE " + field2 + "=" + id;
					ResultSet rs3 = stmt.executeQuery(sql3);
				
					while(rs3.next()){
						String details = rs3.getString(field);
						data.add(details);

					}
					rs3.close();
					String[] transfer = new String[data.size()];
					ResultSet rs3_1 = null;
					for (int i = 0; i < data.size(); i++){
						transfer[i] = data.get(i);
						String sql3_1 = "SELECT " + field3 + " FROM " + table2 + " WHERE " + field4 + "=" + transfer[i];
						rs3_1 = stmt.executeQuery(sql3_1);
						while(rs3_1.next()){
							String details2 = rs3_1.getString(field3);
							data2.add(details2);
						}
					}
					rs3_1.close();	
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
		accessBD("","","","","","","", 0);
		
	}
	
	public static int get_DataSize(){
		return data.size();
	}


	public static String[] Get_Database(String task, String table, String field){
		accessBD(task,table,field, "","","","", 0);

		String[] transfer = new String[data.size()];
		
		for (int i = 0; i < data.size(); i++){
			transfer[i] = data.get(i);
		}
		
		return transfer;
	}
	
	
}