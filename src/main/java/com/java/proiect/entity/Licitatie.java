package com.java.proiect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Licitatie {
    @NotNull
    private Long id;
    @NotNull
    private Long user_id;
    @NotNull
    private Long produs_id;
    @NotNull
    private double oferta;
}
