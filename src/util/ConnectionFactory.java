package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost/bookstore";
		try {
			return DriverManager.getConnection(url, "teste", "teste");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
