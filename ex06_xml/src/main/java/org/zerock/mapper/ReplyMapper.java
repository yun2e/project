package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
//인터페이스 사용 이유 : 일관성과 관계성을 편하게 하기 위해서
public interface ReplyMapper {
	public int insert(ReplyVO vo);
	
	public ReplyVO read(Long bno);
	
	public int delete (Long bno);
	
	public int update(ReplyVO reply);
	
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
	public int getCountByBno(Long bno);
}
