package org.eu.rubensa.cashregister.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.eu.rubensa.cashregister.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class ProductRepositoryTest {
  @Autowired
  protected TestEntityManager entityManager;

  @Autowired
  private ProductRepository repository;

  @Test
  public void should_find_no_products_if_repository_is_empty() {
    Iterable<Product> products = repository.findAll();

    assertThat(products).isEmpty();
  }

  @Test
  public void should_store_a_product() {
    Product product = repository
        .save(Product.builder().code("PROD").name("Name").price(new BigDecimal(20)).build());

    assertThat(product).hasFieldOrPropertyWithValue("code", "PROD");
    assertThat(product).hasFieldOrPropertyWithValue("name", "Name");
    assertThat(product).hasFieldOrPropertyWithValue("price", new BigDecimal(20));
  }

  @Test
  public void should_find_all_products() {
    Product prod1 = Product.builder().code("PROD#1").name("Name#1").price(new BigDecimal(5)).build();
    entityManager.persist(prod1);

    Product prod2 = Product.builder().code("PROD#2").name("Name#2").price(new BigDecimal(20)).build();
    entityManager.persist(prod2);

    Product prod3 = Product.builder().code("PROD#3").name("Name#3").price(new BigDecimal(7.5)).build();
    entityManager.persist(prod3);

    Iterable<Product> products = repository.findAll();

    assertThat(products).hasSize(3).contains(prod1, prod2, prod3);
  }

  @Test
  public void should_find_product_by_id() {
    Product prod1 = Product.builder().code("PROD#1").name("Name#1").price(new BigDecimal(5)).build();
    entityManager.persist(prod1);

    Product prod2 = Product.builder().code("PROD#2").name("Name#2").price(new BigDecimal(20)).build();
    entityManager.persist(prod2);

    Product foundProduct = repository.findById(prod2.getCode()).get();

    assertThat(foundProduct).isEqualTo(prod2);
  }

  @Test
  public void should_find_products_by_name_containing_string() {
    Product prod1 = Product.builder().code("PROD#1").name("Name#1").price(new BigDecimal(5)).build();
    entityManager.persist(prod1);

    Product prod2 = Product.builder().code("PROD#2").name("Name#2").price(new BigDecimal(20)).build();
    entityManager.persist(prod2);

    Product prod3 = Product.builder().code("PROD#10").name("Name#10").price(new BigDecimal(7.5)).build();
    entityManager.persist(prod3);

    Iterable<Product> products = repository.findByNameContaining("#1");

    assertThat(products).hasSize(2).contains(prod1, prod3);
  }

  @Test
  public void should_update_product_by_id() {
    Product prod1 = Product.builder().code("COD#1").name("PRODUCT#1").price(new BigDecimal(1)).build();
    entityManager.persist(prod1);

    Product prod2 = Product.builder().code("COD#2").name("PRODUCT#2").price(new BigDecimal(2)).build();
    entityManager.persist(prod2);

    Product updatedProd = Product.builder().name("PRODUCT#2MOD").price(new BigDecimal(20)).build();

    Product prod = repository.findById(prod2.getCode()).get();
    prod.setName(updatedProd.getName());
    prod.setPrice(updatedProd.getPrice());
    repository.save(prod);

    Product checkProd = repository.findById(prod2.getCode()).get();

    assertThat(checkProd.getCode()).isEqualTo(prod2.getCode());
    assertThat(checkProd.getName()).isEqualTo(updatedProd.getName());
    assertThat(checkProd.getPrice()).isEqualTo(updatedProd.getPrice());
  }

  @Test
  public void should_delete_product_by_id() {
    Product prod1 = Product.builder().code("PROD#1").name("Name#1").price(new BigDecimal(5)).build();
    entityManager.persist(prod1);

    Product prod2 = Product.builder().code("PROD#2").name("Name#2").price(new BigDecimal(20)).build();
    entityManager.persist(prod2);

    Product prod3 = Product.builder().code("PROD#3").name("Name#3").price(new BigDecimal(7.5)).build();
    entityManager.persist(prod3);

    repository.deleteById(prod2.getCode());

    Iterable<Product> products = repository.findAll();

    assertThat(products).hasSize(2).contains(prod1, prod3);
  }

  @Test
  public void should_delete_all_products() {
    entityManager.persist(Product.builder().code("PROD#1").name("Name#1").price(new BigDecimal(5)).build());
    entityManager.persist(Product.builder().code("PROD#2").name("Name#2").price(new BigDecimal(20)).build());

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
  }
}
