package com.moonstack.repository;

import com.moonstack.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,String> , JpaSpecificationExecutor<Message>
{
        Optional<Message> findByValue(String value);
}
