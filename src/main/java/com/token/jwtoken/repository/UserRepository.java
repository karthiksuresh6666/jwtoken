package com.token.jwtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.token.jwtoken.entity.User;

/**
 * @author Karthik Suresh
 *
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);

}



