package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "passengers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String phone;

    @Column(unique = true)
    private String email;

    private String password;
    private String address;
    private int age;

    @Column(columnDefinition = "integer default 0")
    private int status;  // 0 = logged Out, 1 = logged in
}
