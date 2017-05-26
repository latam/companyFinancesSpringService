package pl.mlata.reports.dto;

import java.math.BigDecimal;

/**
 * Created by Mateusz on 25.05.2017.
 */
public class KpirFinancialColumns {
    protected BigDecimal revProductServices;
    protected BigDecimal revOther;
    protected BigDecimal revCombined;
    protected BigDecimal purchaseGoodsMaterials;
    protected BigDecimal insuranceCost;
    protected BigDecimal expPayment;
    protected BigDecimal expOther;
    protected BigDecimal expCombined;

    public KpirFinancialColumns() {
        this.revProductServices = new BigDecimal(0.0);
        this.revOther = new BigDecimal(0.0);
        this.revCombined = new BigDecimal(0.0);
        this.purchaseGoodsMaterials = new BigDecimal(0.0);
        this.insuranceCost = new BigDecimal(0.0);
        this.expPayment = new BigDecimal(0.0);
        this.expOther = new BigDecimal(0.0);
        this.expCombined = new BigDecimal(0.0);
    }
}
