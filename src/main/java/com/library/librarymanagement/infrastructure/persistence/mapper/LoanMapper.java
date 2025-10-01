package com.library.librarymanagement.infrastructure.persistence.mapper;

import com.library.librarymanagement.domain.Loan;
import com.library.librarymanagement.infrastructure.persistence.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "member", ignore = true)  // we tell MapStruct to ignore both fields as they will be set manually
    @Mapping(target = "book", ignore = true)
    LoanEntity toEntity(Loan loan);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "book.bookId", target = "bookId")
    Loan toDomain(LoanEntity entity);
}
