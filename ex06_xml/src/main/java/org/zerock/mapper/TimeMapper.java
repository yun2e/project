package org.zerock.mapper;
//94p
import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	//Annotation
	@Select("select sysdate from dual")
	public String getTime();//추상메소드
	
	//Xml
	public String getTime2();//99p
}
