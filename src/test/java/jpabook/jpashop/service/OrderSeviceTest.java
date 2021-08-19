package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderSeviceTest {

    @Autowired
    EntityManager em;
    @Autowired OrderSevice orderSevice;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception{

        //given
        Member member = CreateMember();

        Book book = CreateBook("시골 JPA", 10000, 10);

        int orderCount = 2;
    //when
        Long orderid = orderSevice.order(member.getId(), book.getId(), orderCount);
        //then
        Order one = orderRepository.findOne(orderid);

        Assert.assertEquals(OrderStatus.ORDER, one.getStatus());
        Assert.assertEquals(1, one.getOrderItems().size());
        Assert.assertEquals(10000*orderCount, one.getTotalPrice());

    }



    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = CreateMember();
        Book item = CreateBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        //when
        Long orderid = orderSevice.order(member.getId(), item.getId(), orderCount);
        orderSevice.cancelOrder(orderid);

        //then
        Order getOrder = orderRepository.findOne(orderid);

        Assert.assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 재고가 증가", 10, item.getStockQuantity());


    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = CreateMember();
        Item item = CreateBook("시골 JPA",10000, 10);

        int orderCount = 11;
        //when
        orderSevice.order(member.getId(), item.getId(), orderCount);
        //then
//        fail("재고 수량 부족 예외가 발생해야 한다");

    }


    private Book CreateBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member CreateMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가","123-123"));
        em.persist(member);
        Item item = new Book();
        return member;
    }

}