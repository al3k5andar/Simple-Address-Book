package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.BaseEntry;

import java.util.*;

public abstract class AbstractContactMap<ID extends Long, T extends BaseEntry>
{
    protected Map<Long,T> contactMap= new HashMap<>();

    public T save(T t){
        if(t!= null){
            if(t.getId()== null)
                t.setId(generateId());
            contactMap.put(t.getId(), t);
        }
        else
            throw new RuntimeException("System can't save null object");
        return t;
    }

    private Long generateId(){
        long id;

        try{
            id= Collections.max(contactMap.keySet())+ 1L;
        }
        catch (NoSuchElementException e){
            id= 1L;
        }

        return id;
    }

    public Set<T> findAll(){
        return new HashSet<>(contactMap.values());
    }

    public T findById(ID id){
        return contactMap.get(id);
    }

    public void delete(T t){
        contactMap.remove(t.getId());
    }

    public void deleteById(ID id){
        contactMap.remove(id);
    }
}
