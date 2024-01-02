package com.example.product.domains;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "product_second_option")
public class ProductSecondOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_first_option_id")
    private ProductFirstOption productFirstOption;

    @Embedded
    private SecondOptionName name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "add_price"))
    private Money addPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;


    public ProductSecondOption() {}

    public ProductSecondOption(ProductFirstOption productFirstOption, SecondOptionName name, Money addPrice) {
        this.productFirstOption = productFirstOption;
        this.name = name;
        this.addPrice = addPrice;
    }

    public Long id() {return id;}
    public ProductFirstOption productFirstOption() {return productFirstOption; }
    public SecondOptionName name() {return name;}
    public Money addPrice() {return addPrice;}
}
