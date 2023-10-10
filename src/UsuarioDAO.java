import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDAO implements ICRUD<Usuario, Integer>{


    @Override
    public void inserir(Connection connection, Usuario user) {
        String comandoSql = "insert into usuario values(?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
            statement.setInt(1,user.getId());
            statement.setString(2,user.getNome());
            statement.setString(3,user.getSenha());
            statement.setInt(4,user.getIdade());
            try {
                statement.setInt(5,user.getCarro().getId());
            }catch (NullPointerException e){
                statement.setNull(5,0);
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Usuario buscarUm(Connection connection, Integer integer) {
        try (PreparedStatement statement = connection.prepareStatement("select * from usuario where id=?")){
            statement.setInt(1,integer);
            ResultSet rs = statement.executeQuery();
            rs.next();
            for (Usuario user:
                 buscarTodos(connection)) {
                if(rs.getInt("id") == user.getId()){
                    return user;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public Set<Usuario> buscarTodos(Connection connection) {
        Set<Usuario> listaUsuarios = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from usuario;")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaUsuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getInt("idade")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaUsuarios;
    }

    @Override
    public void atualizar(Connection connection, Usuario obj) {
        String comandoSql = "update usuario set nome = ?,senha=?,idade = ?, id_carro = ? where id= ?;";
        try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
            statement.setString(1,obj.getNome());
            statement.setString(2,obj.getSenha());
            statement.setInt(3,obj.getIdade());
            statement.setInt(4,obj.getId());
            try {
                statement.setInt(5,obj.getCarro().getId());
            }catch (NullPointerException e){
                statement.setInt(5,0);
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deletar(Connection connection, Integer integer) {
        try (PreparedStatement statement = connection.prepareStatement("delete from usuario where id=?;")){
            statement.setInt(1,integer);
            statement.execute();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
