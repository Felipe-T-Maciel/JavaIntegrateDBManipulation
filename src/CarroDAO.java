import java.sql.*;

public class CarroDAO extends DAOPadrao<Carro, Integer>{

    public CarroDAO() throws SQLException {
        super("carro");
    }

    @Override
    public Carro converter(ResultSet rs) throws SQLException {
        return new Carro(rs);
    }

    @Override
    public void inserir(Connection connection, Carro car) {
        String comandoSql = "insert into carro values(?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
            statement.setInt(1,car.getId());
            statement.setString(2,car.getMarca());
            statement.setString(3,car.getModelo());
            statement.setInt(4,car.getAno());
            statement.setDouble(5,car.getPreco());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void atualizar(Connection connection, Carro car) {
        String comandoSql = "update carro set marca = ?,modelo = ?, ano = ?, preco = ? where id= ?;";
        try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
            statement.setString(1,car.getMarca());
            statement.setString(2,car.getModelo());
            statement.setInt(3,car.getAno());
            statement.setDouble(4,car.getPreco());
            statement.setDouble(5,car.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
