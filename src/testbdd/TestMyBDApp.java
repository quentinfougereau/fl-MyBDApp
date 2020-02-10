package testbdd; /**
 * @author: Alexandre PUTZU
 * @author: Baptiste BUSNOULT
 * @author: Joachim NEULET
 * @author: Quentin FOUGEREAU
 * @date: 04/02/2020, veille du 05 février 2020 et lendemain du 03 février 2020
 * @name: testbdd.TestMyBDApp.java
 */

import bdd.MyBDApp;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyBDApp {


    static MyBDApp myBDApp;
    static String url;
    static String user;
    static String passwd;

    String name1 = "Rebecca";
    String name2 = "Charles";

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("debut des tests");
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "mysql123";
        myBDApp = new MyBDApp(url,user,passwd);
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("fin des tests");
        myBDApp.disconnection();
    }

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("debut du test");


    }

    @AfterEach
    void tearDown() throws Exception {
        System.out.println("fin du test");
    }

    @Test
    public void testInsert() throws SQLException {

        try {
            myBDApp.insert(name1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals("Rebecca",myBDApp.select(7));
    }

    @Test
    public void testRemove() throws SQLException {
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "mysql123";
        myBDApp = new MyBDApp(url,user,passwd);

        try {
            myBDApp.remove("Rebecca");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertFalse(myBDApp.selectAll().contains("Rebecca"));
    }

    @Test
    public void testSelectIdNotExist(){
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "mysql123";
        myBDApp = new MyBDApp(url,user,passwd);

        assertThrows(SQLException.class , ()-> myBDApp.select(512));

    }

    @Test
    public void testSelectIdExist() throws SQLException {
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "mysql123";
        myBDApp = new MyBDApp(url,user,passwd);

        assertEquals("michel",myBDApp.select(1));
    }
}
