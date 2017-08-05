package pl.piomin.services.datagrid.employee.model;

import java.io.Serializable;

public class Employee implements Serializable {

	private static final long serialVersionUID = 3214253910554454648L;
	public Integer id;
	public Integer personId;
	public String company;

	public Employee() {
		
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", personId=" + personId + ", company=" + company + "]";
	}

}
