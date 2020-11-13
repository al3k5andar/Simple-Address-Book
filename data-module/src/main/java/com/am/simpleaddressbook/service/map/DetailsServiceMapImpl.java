package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.service.AddressService;
import com.am.simpleaddressbook.service.DetailsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class DetailsServiceMapImpl extends AbstractContactMap<Long, Details> implements DetailsService {

    private final AddressService addressService;

    public DetailsServiceMapImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public Details save(Details details) {
        if(details.getAddress()!= null)
            if(details.getAddress().getId()== null){
                Address address= addressService.save(details.getAddress());
                details.getAddress().setId(address.getId());
            }
        return super.save(details);
    }

    @Override
    public Set<Details> findAll() {
        return super.findAll();
    }

    @Override
    public Details findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Details details) {
        super.delete(details);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
