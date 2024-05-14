package com.example.ecommerce.repository;

import com.example.ecommerce.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInformationRepository extends JpaRepository<UserInformation,Long> {
    Optional<UserInformation> findByMail(String mail);

}
