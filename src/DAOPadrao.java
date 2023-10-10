import java.sql.Connection;

abstract class DAOPadrao<T, ID> implements ICRUD<T,ID> {
    protected Connection connection;
    protected String comandoSql;

    public DAOPadrao(Connection connection){
        this.connection = connection;
    }
}
