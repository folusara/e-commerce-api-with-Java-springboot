package com.example.myEcommerceAPI.DataTransferObjects;

import com.example.myEcommerceAPI.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutDTO {
    Long cartId;
    PaymentStatus paymentStatus;
}
