package jpabook.jpashop.datajpa.jparepository;

import jpabook.jpashop.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback(value = false)
public class SpringDataJpaTest {

    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void bulkUpdate(){

        //given
        userRepository.save(new Member("서상원1", 10));
        userRepository.save(new Member("서상원2", 20));
        userRepository.save(new Member("서상원3", 30));
        userRepository.save(new Member("서상원4", 40));
        userRepository.save(new Member("서상원5", 50));

        //when
        int reCount = userRepository.bulkAgePlus(20); //DB에는 벌크성 업데이트가 되었는데 영속성 컨텍스트에서는 아직 남아있다. 즉 flush, clear를 해야한다.
//        em.flush();
//        em.clear();

        //then

    }

    @Test
    public void AuditingTest() throws InterruptedException {
        //given
        Member member = new Member("member1", 10);
        userRepository.save(member); // @PrePersist가 실행됨

        Thread.sleep(100);
        member.setName("member2");

        em.flush();//@PreUpdate
        em.clear();
        //when
        Member member1 = userRepository.findById(member.getId()).get();
        //then
        System.out.println("member1.getCreatedDate() = " + member1.getCreatedDate());
        System.out.println("member1.getUpdatedDate() = " + member1.getUpdatedDate());
    }
}
