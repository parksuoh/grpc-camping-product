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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_first_option")
public class ProductFirstOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    private FirstOptionName name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "add_price"))
    private Money addPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;

    @OneToMany(mappedBy = "productFirstOption")
    List<ProductSecondOption> productSecondOptions = new ArrayList<>();

    public ProductFirstOption() {}

    public ProductFirstOption(Product product, FirstOptionName name, Money addPrice) {
        this.product = product;
        this.name = name;
        this.addPrice = addPrice;
    }

    public Long id() {return id;}
    public Product product() {return product;}
    public FirstOptionName name() {return name;}
    public Money addPrice() {return addPrice;}

}
