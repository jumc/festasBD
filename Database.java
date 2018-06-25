import java.sql.*;

// Tem q instalar o driver jdbc do postgre e colocar o jar dele no projeto para funcionar

public class Database {
	
	static private Connection db = null;
	static private String HOST = "jdbc:postgresql://localhost:5432/postgres";
	static private String USER = "postgres";
	static private String PASS = "admin";
	static private Statement stmt = null;
	
	public static boolean open() {
		try {
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection(HOST, USER, PASS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ResultSet runQuery(String statement) {
		try {
			stmt = db.createStatement();
            ResultSet result = stmt.executeQuery(statement);
            /*while (result.next())
            {
                System.out.print("Column 1 returned ");
                System.out.println(result.getString(1));
            }*/
            return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int runUpdate(String statement){
        int rowsAltered = 0;
        try {
            stmt = db.createStatement();
            rowsAltered = stmt.executeUpdate(statement);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(rowsAltered + " rows altered");
        return rowsAltered;
    }

	public static void close() {
		try {
			stmt.close();
			db.close();
            db = null;
            stmt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
