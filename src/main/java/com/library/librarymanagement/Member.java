package com.library.librarymanagement;

import lombok.Data;

@Data   // Lombok annotation to generate getters, setters, constructor, etc.
public class Member {
    private String memberId;
    private String name;
    private String email;
}
