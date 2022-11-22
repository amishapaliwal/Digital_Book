package com.digital.book.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name ="reader")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;
    private String name;
    private String email;
    private Integer bookId;
    private long purchaseTimestamp;
    private boolean isSubscribed;
}
