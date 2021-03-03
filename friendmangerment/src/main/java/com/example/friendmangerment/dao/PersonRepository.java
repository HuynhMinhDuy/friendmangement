package com.example.friendmangerment.dao;

import com.example.friendmangerment.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
        Person findByEmail(String email);
        boolean existsByEmail(String email);
}