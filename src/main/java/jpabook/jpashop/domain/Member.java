package jpabook.jpashop.domain;


import jpabook.jpashop.datajpa.entity.BaseEntity;
import jpabook.jpashop.datajpa.entity.JpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity   {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    private int age;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member() {
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }


}
