package com.plagiarism.plagiarismremover.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plagiarism.plagiarismremover.adapter.UserPrincipal;
import com.plagiarism.plagiarismremover.entity.User;
import com.plagiarism.plagiarismremover.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
	
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
	
	public Optional<User> findById(Long userId){
		return Optional.ofNullable(this.userRepository.findById(userId)
				.orElseThrow(() -> new ObjectNotFoundException("user", userId)));
	}
	
	public User create(User user){
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}
	
	public User update(User user) {
	    User existingUser = userRepository.findById(user.getId())
	            .orElseThrow(() -> new ObjectNotFoundException("user", user.getId()));

	    // Update user properties
	    existingUser.setUsername(user.getUsername());
	    existingUser.setRoles(user.getRoles());

	    return userRepository.save(existingUser);
	}
	
	public void deleteById(Long userId){
		this.userRepository.findById(userId)
				.orElseThrow(() -> new ObjectNotFoundException("user", userId));
		
		this.userRepository.deleteById(userId);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username) // First we need to find the user from database.
			.map(user -> new UserPrincipal(user)) // If found wrap the returned user instance in a UserPrincipal instance.
			.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " is not found.")); //Otherwise, throw an exception
	}

}
