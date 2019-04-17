package com.cuccatti.inventory.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(name = "FRST_NM")
	private String firstName;

	@NotBlank
	@Column(name = "LST_NM")
	private String lastName;

	@NotBlank
	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Address> addresses;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Phone> phones;

	public Customer(Long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Customer(Long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
			Set<Address> addresses) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.addresses = addresses;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Customer)) {
			return false;
		}

		Customer customer = (Customer) o;

		return customer.getEmail().equals(this.email);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.email.hashCode();
		return result;
	}

}
