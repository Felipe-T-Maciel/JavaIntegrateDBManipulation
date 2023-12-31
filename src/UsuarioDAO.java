import java.sql.*;

public class UsuarioDAO extends DAOPadrao<Usuario, Integer>{


    public UsuarioDAO() throws SQLException {
        super("usuario");
    }

    @Override
    public Usuario converter(ResultSet rs) throws SQLException {
        return new Usuario(rs);
    }

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
}
