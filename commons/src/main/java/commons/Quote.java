/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Person person;
    private String quote;

    /**
     * Get method
     *
     * @return the owner as type person of this quote
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Get method
     *
     * @return the quote as type string
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Get method
     *
     * @return id of this quote
     */
    public long getId() {
        return id;
    }

    /**
     * Constructor for mapping
     */
    public Quote() {
        // for object mappers
    }

    /**
     * Constructor
     *
     * @param person p
     * @param quote  q
     */
    public Quote(Person person, String quote) {
        this.person = person;
        this.quote = quote;
    }

    /**
     * Equals method
     *
     * @param obj o
     * @return val
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Hash
     *
     * @return hash val
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString method
     *
     * @return string val
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Setter method
     *
     * @param id set the id of this quote
     */
    public void setId(long id) {
        this.id = id;
    }
}