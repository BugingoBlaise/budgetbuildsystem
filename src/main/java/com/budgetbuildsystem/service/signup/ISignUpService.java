package com.budgetbuildsystem.service.signup;

import com.budgetbuildsystem.dto.SignDto;

public interface ISignUpService {
    public void signupCitizen(SignDto signupRequest);

    void signupContractor(SignDto signupRequest);

    void signupAdministrator(SignDto signupRequest);

    void signupSupplier(SignDto signupRequest);
}
