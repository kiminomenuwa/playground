package net.datasa.test.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 판매글 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	Integer boardNum;
	String memberId;
	String category;
	String title;
	String contents;
	Integer price;
	Boolean soldout;
	String buyer;
	LocalDateTime inputDate;
	
	private List<ReplyDTO> replyList;

}
