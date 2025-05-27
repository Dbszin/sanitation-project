package sanea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/sanea_mais?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String SENHA = "root1234";
	public static Connection conectar() throws SQLException {
		
		return DriverManager.getConnection(URL, USER, SENHA);
	}
	
}
