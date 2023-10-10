import java.sql.*;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){

        try (Connection connection = Banco.conectar()){
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            CarroDAO carroDAO = new CarroDAO();

            do{
                System.out.print("""
                        --- Menu ---
                        
                        [1] Usuarios
                        [2] Carros
                        
                        >\t""");
                int opcao = sc.nextInt();
                switch (opcao){
                    case 1->{
                        menuUsuario(connection, usuarioDAO, carroDAO);
                    }
                    case 2->{
                        menuCarro(connection, carroDAO);
                    }
                    case 3->{
                        System.out.println("Fechando servidor...");
                        System.exit(0);
                    }
                }
            }while (true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static void menuCarro(Connection connection, CarroDAO carroDAO) {
        do{
            System.out.print("""
                --- Bem vindo ao CarroBD ---
                
                [1] Inserir Novo
                [2] Buscar todos
                [3] Buscar um especifico
                [4] Editar um usuario
                [5] Deletar um usuario
                [6] Voltar
                
                >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1 -> {
                    Carro carro = criarCarro(connection);
                    assert carro != null;
                    carroDAO.inserir(connection,carro);
                    System.out.println("O carro foi cadastrado no sistema e seu id é: "+carro.getId()+" divirta-se!");
                }
                case 2 -> {
                    for (Carro carro :
                            carroDAO.buscarTodos(connection) ) {
                        System.out.println(carro.toString());
                    }
                }
                case 3 -> {
                    System.out.println(carroDAO.buscarUm(connection,procuraIdCar(connection, carroDAO)));
                }
                case 4 -> {
                    carroDAO.atualizar(connection,editarCarro(procuraIdCar(connection, carroDAO)));
                }
                case 5 -> {
                    carroDAO.deletar(connection, procuraIdCar(connection,carroDAO));
                }
                case 6->{
                    return;
                }
            }
        }while (true);
    }

    private static Carro criarCarro(Connection connection) {
        System.out.print("Marca: ");
        String marca = sc.next();
        System.out.print("Modelo: ");
        String modelo = sc.next();
        System.out.print("Ano: ");
        int ano = sc.nextInt();
        System.out.print("Preco: ");
        double preco = sc.nextInt();
        try (PreparedStatement statement = connection.prepareStatement("select max(id) as id from carro;")){
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return new Carro(rs.getInt("id")+1, marca, modelo, ano,preco);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static void menuUsuario(Connection connection, UsuarioDAO usuarioDAO, CarroDAO carroDAO) {
        do{
            System.out.print("""
                --- Bem vindo ao UserBD ---
                
                [1] Inserir Novo
                [2] Buscar todos
                [3] Buscar um especifico
                [4] Editar um usuario
                [5] Deletar um usuario
                [6] Linkar um carro ao usuario
                [7] Voltar
                
                >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1 -> {
                    Usuario usuario = criarUser(connection, carroDAO);
                    usuarioDAO.inserir(connection,usuario);
                    System.out.println("Voce foi cadastrado no sistema e seu id é: "+usuario.getId()+" divirta-se!");
                }
                case 2 -> {
                    for (Usuario u :
                            usuarioDAO.buscarTodos(connection) ) {
                        if(u.getCarro()!=null){
                            System.out.println(u.toString());
                        }else {
                            System.out.println(u.toStringSemCarro());
                        }
                    }
                }
                case 3 -> {
                    System.out.println(usuarioDAO.buscarUm(connection,procuraIduser(connection, usuarioDAO)));
                }
                case 4 -> {
                    usuarioDAO.atualizar(connection,editarUser(procuraIduser(connection, usuarioDAO)));
                }
                case 5 -> {
                    usuarioDAO.deletar(connection, procuraIduser(connection, usuarioDAO));
                }
                case 6 -> {

                }
                case 7->{
                    return;
                }
            }
        }while (true);
    }

    public static Usuario editarUser(int id) {
            System.out.print("novo nome: ");
            String nome = sc.next();
            System.out.print("nova senha: ");
            String senha = sc.next();
            System.out.print("nova idade: ");
            int idade = sc.nextInt();
            return new Usuario(id,nome,senha,idade);
    }

    public static Carro editarCarro(int id) {
        System.out.print("nova marca: ");
        String marca = sc.next();
        System.out.print("novo modelo: ");
        String modelo = sc.next();
        System.out.print("novo ano: ");
        int ano = sc.nextInt();
        System.out.print("novo preco: ");
        double preco = sc.nextDouble();
        return new Carro(id,marca,modelo,ano,preco);
    }

    private static int procuraIduser(Connection connection, UsuarioDAO usuarioDAO) {
        int escolhaId = 0;
        do{
            System.out.print("[0] Para cancelar pesquisa" +
                    "\nO id que deseja buscar: ");
            escolhaId = sc.nextInt();
            if(escolhaId!=0) {
                for (Usuario user :
                        usuarioDAO.buscarTodos(connection)) {
                    if (user.getId() == escolhaId) {
                        return escolhaId;
                    }
                }
            }
        }while(true);
    }

    private static int procuraIdCar(Connection connection, CarroDAO carroDAO) {
        int escolhaId = 0;
        do{
            System.out.print("[0] Para cancelar pesquisa" +
                    "\nO id que deseja buscar: ");
            escolhaId = sc.nextInt();
            if(escolhaId!=0) {
                for (Carro carro :
                        carroDAO.buscarTodos(connection)) {
                    if (carro.getId() == escolhaId) {
                        return escolhaId;
                    }
                }
            }
        }while(true);
    }


    private static Usuario criarUser(Connection connection, CarroDAO carroDAO) {
            System.out.print("Nome: ");
            String nome = sc.next();
            System.out.print("Senha: ");
            String senha = sc.next();
            System.out.print("idade: ");
            int idade = sc.nextInt();
            System.out.print("[0] Para não possui" +
                    "\nId do carro: ");
            int idCarro = sc.nextInt();
        try (PreparedStatement statement = connection.prepareStatement("select max(id) as id from usuario;")){
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(idCarro==0){
                    return new Usuario(rs.getInt("id")+1, nome, senha, idade);
                }else {
                    try {
                        return new Usuario(rs.getInt("id")+1, nome, senha, idade, carroDAO.buscarUm(connection,idCarro));
                    } catch (SQLException throwables) {
                        System.err.println(throwables.getMessage());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Voce fez cagada");
    }
}
