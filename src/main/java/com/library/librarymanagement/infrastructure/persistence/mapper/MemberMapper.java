package com.library.librarymanagement.infrastructure.persistence.mapper;

import com.library.librarymanagement.domain.Member;
import com.library.librarymanagement.infrastructure.persistence.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberEntity toEntity(Member member);
    Member toDomain(MemberEntity entity);
}
