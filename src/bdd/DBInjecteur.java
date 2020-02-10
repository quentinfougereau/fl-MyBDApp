package bdd;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.TimeZone;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
/*
 * permet d'injecter un fichier xml dans la base de donnée
 * spécifiée 
 */
public class DBInjecteur {

	public static void main(String[] args) {
		/*attributs pour la base de données*/
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost/DBTest?serverTimezone=" + TimeZone.getDefault().getID();
		String  user = "root";
		String passwd = "root";

		try {
			//se connecter à la base source
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connected as USER: " + user + " with: URL: " + url);

			//creer la connection DbUnit	
			IDatabaseConnection dbConnection = new DatabaseConnection(connection, "DBTest" );
			dbConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());

			//injecter un fichier xml appelé newdataset.xml
			File dataSetFile = new File("xml/fulldataset.xml");
			IDataSet newDataSet = new FlatXmlDataSetBuilder().build(dataSetFile);
			ReplacementDataSet replacementDataSet = new ReplacementDataSet(newDataSet);
			DatabaseOperation.CLEAN_INSERT.execute(dbConnection, replacementDataSet);
            //signaler que tout s'est bien passé
			System.out.println("fichier injecté");

		} catch (Exception e) {
			System.out.println("Connection failed as USER :" + user + " with: URL: "+ url);
			e.printStackTrace();
		}
	}
}
