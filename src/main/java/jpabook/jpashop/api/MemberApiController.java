package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    public CreatememberResponse saveMemeberV1(@RequestBody Member member){
        Long id = memberService.join(member);
        return new CreatememberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreatememberResponse saveMember2(@RequestBody CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreatememberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreatememberResponse {
        private Long id;

        public CreatememberResponse(Long id) {
            this.id = id;
        }
    }
}
