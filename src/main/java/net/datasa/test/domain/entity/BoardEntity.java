package net.datasa.test.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 판매글 Entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "market_board")
public class BoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="board_num")
	private Integer boardNum;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;
    
    @Column(name = "category", nullable = false, length = 50)
	private String category;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "contents", nullable = false, length = 2000)
    private String contents;
    
    @Column(name = "price", columnDefinition = "int default 0")
    private Integer price = 0;
    
    @Column(name = "soldout", columnDefinition = "tinyint(1) default 0 check(soldout in (0, 1))")
    private Boolean soldout = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "member_id")
    private MemberEntity buyer;
    
    @CreatedDate
    @Column(name = "input_date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime inputDate;
	
	
}
