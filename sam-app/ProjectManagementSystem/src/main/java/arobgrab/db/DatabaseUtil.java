package arobgrab.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
	public static String rdsMySqlDatabaseUrl;
	public static String dbUsername;
	public static String dbPassword;

	public final static String jdbcTag = "jdbc:mysql://";
	public final static String rdsMySqlDatabasePort = "3315";
	public final static String multiQueries = "?allowMultiQueries=true";

	public final static String dbName = "pmsDB";
	public final static String testingName = "tmp";

	static Connection conn;

	protected static Connection connect() throws Exception {
		if (conn != null) { return conn; }

		boolean useTestDB = System.getenv("TESTING") != null;

		String schemaName = dbName;
		if (useTestDB) { 
			schemaName = testingName;
			System.out.println("USE TEST DB:" + useTestDB);
		}

		dbUsername = System.getenv("dbUsername");
		if (dbUsername == null) {
			System.out.println("Environment variable dbUsername is not set");
		}

		dbPassword = System.getenv("dbPassword");
		if (dbPassword == null) {
			System.out.println("Environment variable dbPassword is not set");
		}

		rdsMySqlDatabaseUrl = System.getenv("rdsMySqlDatabaseUrl");
		if (rdsMySqlDatabaseUrl == null) {
			System.out.println("Environment variable rdsMySqlDatabaseUrl is not set");
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + schemaName + multiQueries,
					dbUsername,
					dbPassword);

			System.out.println("Database has been connected successfully");
			return conn;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed in database connection");
		}
	}
}
