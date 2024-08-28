package net.datasa.test.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 리플 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	Integer replyNum;
	Integer boardNum;
	String memberId;
	String replyText;
}
