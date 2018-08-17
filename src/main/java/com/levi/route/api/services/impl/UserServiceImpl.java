package com.levi.route.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entities.User;
import com.levi.route.api.repositories.UserRepository;
import com.levi.route.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User persist(User user) {
		log.info("Persisting user: {}", user);
		return this.userRepository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		log.info("Finding user by username {}", username);
		return Optional.ofNullable(this.userRepository.findByUsername(username));
	}

}
