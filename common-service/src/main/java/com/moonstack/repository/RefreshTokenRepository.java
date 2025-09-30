package com.moonstack.repository;

import com.moonstack.entity.RefreshToken;
import com.moonstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String>
{
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}
