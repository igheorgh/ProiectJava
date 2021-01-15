package com.java.proiect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObiectVanzareDto {
    private Long id;
    @NotNull
    private Long user_id;
    @NotNull
    private int vandut;
    @NotNull
    @Min(0)
    private Double pret;
    private String imagine;
    private String descriere;
    @DateTimeFormat
    private Date data_adaugare;
    @DateTimeFormat
    private Date data_expirare;
}
