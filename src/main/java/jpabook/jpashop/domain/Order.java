package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==// 양방향 일 경우 쓰면 양쪽에 모두 값 저장가능하게 하는 메소드
    public void setMemeber(Member memeber){
        this.member=member;
        member.getOrders().add(this);

    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems=orderItems;
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
                delivery.setOrder(this);
    }




}
