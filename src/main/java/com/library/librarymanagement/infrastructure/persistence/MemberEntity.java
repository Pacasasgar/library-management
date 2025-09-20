package com.library.librarymanagement.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MemberEntity {

    @Id
    private String memberId;
    private String name;
    private String email;
}
