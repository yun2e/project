package org.zerock.service;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//474
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTxServiceTests {
	
	@Setter(onMethod_ = @Autowired)
	private SampleTxService service;
	
	@Test
	public void testLong() { //오류가 발생하는게 맞음. 결과적으로 데이터베이스에 값이 둘다 못들어감
		String str = "Starry\r\n" + "Starry night\r\n" + "Paint your palette blue and grey\r\n" +
					"Look out on a summer's day";
		
		log.info(str.getBytes().length);
		service.addData(str);
	}

}
