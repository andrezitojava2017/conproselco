/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.conexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class Conexao {
     private static final String DRIVER = "com.mysql.jdbc.Driver";
    //private static final String URL = "jdbc:mysql://127.0.0.1:3306/assembleia?useTimezone=true&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/processos";
    private static final String USER = "root";
    private static final String PASS = "@agc171213";

    public static Connection getconection() throws SQLException {
        try {
            Class.forName(DRIVER);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void fecharConexao(Connection con) {

        try {

            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fecharConexao(Connection con, PreparedStatement stm) {

        fecharConexao(con);

        try {

            if (stm != null) {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fecharConexao(Connection con, PreparedStatement stm, ResultSet rs) {

        fecharConexao(con, stm);

        try {

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
