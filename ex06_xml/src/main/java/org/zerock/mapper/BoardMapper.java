package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	//방법1 : xml을 사용하지 않는 방법
	//@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	
	//294p
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);
	
	//322p
	public int getTotalCount(Criteria cri);
	
	//482
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
