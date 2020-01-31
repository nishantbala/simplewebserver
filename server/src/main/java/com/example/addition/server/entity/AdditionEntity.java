package com.example.addition.server.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_ADDITION")
public class AdditionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private Long number;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
}