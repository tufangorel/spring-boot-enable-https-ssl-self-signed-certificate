package com.company.customerinfo.service;

import com.company.customerinfo.repository.ShippingAddressRepository;
import com.company.customerinfo.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    public ShippingAddressService(ShippingAddressRepository shippingAddressRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
    }

    public Customer findCustomerByShippingAddressID(Integer shippingAddressID) {
        return shippingAddressRepository.findCustomerByShippingAddressID(shippingAddressID);
    }
}
