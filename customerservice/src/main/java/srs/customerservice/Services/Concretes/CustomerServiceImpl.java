package srs.customerservice.Services.Concretes;

import org.springframework.stereotype.Service;
import srs.customerservice.Entities.Address;
import srs.customerservice.Entities.Customer;
import srs.customerservice.Repositories.AddressRepository;
import srs.customerservice.Repositories.CustomerRepository;
import srs.customerservice.Services.Abstract.AddressService;
import srs.customerservice.Services.DTOs.Request.AddCustomerRequest;
import srs.customerservice.Services.Abstract.CustomerService;
import srs.customerservice.Services.DTOs.Request.Address.AddAddressRequest;
import srs.customerservice.Services.DTOs.Request.Address.DeleteAddressRequest;
import srs.customerservice.Services.DTOs.Request.Customer.AddCustomerDemografikRequest;
import srs.customerservice.Services.DTOs.Request.Customer.UpdateCustomerRequest;
import srs.customerservice.Services.DTOs.Response.AddCustomerResponse;
import srs.customerservice.Services.DTOs.Response.getAddressResponse;
import srs.customerservice.Services.DTOs.Response.getContactResponse;
import srs.customerservice.Services.Mappers.CustomerMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @Override
   public AddCustomerResponse add(AddCustomerRequest request) {

        Customer customer = CustomerMapper.INSTANCE.customerFromAddRequest(request);
        customer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerFromAddResponse(customer);
    }

    @Override
    public getAddressResponse getAddress(int id) {

        Optional<Customer> customer = customerRepository.findById(id);
        getAddressResponse customer1 = CustomerMapper.INSTANCE.getAddressFromCustomer(customer);


        return customer1;
    }

    @Override
    public getContactResponse getContact(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
       getContactResponse customer2 = CustomerMapper.INSTANCE.getContactFromCustomer(customer);
        return customer2;
    }


    public Customer updateCustomer(int id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(request.getFirstName());
        customer.setMiddleName(request.getMiddleName());
        customer.setLastName(request.getLastName());
        customer.setMotherName(request.getMotherName());
        customer.setFatherName(request.getFatherName());
        customer.setGender(request.getGender());
        customer.setBirthDate(request.getBirthDate());
        customer.setNationalityID(request.getNationalityID());


        return customerRepository.save(customer);
    }

    @Override
    public void demografikAdd(AddCustomerDemografikRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setMiddleName(request.getMiddleName());
        customer.setLastName(request.getLastName());
        customer.setBirthDate(LocalDate.parse(request.getBirthDate()));
        customer.setGender(request.getGender());
        customer.setMotherName(request.getMotherName());
        customer.setFatherName(request.getFatherName());
        customer.setNationalityId(request.getNationalityId());

        customerRepository.save(customer);
    }




    public List<Customer> searchCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        customers.addAll(customerRepository.findByFirstNameContainingIgnoreCase(query));
        customers.addAll(customerRepository.findByLastNameContainingIgnoreCase(query));
        customers.addAll(customerRepository.findByMiddleNameContainingIgnoreCase(query));
        customers.addAll(customerRepository.findByMotherNameContainingIgnoreCase(query));
        customers.addAll(customerRepository.findByFatherNameContainingIgnoreCase(query));
        // customers.addAll(customerRepository.findByNationalityIdContainingIgnoreCase(query));
        // customers.addAll(customerRepository.findByCustomerIDContainingIgnoreCase(query));
        customers.addAll(customerRepository.findByAccountNumberContainingIgnoreCase(query));
        return customers;
    }

    @Override
    public void addAddress( AddAddressRequest request) {

        addressService.addAddress(request);

    }

    @Override
    public void updateAddress(int customerId, DeleteAddressRequest request) {

        Address adres = addressRepository.findById(customerId);

        adres.setId(request.getCustomerId());
        adres.setCity(request.getCity());
        adres.setDistrict(request.getDistrict());
        adres.setStreet(request.getStreet());
        adres.setDescription(request.getDescription());
        adres.setHouseNumber(request.getHouseNumber());
        adres.setId(request.getCustomerId());
    }


}
