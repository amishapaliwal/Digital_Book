package com.digital.book.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    private String logo;
    private String title;
    private String category;
    private Double price;
    private String publisher;
    private Date publishDate;
    private Integer authorId;
    private String authorName;
    private boolean isActive;

}
