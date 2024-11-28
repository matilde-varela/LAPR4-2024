/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public class InMemoryCandidateUserRepository
        extends InMemoryDomainRepository<Candidate, PhoneNumber> implements CandidateRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Candidate> findByUsername(final Username name) {
        return matchOne(e -> e.user().username().equals(name));
    }

    @Override
    public Optional<Candidate> findByPhoneNumber(final PhoneNumber number) {
        return matchOne(e -> e.identity().equals(number));
    }

    @Override
    public boolean checksIfExits(PhoneNumber number) {
        return matchOne(e -> e.identity().equals(number)).isPresent();
    }

    @Override
    public Iterable<Candidate> findByActive(boolean b) {
        CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();
        List<Candidate> candidatesList= new ArrayList<>();
        Iterable<Candidate> candidates = candidateRepository.findAll();
        for (Candidate candidate : candidates) {
            if (candidate.user().isActive() == b){
                candidatesList.add(candidate);
            }
        }
        return candidatesList;
    }


}
