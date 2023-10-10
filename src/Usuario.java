import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private Integer id;
    private String nome;
    private String senha;
    private Integer idade;
    private Carro carro;

    public Usuario(Integer id, String nome, String senha, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.idade = idade;
    }

    public Usuario(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nome = rs.getString("nome");
        this.senha = rs.getString("senha");
        if(rs.getInt("id_carro")!=0){
            this.carro = new Carro(rs.getInt("id_carro"));
        }
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Usuario(Integer id, String nome, String senha, Integer idade, Carro carro) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.idade = idade;
        this.carro = carro;
    }

    public String getNome() {
        return nome;
    }

    public Carro getCarro() {
        return carro;
    }

    public String getSenha() {
        return senha;
    }

    public int getId() {
        return id;
    }

    public Integer getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", idade=" + idade +
                ", carro=" + carro.toString() +
                '}';
    }

    public String toStringSemCarro() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", idade=" + idade +
                '}';
    }
}
