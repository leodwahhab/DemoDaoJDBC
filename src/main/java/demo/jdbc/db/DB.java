package demo.jdbc.db;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;

public class DB {
    private static Connection conn = null;

    public DB() {
        criarConexao();
    }

    public Connection criarConexao() {
        try {
            if(conn == null) {
                Properties props = carregarPropriedades();
                conn = DriverManager.getConnection(
                        props.getProperty("dburl"), props
                );
            }
            return conn;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void fecharConexao() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static Properties carregarPropriedades() {
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void consultar() {
        Statement stmt = null;
        ResultSet rs = null;

        try(Connection conn = this.criarConexao()) {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from department");

            while(rs.next()) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void inserir() {
        PreparedStatement ps = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try(Connection conn = this.criarConexao()) {
            ps = conn.prepareStatement(
                "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                    "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, "Leonardo");
            ps.setString(2, "leo@mail.com");
            ps.setDate(3, new java.sql.Date(sdf.parse("12/01/2005").getTime()));
            ps.setDouble(4, 1500);
            ps.setInt(5, new Random().nextInt(1, 4));

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0) {
                System.out.println("Done! ");
                ResultSet rs = ps.getGeneratedKeys();
                while(rs.next())
                    System.out.println("id: " + rs.getInt(1));
            }
            else
                System.out.println("nenhuma linha foi afetada.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void atualizar() {
        PreparedStatement ps = null;

        try(Connection conn = this.criarConexao()) {
            ps = conn.prepareStatement(
                    "UPDATE seller " +
                            "SET BaseSalary = BaseSalary + ? " +
                            "WHERE (DepartmentId = ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setDouble(1, 200);
            ps.setInt(2, 2);

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0)
                System.out.println("Done! linhas afetadas: " + affectedRows);
            else
                System.out.println("nenhuma linha foi afetada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar() {
        PreparedStatement ps = null;

        try(Connection conn = this.criarConexao()) {
            ps = conn.prepareStatement(
                    "DELETE FROM seller " +
                            "WHERE Id = ?"
                    );

            ps.setInt(1, 11);

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0)
                System.out.println("done! affected rows: " + affectedRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
