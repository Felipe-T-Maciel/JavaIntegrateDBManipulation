import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CarroDAO extends DAOPadrao<Carro, Integer>{

    public CarroDAO(Connection connection) {
        super(connection);
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
    public Carro buscarUm(Connection connection, Integer integer){
        try (PreparedStatement statement = connection.prepareStatement("select * from carro where id=?")){
            statement.setInt(1,integer);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new Carro(rs);
        } catch (SQLException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
        throw new NullPointerException("Id não encontrado");
    }


    @Override
    public Set<Carro> buscarTodos(Connection connection) {
        Set<Carro> listaCarros = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from carro;")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaCarros.add(new Carro(rs));
            }
            return listaCarros;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException();
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

    @Override
    public void deletar(Connection connection, Integer integer){
        try (PreparedStatement statement = connection.prepareStatement("delete from carro where id=?;")){
            statement.setInt(1,integer);
            statement.execute();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
