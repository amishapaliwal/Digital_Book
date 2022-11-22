package com.digital.book.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthorPojo {

    private String authorName;
    private String email;
    private String password;
}
