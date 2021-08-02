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
       
    ex)
      Member member = new Member();
    
      System.out.println(member.getOrders().getClass());
    
      em.persist(team);

      System.out.println(member.getOrders().getClass());

      //출력 결과

      class java.util.ArrayList

      class org.hibernate.collection.internal.PersistentBag
    
  
  D-2
   <EntityManager 와 @RequiredArgsConstructor>
   
   ![image](https://user-images.githubusercontent.com/79154652/127878782-a474cc9f-3669-4d8b-aa9f-ae2ffa1a9bc1.png)
   
      
    
    
     
    EntityManager는 원래 @PersistenceContext로 주입을 받지만
    
    Spring Data JPA에서 @Autowired를 지원해주기 때문에 EntityManager를 final키워드 선언해주어 생성자 주입이 가능
    
    final 키워드 추가로 컴파일 시점에 memberRepository를 설정하지 않는 오류 체크 가능
    
    생성자가 하나면, @Autowired 생략 가능
    
    @RequiredArgsConstructor 사용
    
   <Transactional>
  
  ![image](https://user-images.githubusercontent.com/79154652/127879849-8dd485df-aec8-4721-a8db-0f3a3ff60b00.png)

  
  @Transactional : 트랜잭션, 영속성 컨텍스트
    1)readOnly=true : 데이터의 변경이 없는 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상!
    2)데이터베이스 드라이버가 지원하면 DB에서 성능 향상

    
    
