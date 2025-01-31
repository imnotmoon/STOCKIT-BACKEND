package Stockit.order.domain;

import Stockit.member.domain.Member;
import Stockit.order.dto.OrderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderIdx;

    private int stockOrderPrice; //주문 가격
    private int stockOrderCount; //주문 수량

    @CreatedDate
    private LocalDateTime stockOrderedDate; //주문 시각

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //미체결중인 것이 있는지 여부

    @ManyToOne //N쪽이므로 owner객체, 양방향 N:1
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne //단방향 N:1
    @JoinColumn(name = "stock_code")
    private Stock stock;

    ///////////////////////////////////////
    public Order(Member member, Stock stock, OrderDto orderDto) {
        this.member = member;
        this.stock = stock;
        this.stockOrderPrice = orderDto.getStockOrderPrice();
        this.stockOrderCount = orderDto.getStockOrderCount();
        this.status = OrderStatus.NOT_ACCEPTED;
    }
    //////////////////////////////////////
    public static Order createOrder(Member member, Stock stock, OrderDto orderDto) {
        return new Order(member, stock, orderDto);
    }

}
