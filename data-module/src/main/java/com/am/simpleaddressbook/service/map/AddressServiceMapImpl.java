package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.service.AddressService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default","MAP"})
public class AddressServiceMapImpl extends  AbstractContactMap<Long, Address> implements AddressService
{
    @Override
    public Address save(Address address) {
        return super.save(address);
    }

    @Override
    public Set<Address> findAll() {
        return super.findAll();
    }

    @Override
    public Address findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Address address) {
        super.delete(address);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
