package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //조회할 때만 readOnly = true 를 걸어주면 읽기전용
@RequiredArgsConstructor // final 필드값의 생성자를 생성시켜 줌
public class MemberService {


    private final MemberRepository memberRepository; //못 바꾸기 때문에 단점 O



    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    //회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //한 건 조회

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
