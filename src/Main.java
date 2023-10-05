//create database aulaJava;
//        use aulaJava
//
//        create table usuario(
//        id int primary key not null,
//        nome varchar(50) not null,
//        senha varchar(50) not null,
//        idade int
//        );

import com.mysql.cj.jdbc.JdbcConnection;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;

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
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from usuario;");
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
    }

    private static void menu() {
        int escolha = 0;
        do{
            System.out.print("""
                --- Bem vindo ao JavaBD ---
                
                [1] Inserir Novo
                [2] Buscar todos
                [3] Editar um usuario
                [4] Deletar um usuario
                [5] deletar todos os usuarios
                [6] Sair
                >\t""");
            escolha = sc.nextInt();
            listaUsuarios.clear();
            getUsuarios();
            switch (escolha){
                case 1 -> {
                    inserir(criarUser());
                }
                case 2 -> {
                    for (Usuario user:
                         listaUsuarios) {
                        System.out.println(user.toString());
                    }
                }
                case 3 -> {
                    editarUser();
                }
                case 4 -> {
                    deletarUmUsuario();
                }
                case 5 -> {
                    deletarTodos();
                }
                case 6->{
                    System.out.println("Saindo...");
                    System.exit(0);
                }
            }
        }while (true);
    }

    private static void deletarTodos() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){

            Statement statement = connection.createStatement();

            statement.execute("delete from usuario;");
            System.out.println("Todos os usuarios deletados com sucesso!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void deletarUmUsuario() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){

            Statement statement = connection.createStatement();
            int escolhaId = 0;
            do{
                System.out.print("O id que deseja deletar: ");
                escolhaId = sc.nextInt();
            }while (escolhaId<=0 || escolhaId>(listaUsuarios.size()));
            statement.execute("delete from usuario where id="+escolhaId+";");
            System.out.println("Usuario deletado com sucesso!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void editarUser() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){

            Statement statement = connection.createStatement();
            int escolhaId = 0;
            do{
                System.out.print("O id que deseja editar: ");
                escolhaId = sc.nextInt();
            }while (escolhaId<=0 || escolhaId>(listaUsuarios.size()));
            System.out.print("novo nome: ");
            String nome = sc.next();
            System.out.print("nova senha: ");
            String senha = sc.next();
            System.out.print("nova idade: ");
            int idade = sc.nextInt();
            statement.execute("update usuario set nome = '"+nome+"',senha='"+senha+"',idade = "+idade+" where id="+escolhaId+";");

            System.out.println("Usuario alterado com sucesso!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Usuario criarUser() {
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)) {
            Statement statement = connection.createStatement();
            System.out.print("Nome: ");
            String nome = sc.next();
            System.out.print("Senha: ");
            String senha = sc.next();
            System.out.print("idade: ");
            int idade = sc.nextInt();

            ResultSet rs = statement.executeQuery("select max(id) as id from usuario;");
            rs.next();
            return new Usuario(rs.getInt("id")+1, nome, senha, idade);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static void inserir(Usuario usuar){
        try (Connection connection = DriverManager.getConnection(urlBanco,usuario,senha)){
            Statement statement = connection.createStatement();
            String comandoSql = "insert into usuario values("+usuar.getId()+",'"+usuar.getNome()+"','"+ usuar.getSenha()+"',"+usuar.getIdade()+");";
            statement.execute(comandoSql);
            System.out.println("Voce foi cadastrado no sistema e seu id é: "+usuar.getId()+" divirta-se!");
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
