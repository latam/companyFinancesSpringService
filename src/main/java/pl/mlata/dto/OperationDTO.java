package pl.mlata.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperationDTO {
    private Long id;
    private LocalDate date;
    private String docNumber;
    private Long contractor;
    private String description;
    private Boolean purchase;
    private String type;
    private Integer vatBid;
    private BigDecimal nettoValue;
}
