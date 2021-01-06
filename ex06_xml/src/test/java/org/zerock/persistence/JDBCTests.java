package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	static {//static 초기화블럭 : 가장 먼저 실행
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		try(Connection con = DriverManager.getConnection(url,"spring","spring")){
			log.info(con);
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
