package com.library.librarymanagement.configuration;

import com.library.librarymanagement.application.*;
import com.library.librarymanagement.domain.BookRepositoryPort;
import com.library.librarymanagement.domain.MemberRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterMemberUseCase registerMemberUseCase(MemberRepositoryPort memberRepositoryPort) {
        return new RegisterMemberUseCase(memberRepositoryPort);
    }

    @Bean
    public FindMemberByIdUseCase findMemberByIdUseCase(MemberRepositoryPort memberRepositoryPort) {
        return new FindMemberByIdUseCase(memberRepositoryPort);
    }

    @Bean
    public DeleteMemberByIdUseCase deleteMemberByIdUseCase(MemberRepositoryPort memberRepositoryPort) {
        return new DeleteMemberByIdUseCase(memberRepositoryPort);
    }

    @Bean
    public UpdateMemberUseCase updateMemberUseCase(MemberRepositoryPort memberRepositoryPort) {
        return new UpdateMemberUseCase(memberRepositoryPort);
    }

    @Bean
    public RegisterBookUseCase registerBookUseCase(BookRepositoryPort bookRepositoryPort) {
        return new RegisterBookUseCase(bookRepositoryPort);
    }

    @Bean
    public FindBookByIdUseCase findBookByIdUseCase(BookRepositoryPort bookRepositoryPort) {
        return new FindBookByIdUseCase(bookRepositoryPort);
    }

    @Bean
    public DeleteBookByIdUseCase deleteBookByIdUseCase(BookRepositoryPort bookRepositoryPort) {
        return new DeleteBookByIdUseCase(bookRepositoryPort);
    }
}
