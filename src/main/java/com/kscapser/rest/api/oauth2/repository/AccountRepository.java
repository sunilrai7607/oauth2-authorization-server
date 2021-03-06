package com.kscapser.rest.api.oauth2.repository;

import com.kscapser.rest.api.oauth2.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserName(final String userName);
}
