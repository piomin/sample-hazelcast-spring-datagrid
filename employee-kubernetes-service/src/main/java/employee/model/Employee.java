package employee.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Employee {

	private Integer id;
	@EqualsAndHashCode.Exclude
	private Integer personId;
	@EqualsAndHashCode.Exclude
	private String company;
	@EqualsAndHashCode.Exclude
	private String position;

}
