package com.smart.dao;
import com.smart.model.Contact;
import com.smart.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	//pagination...
	
	@Query("from Contact as c where c.user.id =:userId")
	//currentPage-page
	//Contact Per page - 5

	public Page<Contact> findContactsByUser(@Param("userId")int userId, Pageable pePageable);
	
	//search

	public List<Contact> findByNameContainingAndUser(String name, User user);
	
}
