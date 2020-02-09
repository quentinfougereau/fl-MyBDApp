package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*classe de connection à une base de donnée mysql locale
 *Les opérations de cette classes ne servent qu'à la connection
 *pour mieux séparer connection/deconnection des opérations
 *sur la BD et donc de bien identifier les tests.
 */
public class Connect {
	/*la connection */
	private Connection connection = null;
	/*driver à choisir selon le type de la base mysql, posgres,...*/
	private  String driver = "com.mysql.cj.jdbc.Driver";
	private  String url;
	private   String user; 
	private   String passwd;

	public Connect(String url, String user, String passwd){
		this.url = url;
		this.user = user;
		this.passwd = passwd;  
	}

	public void connect() throws SQLException {
		this.connection = this.getConnection();
	}
	
	public Connection getConnection() throws SQLException { 
		Connection connection = null;
		try {
			Class.forName(this.driver);
			connection =  DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/* test simple pour vérifier la connection */
	public boolean isConnected() {
		return this.connection != null;
	}

	/* Se deconnecter de la base proprement */
	public void disconnect() throws SQLException {
		this.connection.close();
		this.connection = null;
	}

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost/DBTest";
		String user = "root";
		String passwd = "root";
		Connect connect = new Connect(url, user, passwd);
		connect.connect(); 
		if (connect.connection != null) {
			System.out.println("connecté à :"+ url+ " comme :" + user + " avec :"+  passwd);
		} else {
			System.out.println("echec de conenction:"+ url+ " comme :" + user + " avec :"+  passwd);
		}
		if (connect.connection != null) {
		connect.disconnect();
		System.out.println("deconnecté");
		}
	}
}
	

