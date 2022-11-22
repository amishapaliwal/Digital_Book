package com.digital.book.pojo;

import com.digital.book.model.Reader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BuyBookPojo {

    private Integer bookId;
    private Reader reader;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
     public class Reader {
         private String name;
         private String email;
     }
}
