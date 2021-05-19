package com.token.jwtoken.startup;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.CannotCreateTransactionException;

import com.token.jwtoken.dto.Roles;
import com.token.jwtoken.entity.User;
import com.token.jwtoken.repository.UserRepository;

/**
 * @author Karthik Suresh
 *
 */
@Configuration
public class MasterDataPersist {

	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataPersist.class);

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void saveDefaultUsers() {
		LOGGER.trace(">>saveDefaultUsers()");
		try {
			List<User> usrList = this.userRepository.findAll();
			if (usrList.isEmpty()) {

				var usr1 = new User();
				usr1.setFirstName("Karthik");
				usr1.setLastName("Suresh");
				usr1.setUserName("KartS");
				usr1.setRole(Roles.ADMIN.toString());
				usr1.setPassword("K@$t");

				var usr2 = new User();
				usr2.setFirstName("Uttam");
				usr2.setLastName("Kumar");
				usr2.setUserName("UTTK");
				usr2.setRole(Roles.ADMIN.toString());
				usr2.setPassword("U@!!");

				List<User> users = Arrays.asList(usr1, usr2);
				this.userRepository.saveAll(users);

			} else {
				LOGGER.debug("Users already present");
			}
		} catch (CannotCreateTransactionException | DataAccessResourceFailureException ex) {
			LOGGER.error("Transaction/DataAccessResourceFailure Exception occured in saveDefaultUsers() :{}",
					ex.getMessage());
		} catch (JpaSystemException ex) {
			LOGGER.error("JpaSystemException occured in saveDefaultUsers() :{}", ex.getMessage());
		} catch (DataAccessException ex) {
			LOGGER.error("DataAccessException occured in saveDefaultUsers() :{}", ex.getMessage());
		}
		LOGGER.trace("<<saveDefaultUsers()");
	}
}
