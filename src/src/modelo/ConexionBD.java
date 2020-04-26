package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private Connection jdbcConnection;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    
    
    
    public ConexionBD() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConexionBD(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	public void conectar() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
        }
    }
     
    public void desconectar() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

	public Connection getJdbcConnection() {
		return jdbcConnection;
	}  
	
	public ResultSet getQuery(String _query) {
        Statement state = null;
        ResultSet resultado = null;
        try {
            state = (Statement) jdbcConnection.createStatement();
            resultado = state.executeQuery(_query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void setQuery(String _query) {

        Statement state = null;

        try {
            state = (Statement) jdbcConnection.createStatement();
            state.execute(_query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}