package com.moonstack.service;

import com.moonstack.entity.ContactDetail;
import com.moonstack.entity.User;

public interface ContactDetailService {
    ContactDetail addContactDetails(ContactDetail contactDetail);
    void delete(User user);

}
