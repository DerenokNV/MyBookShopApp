package com.example.MyBookShopApp.data.struct.user;

import lombok.*;
import com.example.MyBookShopApp.data.struct.enums.ContactType;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_contact")
public class UserContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private ContactType type;

    private short approved;

    private String code;

    private int codeTrails;

    private LocalDateTime codeTime;

    private String contact;
}
