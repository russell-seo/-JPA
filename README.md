# -JPA
Entity 설계 시 주의점

1.Entity는 가급적 Setter를 사용하지 말자

2. 모든 연관관계는 지연로딩으로 설정!(중요)
  (1). 즉시 로딩(FetchType.EAGER)은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다. 특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다.
  (2). 실무에서 모든 연관관계는 지연로딩(FetchType.LAZY)으로 설정해야 한다.
  (3). 연관된 Entity를 함께 DB에서 조회해야 하면, fetch join 또는 Entity 그래프 기능을 사용한다.
  (4). @XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정
        ex)ManyToOne(fetch = FetchType.LAZY)
3. 컬렉션은 필드에서 초기화 하자
    컬렉션은 필드에서 바로 초기화 하는 것이 안전한다.
   (1).null 문제에서 안전하다.
   (2).Hibernate는 Entity를 영속화 할 때, 컬렉션을 감싸서 Hibernate가 제공하는 내장 컬렉션으로 변경한다.
       만약getOrder()처럼 임의의 메서드에서 컬렉션을 잘못 생성하면 하이버네이트 내부 매커니즘에 문제가 발생.
       따라서 필드레벨에서 생성하는 것이 가장 안전
    Member member = new Member();
    System.out.println(member.getOrders().getClass());
    em.persist(team);
    System.out.println(member.getOrders().getClass());
    //출력 결과
    class java.util.ArrayList
    class org.hibernate.collection.internal.PersistentBag
    
    
