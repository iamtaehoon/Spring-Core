package com.example.core.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;
import com.example.core.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/sign-up")
    public String signUpForm(Model model) {
        MemberForm memberForm = new MemberForm(null, null, null); // TODO 설계가 잘못되었구나
        model.addAttribute("memberForm", memberForm);
        return null;//resources에 있는 파일 주소를 넣어준다.
    }


    @GetMapping("/members/{id}")
    public String memberForm(@RequestParam Long id, Model model) {
        MemberDTO memberDTO = memberService.lookUp(id);
        model.addAttribute("memberDTO", memberDTO);
        return null; //
    }

    @PatchMapping("/members/{id}")//id 변수 어떻게 넣더라
    public String changeGrade(@RequestParam Long id) {
        memberService.modifyGrade(id);
        return "redirect:/members/" + id; //원래페이지로~
    }

    @PostMapping("/members/{id}")
    public String modifyInfo(@RequestParam Long id, MemberForm memberForm, Model model) {
        //model에서 꺼내옴.
        // memberService에서 수정하는 메서드를 꺼내온다.
        return null;
    }
}
