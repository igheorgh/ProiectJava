package com.java.proiect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Calificativ {
    @NotNull
    public Long id;
    @NotNull
    public Long vanzator_id;
    @NotNull
    public long cumparator_id;
    @Min(1) @Max(5)
    public double nota;
}
