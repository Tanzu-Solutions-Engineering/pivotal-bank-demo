package io.pivotal.accounts.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.accounts.domain.Account;
import io.pivotal.accounts.domain.AccountType;

public interface AccountRepository extends CrudRepository<Account,Integer> {
	public List<Account> findByUserid(String userId);
	public List<Account> findByUseridAndType(String userId, AccountType type);
}
