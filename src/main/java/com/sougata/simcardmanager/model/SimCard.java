package com.sougata.simcardmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sim_cards")
public class SimCard {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String entryId;

    private String simCardNo;
    private String mobileNo;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    private String stateOfRegistration;
    private String kyc;
    private String telecomProvider;
    private String fullName;

    public SimCard(String simCardNo, String mobileNo, String status, String stateOfRegistration, String kyc,
                   String telecomProvider, String fullName) {
        this.simCardNo = simCardNo;
        this.mobileNo = mobileNo;
        this.status = status;
        this.stateOfRegistration = stateOfRegistration;
        this.kyc = kyc;
        this.telecomProvider = telecomProvider;
        this.fullName = fullName;
    }
}
