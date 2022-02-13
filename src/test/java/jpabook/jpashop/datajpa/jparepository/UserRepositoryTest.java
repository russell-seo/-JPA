package jpabook.jpashop.datajpa.jparepository;

import jpabook.jpashop.api.MemberApiController;
import jpabook.jpashop.api.MemberDtos;
import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByNames(){
        Member member = new Member("서상원", 10);
        Member member2 = new Member("서상원2", 29);
        userRepository.save(member);
        userRepository.save(member2);



        List<Member> byNames = userRepository.findByNames(Arrays.asList("서상원", "서상원2"));
        for (Member byName : byNames) {
            System.out.println("byName = " + byName);
        }

    }


    @Test
    public void 페이징테스트(){
        //given
        userRepository.save(new Member("서상원1", 10));
        userRepository.save(new Member("서상원2", 20));
        userRepository.save(new Member("서상원3", 30));
        userRepository.save(new Member("서상원4", 40));
        userRepository.save(new Member("서상원5", 50));

        //when

        int age = 10;
        PageRequest pa = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));


        //then
        Page<Member> page = userRepository.findByAge(10, pa);

        Page<MemberDtos> pages = page.map(m -> new MemberDtos(m.getName(), m.getAge()));


        page.getTotalElements(); //전체 데이터수
        page.getContent();//조회한 데이터
        page.getNumber();//페이지 번호
        page.getTotalPages();//전체 페이지 번호
        page.hasNext();//다음 페이지가 있는가?
        page.isFirst();//첫번째 항목인가?
    }

}