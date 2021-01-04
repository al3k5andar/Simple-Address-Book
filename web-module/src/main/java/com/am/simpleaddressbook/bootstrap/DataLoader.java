package com.am.simpleaddressbook.bootstrap;

import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final GroupService groupService;
    private final ContactService contactService;

    public DataLoader(GroupService groupService, ContactService contactService) {
        this.groupService = groupService;
        this.contactService = contactService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contactService.findAll().size()== 0)
            StaticData.contactList().forEach(contactService::save);

        if(groupService.findAll().size()== 0)
            StaticData.groupList().forEach(groupService::save);
    }

}
