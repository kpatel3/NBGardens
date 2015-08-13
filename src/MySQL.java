import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySQL {
	//variable that is used to send data to other classes
	public static ArrayList<String> data = new ArrayList<String>();
	
	//Strings that are used to initiallise the database
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/ProductDB";
	final static String USER = "root";
	final static String PASS = "Netbuilder12";
	
	
	public static void accessBD(String task, String query1, String back){

		//variables used to connect to the database
		Connection conn = null;
		Statement stmt = null;
		
		try{
			//erasing previous data
			data.clear();
			
			//connecting to the database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			
			if ((task != "")){
				switch(task){
				//Reading data from the database
				case "READ":
					ResultSet rs = stmt.executeQuery(query1);
					while (rs.next()) {
						String details = rs.getString(back);
						data.add(details);
					}
					rs.close();
					break;
					
				//Searching for 	
				case "Search":
					ResultSet rs2 = stmt.executeQuery(query1);
					while (rs2.next()){
						String details = rs2.getString(back);
						data.add(details);
					}
					rs2.close();
					break;
					
				//Adding or updating data to the database	
				case "Update":
				case "Add":
					stmt.execute(query1);
					break;
					
				//Using the JOIN statement in the database
				case "Refine":
					ResultSet rst = stmt.executeQuery(query1);
					while(rst.next()){
						String details = rst.getString(back);
						data.add(details);
					}
					rst.close();
					break;
					
				//What happends if there is an invalid input	
				default:
					System.out.println("No operation... Invalid input");
				}
			}
		
		//error handling	
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			
		//closing the connection	
		}finally {
			try {
				
				if (stmt != null){
					conn.close();
				}
			}catch (SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	
	//returns the size of the list of data (Used for UI)
	public static int get_DataSize(){
		return data.size();
	}

	//Used to update the status of the other (used in UI)
	public static void Update_Status(int orderID, String toWhat){
		accessBD("Update", "UPDATE `productdb`.`order` SET `status` = "+ toWhat +" WHERE `order_ID` = " + orderID, "");
	}	
	
	//Transfers the data from an ArrayList to a String[]
	public static String[] Transfer_Data(){
		String[] transfer = new String[data.size()];
		for (int i = 0; i < data.size(); i++){
			transfer[i] = data.get(i);
		}
		return transfer;
	}
	
	
}