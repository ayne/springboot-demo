package com.example.data;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by charmanesantiago on 04/02/2016.
 */

@Entity
@Data //lombok for auto getter and setter
@Table(name = "users")
public class User {

    @Id
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 60)
    private String password;

    @Size(max = 60)
    private String name;

    @Size(max = 10)
    private String gender;
}
