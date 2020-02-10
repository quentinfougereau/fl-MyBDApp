package bdd;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.TimeZone;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mysql.MySqlMetadataHandler;

/*
 * classe permettant de créer un fichier Xml image de la base de données
 */
public class DBExtracteur {
	
	public DBExtracteur(String driver, String url, String user, String passwd, String xmlDataSetName)  {
		try {
			//se connecter à la base source
			Class.forName(driver).newInstance();
			Connection jdbcConnection = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connected as USER: " + user + " with: URL: " + url);
            
			//creer la connection DbUnit	
			IDatabaseConnection dbConnection = new DatabaseConnection(jdbcConnection, "DBTest" );
			dbConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
			
			//créer le fichier xml
			IDataSet fullDataSet = dbConnection.createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream(xmlDataSetName));
			System.out.println("fichiers générés");

		} catch (Exception e) {
			System.out.println("Connection failed as USER :" + user + " with: URL: "+ url);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost/DBTest?serverTimezone=" + TimeZone.getDefault().getID();
		String  user = "root";
		String passwd = "root";
		String xmlDataSetName = "xml/fulldataset.xml";
		DBExtracteur dbExtracteur = new DBExtracteur(driver, url, user, passwd, xmlDataSetName);		
	}
}
