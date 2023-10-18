package com.example.MyBookShopApp.data.struct.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String hash;

    private LocalDateTime regTime;

    private int balance;

    private String name;

    @Transient
    @JsonProperty
    private UserContactEntity userContact;

}
