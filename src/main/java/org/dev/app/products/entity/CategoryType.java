package org.dev.app.products.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


public enum CategoryType {
    ELECTRONICS,
    CLOTHING,
    HOME_KITCHEN,
    BEAUTY_HEALTH,
    TECHNOLOGY
}