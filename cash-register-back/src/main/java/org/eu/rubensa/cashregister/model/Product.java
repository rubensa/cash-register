package org.eu.rubensa.cashregister.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Basic Product Information
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product implements Serializable {
  /** Serial version */
  private static final long serialVersionUID = 1L;

  /** Product Code */
  @Id
  private String code;

  /** Product Name */
  private String name;

  /** Product Price */
  private BigDecimal price;
}
