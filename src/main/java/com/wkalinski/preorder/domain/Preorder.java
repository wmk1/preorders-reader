package com.wkalinski.preorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Preorder extends ResourceSupport {

    private Link id;
    private String imageUrl;
    private BigDecimal regularPrice;
    private BigDecimal minPrice;
    private int preorderId;
    private String name;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public void setPreorderId(int preorderId) {
        this.preorderId = preorderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Preorder(@JsonProperty("image") String imageUrl,
                     @JsonProperty("regular_price") BigDecimal regularPrice,
                     @JsonProperty("min_price") BigDecimal minPrice,
                     @JsonProperty("entity_id") int preorderId,
                     @JsonProperty("name") String name) {
        this.imageUrl = checkNotNull(imageUrl);
        this.regularPrice = regularPrice;
        this.minPrice = minPrice;
        this.preorderId = checkNotNull(preorderId);
        this.name = checkNotNull(name);
    }

    public int getPreorderId() {
        return preorderId;
    }

    public static class PreorderBuilder {

        private String imageUrl;
        private BigDecimal regularPrice;
        private BigDecimal minPrice;
        private int preorderId;
        private String name;

        public PreorderBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public PreorderBuilder regularPrice(BigDecimal regularPrice) {
            this.regularPrice = regularPrice;
            return this;
        }

        public PreorderBuilder minPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public PreorderBuilder preorderId(int preorderId) {
            this.preorderId = preorderId;
            return this;
        }

        public PreorderBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Preorder build() {
            return new Preorder(imageUrl, regularPrice, minPrice, preorderId, name);
        }
    }
}
