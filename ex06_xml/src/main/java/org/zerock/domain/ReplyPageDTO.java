package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
	private int replyCnt; //댓글수
	private List<ReplyVO> list; //댓글
}
