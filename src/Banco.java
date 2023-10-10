import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    private static final String urlBanco = "jdbc:mysql://localhost:3306/aulajava";
    private static final String usuarioBD = "root";
    private static final String senhaBD = "root";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlBanco,usuarioBD,senhaBD);
    }
}
