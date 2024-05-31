package srs.customerservice.Services.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import srs.customerservice.Services.DTOs.Request.AddCustomerRequest;
import srs.customerservice.Entities.Customer;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);


    Customer customerFromAddRequest(AddCustomerRequest request);
}