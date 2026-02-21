package com.kce.bean;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "DAMAGE_REPORT")
public class DamageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "damage_seq_gen")
    @SequenceGenerator(name = "damage_seq_gen", sequenceName = "DAMAGE_SEQ", allocationSize = 1)
    @Column(name = "DAMAGE_ID")
    private int damageID;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Temporal(TemporalType.DATE)
    @Column(name = "REPORT_DATE")
    private Date reportDate;

    @Column(name = "REPORTED_BY")
    private String reportedBy;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "DAMAGED_QUANTITY")
    private double damagedQuantity;

    @Column(name = "STATUS")
    private String status;

    // Default Constructor (VERY IMPORTANT in Hibernate)
    public DamageReport() {
    }

    // Parameterized Constructor
    public DamageReport(int damageID, String itemCode, Date reportDate,
                        String reportedBy, String reason,
                        double damagedQuantity, String status) {
        this.damageID = damageID;
        this.itemCode = itemCode;
        this.reportDate = reportDate;
        this.reportedBy = reportedBy;
        this.reason = reason;
        this.damagedQuantity = damagedQuantity;
        this.status = status;
    }

    // Getters & Setters
    public int getDamageID() {
        return damageID;
    }

    public void setDamageID(int damageID) {
        this.damageID = damageID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getDamagedQuantity() {
        return damagedQuantity;
    }

    public void setDamagedQuantity(double damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
