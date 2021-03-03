package com.example.friendmangerment.model;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "people")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "email_address", nullable = false,unique = true)
    private String email;

    public Person() {
    }
    public Person(long id, String email) {
        this.id = id;
        this.email = email;
    }

}
