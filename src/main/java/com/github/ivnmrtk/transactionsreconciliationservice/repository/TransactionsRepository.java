package com.github.ivnmrtk.transactionsreconciliationservice.repository;

import com.github.ivnmrtk.transactionsreconciliationservice.dao.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository extends CrudRepository<Transaction, Integer> {
}
