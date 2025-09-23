package com.library.librarymanagement.infrastructure.web;

import com.library.librarymanagement.application.DeleteMemberByIdUseCase;
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
    private final DeleteMemberByIdUseCase deleteMemberByIdUseCase;

    public MemberController(RegisterMemberUseCase registerMemberUseCase,
                            FindMemberByIdUseCase findMemberByIdUseCase,
                            DeleteMemberByIdUseCase deleteMemberByIdUseCase){
        this.registerMemberUseCase = registerMemberUseCase;
        this.findMemberByIdUseCase = findMemberByIdUseCase;
        this.deleteMemberByIdUseCase = deleteMemberByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody CreateMemberRequest request) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String memberId) {
        deleteMemberByIdUseCase.execute(memberId);
        return ResponseEntity.noContent().build();
    }
}
