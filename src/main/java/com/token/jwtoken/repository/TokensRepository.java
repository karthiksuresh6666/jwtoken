package com.token.jwtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.token.jwtoken.entity.Tokens;

/**
 * @author Karthik Suresh
 *
 */
@Repository
@Transactional
public interface TokensRepository extends JpaRepository<Tokens, Long> {

}
