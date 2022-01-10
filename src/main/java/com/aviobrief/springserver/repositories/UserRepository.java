package com.aviobrief.springserver.repositories;


import com.aviobrief.springserver.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

//    @Override
//    default List<UserEntity> findAll() {
//        int count = 0;
//        System.out.println("here");
//        while (count < 2_000_000) {
//            count++;
//            System.out.println(count);
//
//        }
//        System.out.println("done");
//        return null;
//    }
}
