package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

// 연습용
public class LoadXMLFile {

	public static void main(String[] args) {

		// XML 파일 읽어오기 (FileInputStream, Properties)

		FileInputStream fis = null;
		Connection conn = null;

		try {
			
			Properties prop = new Properties();
			
			// driver.xml 파일을 읽기 위한 InputSream 객체 생성
			fis = new FileInputStream("driver.xml");
			
			// 연결된 driver.xml 파일에 있는 내용을 모두 읽어와
			// properties 객체에 K:V 형식으로 저장
			prop.loadFromXML(fis);
			
			// prop.getproperty("key") : key가 일치하는 속성값(value)을 얻어옴
			/* key 		  : 		    value
			 * driver     : oracle.jdbc.driver.OracleDriver
			 * url		  : jdbc:oracle:thin:@localhost:1521:XE
			 * userName   : kh_pyj
			 * password   : kh1234
			 * */ 
			
			String driver = prop.getProperty("driver");
			// oracle.jdbc.driver.OracleDriver
			
			String url = prop.getProperty("url");
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String userName = prop.getProperty("userName");
			// kh_pyj
			
			String password = prop.getProperty("password");
			// kh1234
			
			Class.forName(driver); 
			// (== Class.forname(oracle.jdbc.driver.OracleDriver))
			
			conn = DriverManager.getConnection(url, userName, password);
			// (== conn = DriverManager.getConnection(jdbc:oracle:thin:@localhost:1521:XE, kh_pyj, kh1234))
			
			System.out.println(conn);
			// oracle.jdbc.driver.T4CConnection@29176cc1

		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {

			try {
				
				if (fis != null) fis.close();
				if(conn != null) conn.close();
				
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

}
