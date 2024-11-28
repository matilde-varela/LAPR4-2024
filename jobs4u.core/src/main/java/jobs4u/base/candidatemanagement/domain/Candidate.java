/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.candidatemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;

import java.util.Calendar;

@Entity
@Table(name = "T_CANDIDATE")
public class Candidate implements AggregateRoot<PhoneNumber> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private PhoneNumber phoneNumber;

    /**
     * cascade = CascadeType.NONE as the systemUser is part of another aggregate
     */
    @OneToOne()
    private SystemUser systemUser;

    public Candidate(final SystemUser user, final PhoneNumber phoneNumber) {
        Preconditions.noneNull(user, phoneNumber);
        this.systemUser = user;
        this.phoneNumber = phoneNumber;
    }

    protected Candidate() {
    }

    public SystemUser user() {
        return this.systemUser;
    }

    public EmailAddress email() {
        return this.systemUser.email();
    }

    public Name name() {
        return this.systemUser.name();
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }
    public CandidateDTO toDTO() {
        return new CandidateDTO(systemUser.name().toString(), systemUser.email().toString(), phoneNumber.number());
    }

    public PhoneNumber phoneNumber() {
        return identity();
    }

    @Override
    public PhoneNumber identity() {
        return this.phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + systemUser.name().firstName() + "| Phone Number: " + phoneNumber.toString() + "| Email: " + email().toString();
    }

    public void updateUser(SystemUser user) {
        this.systemUser = user;
    }

}
