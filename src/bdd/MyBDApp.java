/**
 * @author: Alexandre PUTZU
 * @author: Baptiste BUSNOULT
 * @author: Joachim NEULET
 * @author: Quentin FOUGEREAU
 * @date: 04/02/2020, veille du 05 février 2020 et lendemain du 03 février 2020
 * @name: MyBDApp.java
 */

package bdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyBDApp {

    static Connect connect;

    public MyBDApp(String url, String user, String passwd) {
        connect = new Connect(url, user, passwd);
        try {
            connect.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost/DBTest";
        String user = "alexandre";
        String passwd = "mysql123";
        MyBDApp bdApp = new MyBDApp(url, user, passwd);
        bdApp.remove("Rebecca");
        bdApp.disconnection();
    }


    public void insert(String nom) throws SQLException {
        Statement statement = connect.getConnection().createStatement();
        String sql = "INSERT INTO personne(nom) VALUES ('" + nom + "')";
        statement.executeUpdate(sql);
    }

    public void remove(String nom) throws SQLException {
        Statement statement = connect.getConnection().createStatement();
        String sql = "DELETE FROM personne WHERE nom = '" + nom + "'";
        statement.executeUpdate(sql);
    }

    public String select(int id) throws SQLException {

        Statement statement = connect.getConnection().createStatement();

        String sql = "SELECT nom FROM personne WHERE id = '" + id + "'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        String lastName = rs.getString("nom");
        System.out.println(lastName);
        return lastName;
    }

    public ArrayList<String> selectAll() throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        Statement statement = connect.getConnection().createStatement();

        String sql = "SELECT nom FROM personne";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            result.add(rs.getString("nom"));
        }

        return result;
    }

    public void disconnection() throws SQLException {
        if (connect.isConnected()) {
            connect.disconnect();
        }
    }

}
