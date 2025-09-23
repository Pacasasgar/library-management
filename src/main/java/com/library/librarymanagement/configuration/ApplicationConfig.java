package com.library.librarymanagement.configuration;

import com.library.librarymanagement.application.DeleteMemberByIdUseCase;
import com.library.librarymanagement.application.FindMemberByIdUseCase;
import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.application.UpdateMemberUseCase;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterMemberUseCase registerMemberUseCase(MemberRepositoryPort memberRepositoryPort){
        return new RegisterMemberUseCase(memberRepositoryPort);
    }

    @Bean
    public FindMemberByIdUseCase findMemberByIdUseCase(MemberRepositoryPort memberRepositoryPort){
        return new FindMemberByIdUseCase(memberRepositoryPort);
    }

    @Bean
    public DeleteMemberByIdUseCase deleteMemberByIdUseCase(MemberRepositoryPort memberRepositoryPort){
        return new DeleteMemberByIdUseCase(memberRepositoryPort);
    }

    @Bean
    public UpdateMemberUseCase updateMemberUseCase(MemberRepositoryPort memberRepositoryPort) {
        return new UpdateMemberUseCase(memberRepositoryPort);
    }
}
