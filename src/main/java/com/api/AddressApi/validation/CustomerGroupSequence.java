package com.api.AddressApi.validation;

import com.api.AddressApi.Dto.CustomerDto;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerGroupSequence implements DefaultGroupSequenceProvider<CustomerDto> {

        @Override
        public List<Class<?>>getValidationGroups(CustomerDto customerDto) {
            List<Class<?>> groups = new ArrayList<>();
            groups.add(CustomerDto.class);

            if(isPersonSelected(customerDto)) {
                groups.add(customerDto.getTypeDocument().getGroup());
            }
            return groups;

        }

        protected boolean isPersonSelected(CustomerDto customerRequestDto) {
            return customerRequestDto != null && customerRequestDto.getTypeDocument() != null;
        }

    }

