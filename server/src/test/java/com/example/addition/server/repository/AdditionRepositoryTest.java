package com.example.addition.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.addition.server.entity.AdditionEntity;

@DataJpaTest
@Transactional
public class AdditionRepositoryTest {

	  @Autowired
	  private transient AdditionRepository additionRepository;
	  
	  private AdditionEntity additionEntity;
	  
	  public void setAdditionRepository(final AdditionRepository additionRepository) {
		    this.additionRepository = additionRepository;
		  }

	  @BeforeEach
	  public void setUp() {
		    this.additionEntity = new AdditionEntity();
		    this.additionEntity.setNumber(new BigInteger("1"));
		    this.additionEntity.setSessionId("somesessionid");
		}
		  
	  @Test
	  public void testSaveSuccess() throws Exception {
			additionRepository.save(this.additionEntity);
		    Optional<AdditionEntity> entity = additionRepository.findById(this.additionEntity.getId());
		    assertThat(entity.get().equals(this.additionEntity));
	  }
		  
}
