package com.example.MyBookShopApp.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "test_entities" )
public class TestEntity {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long id;
  private String data;

  @Override
  public String toString() {
    return "TestEntity{" +
            "id=" + id +
            ", data='" + data + "\'" +
            "}";
  }

}
