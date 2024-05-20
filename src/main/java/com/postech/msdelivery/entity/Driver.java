package com.postech.msdelivery.entity;

import com.postech.msdelivery.validation.UniqueEmail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    @UniqueEmail
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean available;

    // @Column(nullable = false)
    // private BigDecimal latitude;

    // @Column(nullable = false)
    // private BigDecimal longitude;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;


    public Driver(String name, String email) {
       this.name = name;
       this.email = email;
    }

    @Deprecated
    public Driver() {}
}

