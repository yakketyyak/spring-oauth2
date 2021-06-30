package com.yakketyyak.oauth2.config;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	// programatic transaction
	private final PlatformTransactionManager transactionManager;
	private final UserRepository userRepository;

	public void create(UserEntity entity) {
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
		try {

			transactionManager.getTransaction(definition);
		} catch (Exception e) {
			transactionManager.rollback(transactionStatus);
		}
	}

}
