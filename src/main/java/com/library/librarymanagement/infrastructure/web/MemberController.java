package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.infrastructure.web.dto.CreateMemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;

    public MemberController(RegisterMemberUseCase registerMemberUseCase){
        this.registerMemberUseCase = registerMemberUseCase;
    }

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody CreateMemberRequest request){
        Member newMember = registerMemberUseCase.execute(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }
}
