package com.digital.book.pojo;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookPojo {

    private Integer bookId;
    private String logo;
    private String title;
    private String category;
    private Double price;
    private String publisher;
    private Date publishDate;
    private Integer authorId;
    private boolean isActive;
}

