package com.project.demo.services;

import com.project.demo.dtos.ShippingAddressDto;
import com.project.demo.models.ShippingAddress;
import com.project.demo.models.User;
import org.springframework.stereotype.Service;


@Service
public interface ShippingAddressService {
    public ShippingAddress addAddressForUser(User user, ShippingAddress newAddress);
    public ShippingAddress updateShippingAddress(Integer userId, Integer addressId, ShippingAddressDto updateAddressDto);
}
