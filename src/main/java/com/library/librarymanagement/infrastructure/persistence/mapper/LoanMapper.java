package com.library.librarymanagement.infrastructure.persistence.mapper;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.persistence.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanEntity toEntity(Loan loan);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "book.bookId", target = "bookId")
    Loan toDomain(LoanEntity entity);
}
