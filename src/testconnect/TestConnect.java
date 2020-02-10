/**
 * @author: Alexandre PUTZU
 * @author: Baptiste BUSNOULT
 * @author: Joachim NEULET
 * @author: Quentin FOUGEREAU
 * @date: 04/02/2020, veille du 05 janvier 2020 et lendemain du 03 janvier 2020
 * @name: TestConnect.java
 */
package testconnect;

import bdd.Connect;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestConnect {

    String url;
    String user;
    String passwd;

    Connect c1;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("debut des tests");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("fin des tests");
    }

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("debut du test");
        Connect c1;
        String url;
        String user;
        String passwd;
    }

    @AfterEach
    void tearDown() throws Exception {
        System.out.println("fin du test");
    }

    @Test
    public void testConnectConstructor() {
        url = "url";
        user = "user";
        passwd = "passwd";
        c1 = new Connect(url, user, passwd);
        assertNotNull(c1);
    }

    @Test
    public void testConnectValid() throws SQLException {
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "mysql123";
        c1 = new Connect(url, user, passwd);
        c1.connect();
        assertTrue(c1.isConnected());
    }

    @Test
    public void testConnectWrongUrl() {
        url = "https://ent.univ-amu.fr/";
        user = "alexandre";
        passwd = "mysql123";
        c1 = new Connect(url, user, passwd);
        assertThrows(SQLException.class, () -> c1.connect());
    }

    @Test
    public void testConnectWrongPassword() {
        url = "jdbc:mysql://localhost/DBTest";
        user = "alexandre";
        passwd = "azertyuio";
        c1 = new Connect(url, user, passwd);
        assertThrows(SQLException.class, () -> c1.connect());
    }

    @Test
    public void testConnectWrongUser() {
        url = "jdbc:mysql://localhost/DBTest";
        user = "baptiste";
        passwd = "mysql123";
        c1 = new Connect(url, user, passwd);
        assertThrows(SQLException.class, () -> c1.connect());
    }

    @Test
    public void testDisconnect() {
        url ="jdbc:mysql://localhost/DBTest";
        user="alexandre";
        passwd = "mysql123";
        c1 = new Connect(url,user,passwd);
        try {
            c1.connect();
            c1.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertFalse(c1.isConnected());
    }


}
