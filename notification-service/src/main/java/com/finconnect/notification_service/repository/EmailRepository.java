package com.finconnect.notification_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.finconnect.notification_service.entity.Email;

public interface EmailRepository extends MongoRepository<Email, String> {

}
