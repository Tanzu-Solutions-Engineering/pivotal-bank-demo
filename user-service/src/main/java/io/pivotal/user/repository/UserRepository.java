package io.pivotal.user.repository;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.user.domain.User;

public interface UserRepository extends CrudRepository<User,Integer> {
	public User findByUseridAndPasswd(String userId, String passwd);
	public User findByUserid(String userId);
	public User findByAuthtoken(String authtoken);
}
