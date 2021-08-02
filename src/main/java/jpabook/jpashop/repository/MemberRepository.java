package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;


    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);  //단건 조회 = 첫번째는 타입(Member.class), 두번쨰는 PK(Primary Key)
    }

    public List<Member> findAll(){

        return em.createQuery("select m from Member m", Member.class)  //괄호 안에 첫번째는 JPQL, 2번쨰는 반환 타입
                .getResultList();     //쿼리 JPQL의 from 대상은 테이블이 아니라 Entity임
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
