package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	private static Connection getConn() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/imdab_db?"
					+ "user=root&password=toor&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean processQuery(String query, Processor processor) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			conn = getConn();
			if (conn == null) {
				return false;
			}

			stmt = conn.createStatement();
			if (stmt != null) {
				resultSet = stmt.executeQuery(query);
			}

			// Get amount of columns in table
			int columnCount = resultSet.getMetaData().getColumnCount();

			// For each row, (next()) create and Object[] row and process it
			while (resultSet.next()) {
				Object[] row = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					row[i - 1] = resultSet.getString(i);
				}
				processor.process(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	// Test method
	// It gives processQuery a select statement, and then tells it what to do with EACH row.
//	public static void main(String[] args) {
//
//		processQuery("SELECT ID, Rating, NrOfVotes, Title FROM game LIMIT 10", (row) -> {
//			for (int i = 0; i < row.length; i++) {
//				System.out.print(row[i] + " ");
//			}
//			System.out.println();
//		});
//	}
}
