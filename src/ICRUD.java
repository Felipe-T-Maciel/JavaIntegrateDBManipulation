import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ICRUD<T, ID> {
    void inserir(Connection connection,T obj);
    T buscarUm(Connection connection, ID id) throws NoSuchFieldException;
    Set<T> buscarTodos(Connection connection);
    void atualizar(Connection connection, T obj);
    void deletar(Connection connection, ID id) throws SQLException;
}
