package org.eu.rubensa.cashregister.repository;

import java.util.List;

import org.eu.rubensa.cashregister.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
  List<Product> findByNameContaining(String name);
}
