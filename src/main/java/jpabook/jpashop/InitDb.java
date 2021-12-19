package jpabook.jpashop;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){

            Member member = createMember("서상원", "강남구","1", "1111");
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2");
            book2.setPrice(20000);
            book2.setStockQuantity(200);
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem1 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);

        }



        public void dbInit2(){

            Member member = createMember("이채영", "서울", "강동구", "2222");
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring");
            book1.setPrice(30000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("Spring2");
            book2.setPrice(40000);
            book2.setStockQuantity(200);
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem1 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);

        }

        private Member createMember(String name, String city, String s, String s2) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, s,s2));
            return member;
        }
    }
}
