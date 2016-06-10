package com.hui.maven.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class AbstractDbunitTestCase {
	public static IDatabaseConnection dbunitCon;
	private static File tempFile;
	
	@BeforeClass
	public static void init() throws DatabaseUnitException,SQLException {
		dbunitCon = new DatabaseConnection(DbUtil.getConnection());
	}
	
	protected IDataSet createDataSet(String tname) throws DataSetException {
		InputStream is = AbstractDbunitTestCase.class
						 .getClassLoader().getResourceAsStream(tname + ".xml");
		Assert.assertNotNull("dbunit的基本数据文件不存在", is);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));			
	}
	
	protected void backupAllTable() throws SQLException, DataSetException, IOException {
		IDataSet ds = dbunitCon.createDataSet();
		writeBackupFile(ds);
	}

	private void writeBackupFile(IDataSet ds) throws DataSetException, IOException {
		 tempFile = File.createTempFile("back", "xml");
		 FlatXmlDataSet.write(ds, new FileWriter(tempFile));		
	}
	
	protected void backupCustomTable(String[] tname) throws DataSetException, IOException {
		QueryDataSet ds = new QueryDataSet(dbunitCon);
		for(String str : tname) {
			ds.addTable(str);
		}
		writeBackupFile(ds);
	}
	
	protected void backupOneTable(String tname) throws DataSetException, IOException {
		backupCustomTable(new String[]{tname});
	}
	
	protected static void resumeTable() throws DatabaseUnitException, SQLException, FileNotFoundException {
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}	
	
	@AfterClass
	public static void destory() {
		if(dbunitCon != null)
			try {
				dbunitCon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}


