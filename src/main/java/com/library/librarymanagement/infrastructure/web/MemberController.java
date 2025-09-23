package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.FindMemberByIdUseCase;
import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.infrastructure.web.dto.CreateMemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final FindMemberByIdUseCase findMemberByIdUseCase;

    public MemberController(RegisterMemberUseCase registerMemberUseCase, FindMemberByIdUseCase findMemberByIdUseCase){
        this.registerMemberUseCase = registerMemberUseCase;
        this.findMemberByIdUseCase = findMemberByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody CreateMemberRequest request){
        Member newMember = registerMemberUseCase.execute(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable("id") String memberId) {
        Optional<Member> memberOptional = findMemberByIdUseCase.execute(memberId);

        return memberOptional
                .map(member -> ResponseEntity.ok(member))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
