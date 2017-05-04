package pl.piomin.services.datagrid.employee.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
// @KeySpace("person")
public class Employee implements Serializable {

	private static final long serialVersionUID = 3214253910554454648L;

	@Id
	@GeneratedValue
	private Integer id;
	private Integer personId;
	private String company;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
