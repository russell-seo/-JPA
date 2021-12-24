package jpabook.jpashop.repository.Order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQuery(){
        List<OrderQueryDto> orderQueryDtos = findOrderQueryDtos();
        orderQueryDtos.stream()
                      .forEach(o -> {
            List<OrderItemQueryDto> orderItemsDto = findOrderItemsDto(o.getOrderId());
            o.setOrderItems(orderItemsDto);
        });
        return orderQueryDtos;
    }




    public List<OrderQueryDto> findOrderQueryDtos(){

        return em.createQuery(
                "select new " +
                        "jpabook.jpashop.repository.Order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class).getResultList();
    }

    public List<OrderItemQueryDto> findOrderItemsDto(Long orderId){
        return em.createQuery(
                "select new " +
                        "jpabook.jpashop.repository.Order.query.OrderItemQueryDto(o.id, o.item.name, o.orderPrice, o.count) from OrderItem o" +
                " join o.item i" +
                " where o.order.id = : orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
