package com.budgetbuildsystem.service.signup;

import com.budgetbuildsystem.dto.SignupRequest;

public interface ISignUpService {
    public void signupCitizen(SignupRequest signupRequest);

    void signupContractor(SignupRequest signupRequest);

    void signupAdministrator(SignupRequest signupRequest);

    void signupSupplier(SignupRequest signupRequest);
}
