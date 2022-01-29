package com.example.core.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.domain.member.Member;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;
import com.example.core.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public String showMemberList(Model model) {
        List<MemberDTO> memberDTOs = memberService.findAll();
        model.addAttribute("memberDTOs", memberDTOs);
        return "members/members"; //해당 파일은 for문을 돌려서 존재하는 memberDTO들을 다 보여준다.
        // 각 회원 옆에는 등급변경 버튼 있음. 누르면 Patch 실행.
    }

    @GetMapping("/members/sign-up")
    public String signUpForm(Model model) {
        MemberForm memberForm = new MemberForm(null, null, null); // TODO 설계가 잘못되었구나
        model.addAttribute("memberForm", memberForm);
        return "members/sign-up"; //해당 파일은 회원가입 form이 들어있다. 등록버튼 누르면 Post, 취소 누르면 redirect해서 /members로
    }

    @PostMapping("/members/sign-up")
    public String signUp(MemberForm memberForm) { //form이 날아오면 자동으로 modelAttribute가 됨.
        Long joinMemberId = memberService.join(memberForm);
        System.out.println("joinId: " + joinMemberId);
        return "redirect:/members"; //TODO 이거 바꿔야함. 밑에거로
        // return "redirect:/members/" + joinMemberId; // 가입된 녀석의 세부정보를 보여줌.
    }

    @GetMapping("/members/{id}")
    public String memberInfo(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.lookUp(id);
        model.addAttribute("memberDTO", memberDTO);
        return "members/member-info"; // 해당 파일은 model을 통해 넣은 memberDTO를 전부 보여준다. 홈으로 돌아가는 버튼이 필요.
    }

    @PatchMapping("/members/{id}") // /members?id=1 이런식으로 사용
    public String changeGrade(@PathVariable Long id) {
        memberService.modifyGrade(id);
        return "redirect:/members";
    }

    //service 계층에 수정하는 메서드를 만들지 않음.
    // @GetMapping("members/{id}/modify") //동사 쓰면 안되는데...
    // public String memberForm(@RequestParam Long id, Model model) {
    //     MemberDTO memberDTO = memberService.lookUp(id);
    //     //memberDTO -> memberForm 바꿔주고 넣어야할듯. 그래야 안에서 수정이 가능하지.
    //     model.addAttribute("memberDTO", memberDTO);
    //     return "members/member-modify-form";
    // }
    //
    // @PostMapping("/members/{id}/modify")
    // public String modifyInfo(@RequestParam Long id, MemberForm memberForm, Model model) {
    //     // memberService에서 수정하는 메서드를 꺼내온다.
    //     return "redirect:/members/" + id;
    // }
}
