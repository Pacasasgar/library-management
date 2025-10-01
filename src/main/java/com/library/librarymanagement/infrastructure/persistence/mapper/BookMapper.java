package com.library.librarymanagement.infrastructure.persistence.mapper;

import com.library.librarymanagement.domain.Book;
import com.library.librarymanagement.infrastructure.persistence.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookEntity toEntity(Book book);
    Book toDomain(BookEntity entity);
}
