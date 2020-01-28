package pl.piomin.services.employee.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Employee implements Serializable {

	@Id
	private Long id;
	@EqualsAndHashCode.Exclude
	private Integer personId;
	@EqualsAndHashCode.Exclude
	private String company;
	@EqualsAndHashCode.Exclude
	private String position;
	@EqualsAndHashCode.Exclude
	private int salary;

}
