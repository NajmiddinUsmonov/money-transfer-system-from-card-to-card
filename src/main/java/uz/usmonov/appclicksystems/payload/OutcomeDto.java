package uz.usmonov.appclicksystems.payload;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OutcomeDto {

    @NotNull
    private Integer fromCardId;

    @NotNull
    private Integer toCardId;

    @NotNull
    private double amount;


}
