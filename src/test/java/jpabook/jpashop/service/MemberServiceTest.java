package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // junit 실행 할때 스프링이랑 같이 실행하는 코드 >> junit4일때
@Transactional // Test 에서만 마지막에 Rollback 함
@SpringBootTest//스프링 부트를 테스트에서 띄우기 위해 Autowired 써야되니깐
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        Assertions.assertEquals(member, memberService.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // try catch 대신 어노테이션으로 예외를 잡음
    public void 회원조회() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 터져야 함함
       //then
        Assert.fail("예외가 발생해야함");
    }

}
