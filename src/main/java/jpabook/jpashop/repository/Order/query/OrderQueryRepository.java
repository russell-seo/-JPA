package jpabook.jpashop.repository.Order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQuery(){
        List<OrderQueryDto> orderQueryDtos = findOrders();
        orderQueryDtos.stream()
                      .forEach(o -> {
            List<OrderItemQueryDto> orderItemsDto = findOrderItemsDto(o.getOrderId());
            o.setOrderItems(orderItemsDto);
        });
        return orderQueryDtos;
    }




    public List<OrderQueryDto> findOrders(){

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

    public List<OrderQueryDto> findAllByDto_optiomization() {
        List<OrderQueryDto> orders = findOrders(); // 루트 조회 (toOne 코드를 모두 한번에 조회)

        List<Long> orderids = orders.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList()); // orders 루프 돌면서 주문 id값만 가져오기

        //orderids를 파라미터로 where 절의 in 으로 한번에 가져오기
        List<OrderItemQueryDto> orderItems = em.createQuery("select new "
                                    + "jpabook.jpashop.repository.Order.query.OrderItemQueryDto(o.id, o.item.name, o.orderPrice, o.count) from OrderItem o"
                                    + " join o.item i"
                                    + " where o.order.id in : orderids", OrderItemQueryDto.class)
                .setParameter("orderids", orderids)
                .getResultList();

        //map으로 만들기 핵심은 메모리 Map에 올려두고 찾는것.
        Map<Long, List<OrderItemQueryDto>> orderItemMap =
                orderItems.stream().collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        //루프 돌면서 컬렉션 추가(추가 쿼리 실행 x)
        orders.forEach(x -> x.setOrderItems(orderItemMap.get(x.getOrderId())));

        return orders;
    }
}
