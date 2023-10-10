import java.sql.ResultSet;
import java.sql.SQLException;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private double preco;

    public Carro(int id, String marca, String modelo, int ano, double preco) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
    }

    public Carro(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.marca = rs.getString("marca");
        this.modelo = rs.getString("modelo");
        this.ano = rs.getInt("ano");
        this.preco = rs.getDouble("preco");
    }

    public Carro(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", preco=" + preco +
                '}';
    }
}
