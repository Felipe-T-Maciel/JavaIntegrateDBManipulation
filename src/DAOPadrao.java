import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

abstract class DAOPadrao<T, ID> implements ICRUD<T,ID> {

    protected Connection connection;
    private String tabela;

    public DAOPadrao(String tabela) throws SQLException {
        this.connection = Banco.conectar();
        this.tabela = tabela;
    }

    @Override
    public Set<T> buscarTodos(Connection connection) {
        Set<T> listaTodos = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from "+tabela+";")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaTodos.add(converter(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaTodos;
    }

    public void deletar(Connection connection, Integer integer) {
        try (PreparedStatement statement = connection.prepareStatement("delete from "+tabela+" where id=?;")){
            statement.setInt(1,integer);
            statement.execute();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public T buscarUm(Connection connection, Integer integer) {
        try (PreparedStatement statement = connection.prepareStatement("select * from "+tabela+" where id=?")){
            statement.setInt(1,integer);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return converter(rs);
            }
            throw new NoSuchElementException();
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
    }

    public abstract T converter(ResultSet rs) throws SQLException;
}
