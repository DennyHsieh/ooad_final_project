package controll;
import java.sql.*;

/**
 * This class is used to make the communication with database easier.
 * @author Jerry
 */
public class MysqlExe {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_finalproject_assigned?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "root";
	private static int debug;
	
	/**
	 * Check if the debug mod on.
	 * @return If the debug mod on.
	 */
	public static int getDebug() {
		return debug;
	}
	/**
	 * Set the debug mod.
	 * @param debug The debug mod.
	 */
	public static void setDebug(int debug) {
		MysqlExe.debug = debug;
	}
	
	/**
	 * Get the handler of the connection.
	 * @author Jerry
	 */
	public static class RetVal {
		public Connection conn;
		public ResultSet res;
		public RetVal(Connection conn, ResultSet res) {
			this.conn = conn;
			this.res = res;
		}
	}
	/**
	 * Execute a sql.
	 * @param sql SQL statement
	 * @throws SQLException
	 */
	public static void execStmt(String sql) throws SQLException {
		if (debug == 1) System.out.println("[ExecStmt] " + sql);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.close();
		} catch (SQLException e) {
			if (conn != null) conn.close();
			throw e;
		}
	}
	/**
	 * Execute a sql and fetch results.
	 * @param sql SQL statement
	 * @return Result and Connection Object.
	 * @throws SQLException
	 */
	public static RetVal execQuery(String sql) throws SQLException {
		if (debug == 1) System.out.println("[ExecQuery] " + sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			if (conn != null) conn.close();
			throw e;
		}
		return new RetVal(conn, rs);
	}
	
	/*public static void main(String[] args) {
		execStmt("DELETE FROM timeTable_down");
		execStmt("INSERT INTO timeTable_down VALUES (20170527, 0833, 1400, 1411, 1419, 1434, 1447, 1458, 1517, 1530, 1541, 1555, 1613, 1625)");
	}*/
}
