package com.guen.program.qdslstudy.model.entity.oe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMERS" ,schema = "OE")
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "CUST_FIRST_NAME", nullable = false, length = 20)
    private String custFirstName;

    @Size(max = 20)
    @NotNull
    @Column(name = "CUST_LAST_NAME", nullable = false, length = 20)
    private String custLastName;

    @Size(max = 3)
    @Column(name = "NLS_LANGUAGE", length = 3)
    private String nlsLanguage;

    @Size(max = 30)
    @Column(name = "NLS_TERRITORY", length = 30)
    private String nlsTerritory;

    @Column(name = "CREDIT_LIMIT", precision = 9, scale = 2)
    private BigDecimal creditLimit;

    @Size(max = 30)
    @Column(name = "CUST_EMAIL", length = 30)
    private String custEmail;
    @Size(max = 20)
    @Column(name = "MARITAL_STATUS", length = 20)
    private String maritalStatus;

    @Size(max = 1)
    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Size(max = 20)
    @Column(name = "INCOME_LEVEL", length = 20)
    private String incomeLevel;


}