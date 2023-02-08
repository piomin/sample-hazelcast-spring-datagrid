package pl.piomin.services.datagrid.employee.data;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import pl.piomin.services.datagrid.employee.model.Employee;

import java.io.IOException;

public class EmployeeSerializer implements StreamSerializer<Employee> {

	@Override
	public int getTypeId() {
		return 1;
	}

	@Override
	public void write(ObjectDataOutput out, Employee employee) throws IOException {
		out.writeInt(employee.getId());
		out.writeInt(employee.getPersonId());
		out.writeUTF(employee.getCompany());
	}

	@Override
	public Employee read(ObjectDataInput in) throws IOException {
		Employee e = new Employee();
		e.setId(in.readInt());
		e.setPersonId(in.readInt());
		e.setCompany(in.readUTF());
		return e;
	}

	@Override
	public void destroy() {
	}

}
