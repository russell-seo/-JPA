package jpabook.jpashop.datajpa.jparepository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Member, Long> {

//    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);


    @Query("select m from Member m where m.name = :name and m.age = :age")
    List<Member> findUser(@Param("name") String name, @Param("age") int age);

    @Query("select m.name from Member m")
    List<Member> findUsernameList();

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findByName(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // clearAutomatically -> em.clear를 하는 속성이다.(영속성 컨텍스트 초기화)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m join fetch m.orders")
    List<Member> findMemberFetchJoin();

    @EntityGraph(attributePaths = ("orders"))
    @Query("select m from Member m")
    List<Member> findEntityGraph();

}
