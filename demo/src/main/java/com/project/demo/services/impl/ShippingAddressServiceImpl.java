package com.project.demo.services.impl;

import com.project.demo.services.ShippingAddressService;

import com.project.demo.dtos.ShippingAddressDto;
import com.project.demo.exceptions.EntityNotFoundException;
import com.project.demo.models.ShippingAddress;
import com.project.demo.models.User;
import com.project.demo.repositories.ShippingAddressRepository;
import com.project.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final UserRepository userRepository;

    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository,
                                      UserRepository userRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShippingAddress addAddressForUser(User user, ShippingAddress newAddress) {
        newAddress.setUser(user);
        return shippingAddressRepository.save(newAddress);
    }

    @Override
    public ShippingAddress updateShippingAddress(Integer userId, Integer addressId, ShippingAddressDto updatedAddressDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        ShippingAddress shippingAddress = shippingAddressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping address with ID " + addressId + " not found"));

        shippingAddress.setStreet(updatedAddressDto.getStreet());
        shippingAddress.setCity(updatedAddressDto.getCity());
        shippingAddress.setPostalCode(updatedAddressDto.getPostalCode());

        return shippingAddressRepository.save(shippingAddress);
    }

}