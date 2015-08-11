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
	
	
	public static void accessBD(String task, String query1, String back){

		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			data.clear();
			data2.clear();
			stmt = conn.createStatement();
			if ((task != "")){
				switch(task){
				case "READ":
					//Read


					ResultSet rs = stmt.executeQuery(query1);
					while (rs.next()) {
						String details = rs.getString(back);
						data.add(details);
					}
					rs.close();
					break;
				case "Search":
					//Search for other fields
					ResultSet rs2 = stmt.executeQuery(query1);
					while (rs2.next()){
						String details = rs2.getString(back);
						data.add(details);
					}
					rs2.close();
					break;
				case "Update":
				case "Add":
					stmt.execute(query1);
					break;
				case "Refine":
					ResultSet rst = stmt.executeQuery(query1);
					while(rst.next()){
						String details = rst.getString(back);
						data.add(details);
					}
					rst.close();
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
		//System.out.println("Goodbye!");
	}
	
	public static int get_DataSize(){
		return data.size();
	}

	
	public static void Update_Status(int orderID, String toWhat){
		accessBD("Update", "UPDATE `productdb`.`order` SET `status` = "+ toWhat +" WHERE `order_ID` = " + orderID, "");
	}	
	
	public static String[] Transfer_Data(){
		String[] transfer = new String[data.size()];
		for (int i = 0; i < data.size(); i++){
			transfer[i] = data.get(i);
		}
		return transfer;
	}
	
	
}