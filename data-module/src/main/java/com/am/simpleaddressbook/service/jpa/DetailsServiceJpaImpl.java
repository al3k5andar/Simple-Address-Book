package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.repositories.DetailsRepository;
import com.am.simpleaddressbook.service.DetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Profile("JSP")
@Service
@Slf4j
public class DetailsServiceJpaImpl implements DetailsService {

    private final DetailsRepository detailsRepository;

    public DetailsServiceJpaImpl(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Override
    public Details save(Details details) {
        if(details== null){
            log.debug("Details object can't be null");
            return new Details();
        }
        else{
            Optional<Details> detailsOptional= detailsRepository.findById(details.getId());
            if(detailsOptional.isPresent()){
                Details savedDetails= detailsOptional.get();
                if(savedDetails.getAddress()!= null){
                    Address address= savedDetails.getAddress();
                    address.setCountry(details.getAddress().getCountry());
                    address.setCity(details.getAddress().getCity());
                    address.setStreet(details.getAddress().getStreet());
                    address.setHouseNo(details.getAddress().getHouseNo());
                }
                else
                    savedDetails.setAddress(new Address());

                savedDetails.setId(details.getId());
                savedDetails.setBirthday(details.getBirthday());
                savedDetails.setNickName(details.getNickName());
                savedDetails.setPhone(details.getPhone());
                savedDetails.setWorkPhone(details.getWorkPhone());
                savedDetails.setEmail(details.getEmail());

                detailsOptional = Optional.of(savedDetails);
                return detailsRepository.save(detailsOptional.get());
            }
            else
                return detailsRepository.save(details);
        }
    }

    @Override
    public Set<Details> findAll() {
        Set<Details> detailsSet= new HashSet<>();
        detailsRepository.findAll().forEach(detailsSet::add);
        return detailsSet;
    }

    @Override
    public Details findById(Long aLong) {
        return detailsRepository.findById(aLong).orElse(null);
    }

    @Override
    public void delete(Details details) {
        detailsRepository.delete(details);
    }

    @Override
    public void deleteById(Long aLong) {
        detailsRepository.deleteById(aLong);
    }
}
