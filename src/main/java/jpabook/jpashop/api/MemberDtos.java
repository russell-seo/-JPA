package jpabook.jpashop.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDtos {

    private String name;
    private int age;
}
