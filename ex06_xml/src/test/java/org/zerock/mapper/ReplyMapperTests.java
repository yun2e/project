package org.zerock.mapper;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	//테스트 전에 해당 번호의 게시물이 존재하는지 반드시 확인해야함
	private Long[] bnoArr = {1071L, 1070L, 1069L, 1068L, 1067L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//383
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("replyer"+i);
			
			mapper.insert(vo);
		});
	}
	
	//385
	@Test
	public void testRead() {
		Long targetRno = 5L; //5번째 댓글
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}
	
	//386
	@Test
	public void testDelete() {
		Long targetRno = 2L; //n번째 댓글
		mapper.delete(targetRno);
	}
	
	//387
	@Test
	public void testUpdate() {
		Long targetRno = 10L; //10번째 댓글
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT : " + count);
	}
	
	//388
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		//1071L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	//431
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 10);
		
		//1071L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 1070L);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}

}
