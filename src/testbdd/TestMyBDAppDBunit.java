package testbdd;
/**
 * @author: Alexandre PUTZU
 * @author: Baptiste BUSNOULT
 * @author: Joachim NEULET
 * @author: Quentin FOUGEREAU
 * @date: 04/02/2020, veille du 05 février 2020 et lendemain du 03 février 2020
 * @name: testbdd.TestMyBDAppDBunit.java
 */

import bdd.MyBDApp;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

import java.io.File;
import java.io.FileInputStream;
import java.util.TimeZone;

public class TestMyBDAppDBunit extends DBTestCase {

    public final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public final String URL = "jdbc:mysql://localhost/DBTest?serverTimezone=" + TimeZone.getDefault().getID();
    public final String USERNAME = "root";
    public final String PASSWORD = "root";
    public final String SCHEMA = "DBTest";

    public TestMyBDAppDBunit(String name) {
        super(name);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, URL);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USERNAME);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, SCHEMA);
    }

    @Override
    protected IDatabaseTester getDatabaseTester() throws Exception {
        return super.getDatabaseTester();
    }

    /**
     * Override method to set custom properties/features
     */
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        DefaultColumnFilter columnFilter = new DefaultColumnFilter();
        columnFilter.includeColumn("id"); // telling DBUnit to treat the named column as primary key
        config.setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, columnFilter);
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("xml/dataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    public void testInsertXML() {
        try {
            IDataSet expected = new FlatXmlDataSetBuilder().build(new FileInputStream("xml/datasetExpected.xml"));
            IDataSet actual = getDataSet();
            Assertion.assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testInsertRow() throws Exception {
        File dataSetFile = new File("xml/datasetExpectedInsert.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(dataSetFile);

        MyBDApp myBDApp = new MyBDApp(URL, USERNAME, PASSWORD);
        myBDApp.insert("henry");

        IDataSet dataSetExtracted = getConnection().createDataSet();

        Assertion.assertEqualsIgnoreCols(expectedDataSet, dataSetExtracted, "personne", new String[] {"id"});
    }

    public void testDeleteRow() throws Exception {
        File dataSetFile = new File("xml/datasetExpectedRemove.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(dataSetFile);

        MyBDApp myBDApp = new MyBDApp(URL, USERNAME, PASSWORD);
        myBDApp.remove("marc");

        IDataSet dataSetExtracted = getConnection().createDataSet();

        Assertion.assertEqualsIgnoreCols(expectedDataSet, dataSetExtracted, "personne", new String[] {"id"});
    }

    public void testSelectRowById() throws Exception {
        File dataSetFile = new File("xml/datasetExpectedSelect.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(dataSetFile);

        MyBDApp myBDApp = new MyBDApp(URL, USERNAME, PASSWORD);
        myBDApp.select(2);

        IDataSet dataSetExtracted = getConnection().createDataSet();

        assertEquals(expectedDataSet.getTable("personne").getValue(0, "nom"), dataSetExtracted.getTable("personne").getValue(1, "nom"));
    }


}