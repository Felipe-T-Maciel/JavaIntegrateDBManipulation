//create database aulaJava;
//        use aulaJava
//
//        create table usuario(
//        id int primary key not null,
//        nome varchar(50) not null,
//        senha varchar(50) not null,
//        idade int
//        );
import java.sql.*;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Set<Usuario> listaUsuarios = new HashSet<>();
    static String urlBanco = "jdbc:mysql://localhost:3306/aulajava";
    static String usuario = "root";
    static String senha = "root";

    public static void main(String[] args){
        menu();
    }

    private static void getUsuarios(){
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)) {
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void menu() {
        int escolha = 0;
        do{
            System.out.print("""
                --- Bem vindo ao JavaBD ---
                
                [1] Inserir Novo
                [2] Buscar todos
                [3] Buscar um especifico
                [4] Editar um usuario
                [5] Deletar um usuario
                [6] deletar todos os usuarios
                [7] Sair
                >\t""");
            escolha = sc.nextInt();
            listaUsuarios.clear();
            getUsuarios();
            switch (escolha){
                case 1 -> {
                    inserir(Objects.requireNonNull(criarUser()));
                }
                case 2 -> {
                    for (Usuario user:
                            listaUsuarios) {
                        System.out.println(user.toString());
                    }
                }
                case 3 -> {
                    buscarUmEspecifico();
                }
                case 4 -> {
                    editarUser();
                }
                case 5 -> {
                    deletarUmUsuario();
                }
                case 6 -> {
                    deletarTodos();
                }
                case 7->{
                    System.out.println("Saindo...");
                    System.exit(0);
                }
            }
        }while (true);
    }

    private static void buscarUmEspecifico() {
        int id = procuraId();
        for (Usuario user:
             listaUsuarios) {
            if(user.getId() == id){
                System.out.println(user.toString());
            }
        }
    }

    private static void deletarTodos() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){
            try (PreparedStatement statement = connection.prepareStatement("delete from usuario;")){
                statement.execute();
                System.out.println("Todos os usuarios deletados com sucesso!");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void deletarUmUsuario() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){
            int id = procuraId();
            try (PreparedStatement statement = connection.prepareStatement("delete from usuario where id=?;")){
                statement.setInt(1,id);
                statement.execute();
                System.out.println("Usuario deletado com sucesso!");
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void editarUser() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){

            int id = procuraId();
            System.out.print("novo nome: ");
            String nome = sc.next();
            System.out.print("nova senha: ");
            String senha = sc.next();
            System.out.print("nova idade: ");
            int idade = sc.nextInt();
            String comandoSql = "update usuario set nome = ?,senha=?,idade = ? where id= ?;";
            try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
                statement.setString(1,nome);
                statement.setString(2,senha);
                statement.setInt(3,idade);
                statement.setInt(4,id);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            System.out.println("Usuario alterado com sucesso!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static int procuraId() {
        int escolhaId = 0;
        do{
            System.out.print("O id que deseja editar: ");
            escolhaId = sc.nextInt();
        }while (escolhaId<=0 || escolhaId>(listaUsuarios.size()));
        return escolhaId;
    }

    private static Usuario criarUser() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)) {
            System.out.print("Nome: ");
            String nome = sc.next();
            System.out.print("Senha: ");
            String senha = sc.next();
            System.out.print("idade: ");
            int idade = sc.nextInt();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select max(id) as id from usuario;");
            rs.next();
            return new Usuario(rs.getInt("id")+1, nome, senha, idade);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static void inserir(Usuario user){
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){
            String comandoSql = "insert into usuario values(?,?,?,?);";
            try (PreparedStatement statement = connection.prepareStatement(comandoSql)){
                statement.setInt(1,user.getId());
                statement.setString(2,user.getNome());
                statement.setString(3,user.getSenha());
                statement.setInt(4,user.getIdade());
                statement.execute();
                System.out.println("Voce foi cadastrado no sistema e seu id é: "+user.getId()+" divirta-se!");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
