package org.zerock.domain;
//183p
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
	//481
	private int replyCnt;
	
	//552
	private List<BoardAttachVO> attachList;
}
