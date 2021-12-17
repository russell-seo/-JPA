package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    public CreatememberResponse saveMemeberV1(@RequestBody Member member){
        Long id = memberService.join(member);
        return new CreatememberResponse(id);
    }

    @GetMapping("/api/v1/members") //안좋은예 -> 절대 Entity를 외부에 노출하면 안됨됨
   public List<Member> findMember(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result findMembers(){
        List<MemberDto> collect = memberService.findMembers().stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/v2/members")
    public CreatememberResponse saveMember2(@RequestBody CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreatememberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member one = memberService.findOne(id);
        return new UpdateResponse(one.getId(), one.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateResponse{
        private Long id;
        private String name;
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
