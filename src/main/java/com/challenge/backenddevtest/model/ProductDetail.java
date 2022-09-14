package com.challenge.backenddevtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Size(min = 1)
    private String id;
    @Size(min = 1)
    private String name;
    @NotNull
    private double price;
    @NotNull
    private boolean availability;
}
