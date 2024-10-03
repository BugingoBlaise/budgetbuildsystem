package com.budgetbuildsystem.service.signup;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.supplier.ISupplierService;
import com.budgetbuildsystem.service.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service@Transactional@Slf4j
public class SignUpService implements ISignUpService {
    @Autowired
    IUserService iUserService;
    @Autowired
    ICitizenService iCitizenService;
    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IContractorService contractorService;
//    @Autowired
//    private  I administratorRepository;*/
    @Autowired
    PasswordEncoder passwordEncoder;

  @Override
    public void signupCitizen(SignDto signupRequest) {
 /*

       switch (signupRequest.getUserType().toUpperCase()) {
            case "CITIZEN":
                User user = new User();
                user.setUsername(signupRequest.getUsername());
                user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

                user.setRoles(Set.of(String.valueOf(ERole.CITIZEN)));
                Citizen citizen = new Citizen();
                citizen.setEmail(signupRequest.getEmail());
                citizen.setPhoneNumber(signupRequest.getPhoneNumber());
                citizen.setUser(user);
                user.setCitizen(user.setCitizen(citizen));
                iUserService.signUpUser(user);
                iCitizenService.saveCitizen(citizen);
                break;



            case "ADMINISTRATOR":
                user.setRoles(Set.of("ROLE_ADMIN"));
                RHA_Administrator administrator = new RHA_Administrator();
                administrator.setDepartment(signupRequest.getDepartment());
                administrator.setUser(user);
                user.setAdministrator(administrator);
                userRepository.save(user);
                administratorRepository.save(administrator);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type");
        }*/
    }

    @Override
    public void signupContractor(SignDto signupRequest) {

    }

    @Override
    public void signupAdministrator(SignDto signupRequest) {

    }

    @Override
    public void signupSupplier(SignDto signupRequest) {

    }
}

  /*  @Override
    public void signupContractor(SignupRequest signupRequest) {
        User user = new User();
        String usertype=signupRequest.getUserType().toUpperCase();
        if(usertype.equals("CONTRACTOR")){
            user.setUsername(signupRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setRoles(Set.of(String.valueOf(ERole.CONTRACTOR)));

            Contractor contractor = new Contractor();

            contractor.setCompanyName(signupRequest.getCompanyName());
            contractor.setLicenseNumber(signupRequest.getLicenseNumber());
            contractor.setUser(user);

            user.setContractor(contractor);
            iUserService.signUpUser(user);
            contractorService.saveContractor(contractor);

        }else{
            throw new UsernameNotFoundException("Invalid user type");

        }
    }*/

 /*   @Override
    public void signupAdministrator(SignupRequest signupRequest) {
        User user = new User();
        String usertype=signupRequest.getUserType().toUpperCase();
        if(usertype.equals("ADMIN")){

            user.setUsername(signupRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setRoles(Set.of("ROLE_CONTRACTOR"));

            RHA_Administrator administrator = new RHA_Administrator();

            administrator.set.setCompanyName(signupRequest.getCompanyName());
            contractor.setLicenseNumber(signupRequest.getLicenseNumber());
            contractor.setUser(user);
            user.setContractor(contractor);
            iUserService.signUpUser(user);
            contractorService.saveContractor(contractor);

        }else{
            throw new UsernameNotFoundException("Invalid user type");

        }

    }*/

    /*@Override
    public void signupSupplier(SignupRequest signupRequest) {
        User user = new User();
        String usertype=supplierDto.getUserType().toUpperCase();
        if(usertype.equals("SUPPLIER")){
            user.setUsername(supplierDto.getSupplierName());
            user.setPassword(passwordEncoder.encode(supplierDto.getPassword()));
            user.setRoles(Set.of(String.valueOf(ERole.SUPPLIER)));

            Supplier supplier = new Supplier();

            supplier.setSupplierName(supplier.getSupplierName());
            supplier.setContactDetails(supplierDto.getContactDetails());
            supplier.setUser(user);
            user.setSupplier(supplier);
            iUserService.signUpUser(user);

            supplierService.saveSupplier(supplier);

        }else{
            throw new UsernameNotFoundException("Invalid user type");

        }
    }

     */





