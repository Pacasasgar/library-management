package com.library.librarymanagement.configuration;

import com.library.librarymanagement.application.RegisterMemberUseCase;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterMemberUseCase registerMemberUseCase(MemberRepositoryPort memberRepositoryPort){
        return new RegisterMemberUseCase(memberRepositoryPort);
    }
}
