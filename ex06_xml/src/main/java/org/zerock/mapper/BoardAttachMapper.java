package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardAttachVO;

//552
public interface BoardAttachMapper {

	@Insert("INSERT INTO tbl_attach (uuid, uploadpath, filename,filetype, bno) values (#{uuid},#{uploadPath},#{fileName},#{fileType},#{bno})")
	public void insert(BoardAttachVO vo);

	@Delete("delete from tbl_attach where uuid = #{uuid}")
	public void delete(String uuid);
	
	@Select("select * from tbl_attach where bno = #{bno}")
	public List<BoardAttachVO> findByBno(Long bno);
	
	@Delete("delete from tbl_attach where bno = #{bno}")
	public void deleteAll(Long bno);
	
	@Select("select * from tbl_attach where uploadpath = to_char(sysdate -1, 'yyyy/mm/dd')")
	public List<BoardAttachVO> getOldFiles();
}
