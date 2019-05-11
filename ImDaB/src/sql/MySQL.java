package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;


public class MySQL {

	private final LinkedBlockingQueue<String> queryQueue = new LinkedBlockingQueue<String>();
	private volatile boolean running = true;
	private static final int minWaitTime = 5;
	private static MySQL instance = null;

	private MySQL() {
		Thread thread = new Thread(() -> {
			while (running) {
				Instant start = Instant.now();

				while (queryQueue.size() > 0)
					executeQuery(queryQueue.remove());
				try {
					Thread.sleep(Math.min(minWaitTime - Duration.between(start, Instant.now()).toMillis(), 0));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private static Connection getConn() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/imdab_db?" +
			                                   "user=root&password=_IMDaB_123&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		} catch(Exception e) {
			return null;
		}
	}

	public static boolean enqueNonResultQuery(String insertQuery) {
		if (instance == null)
			instance = new MySQL();
		try {
			instance.queryQueue.put(insertQuery);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private void executeQuery(String insertQuery) {
		if (insertQuery == null || insertQuery.isEmpty())
			return;
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = getConn();
			if (conn == null)
				return;

			stmt = conn.createStatement();
			if (stmt != null)
				stmt.execute(insertQuery);

		} catch(Exception e) {
			e.printStackTrace();
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
	}

}
