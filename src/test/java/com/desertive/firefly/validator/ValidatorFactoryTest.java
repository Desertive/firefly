package com.desertive.firefly.validator;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.Before;
import org.junit.Test;

public class ValidatorFactoryTest {

	ValidatorFactory validator;
	TestUser testUser;

	@Before
	public void initTestUser() {
		validator = new ValidatorFactory();
		testUser = new TestUser();
		testUser.setId(1);
		testUser.setAge(27);
		testUser.setThingsILike(Arrays.asList("Music", "Blaablaa"));
	}

	@Test
	public void validatorShouldPassValidObject() {
		assertTrue(validator.isValidOrThrow(testUser));
	}

	@Test(expected = ValidationException.class)
	public void validatorShouldTrowWhenNotNullPropertyIsNull() {
		testUser.setId(null);
		validator.isValidOrThrow(testUser);
	}

	@Test(expected = ValidationException.class)
	public void validatorShouldTrowWhenIntegerIsBelowMin() {
		testUser.setAge(10);
		validator.isValidOrThrow(testUser);
	}

	@Test(expected = ValidationException.class)
	public void validatorShouldTrowWhenIntegerIsAboveMax() {
		testUser.setAge(200);
		validator.isValidOrThrow(testUser);
	}

	@Test(expected = ValidationException.class)
	public void validatorShouldTrowWhenListIsTooSmall() {
		testUser.setThingsILike(Arrays.asList());
		validator.isValidOrThrow(testUser);
	}

	public class TestUser {

		@NotNull(message = "id is null")
		private Integer id;
		@NotNull(message = "age is null")
		@Min(18)
		@Max(120)
		private Integer age;
		@NotNull(message = "thingsILike is null")
		@Size(min = 1)
		private List<String> thingsILike;

		public void setId(Integer id) {
			this.id = id;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public void setThingsILike(List<String> thingsILike) {
			this.thingsILike = thingsILike;
		}

	}
}
