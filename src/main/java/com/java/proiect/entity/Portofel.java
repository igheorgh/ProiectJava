package com.java.proiect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Portofel {
    @NotNull
    private Long id;
    @NotNull
    private Long user_id;
    @Min(0)
    private Double suma_bani;
}
