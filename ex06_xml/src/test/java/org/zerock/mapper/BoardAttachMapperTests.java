package org.zerock.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardAttachMapperTests {

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Test
	public void testInsert() {
		BoardAttachVO vo = new BoardAttachVO();
		vo.setFileName("고양이");
		vo.setUploadPath("2020");
		vo.setUuid("1111");
		vo.setBno(1105L);
		attachMapper.insert(vo);
		log.info(vo);
	}

}
