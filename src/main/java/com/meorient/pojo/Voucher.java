package com.meorient.pojo;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 展会信息
 */
@AllArgsConstructor
public class Voucher implements Serializable {
    private String customer;
    private String exhibition;


    public String getCustomer() {
        return customer;
    }

    public String getExhibition() {
        return exhibition;
    }
}
