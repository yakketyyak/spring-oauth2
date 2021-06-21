/**
 * 
 */
package com.yakketyyak.oauth2.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pbeugre
 *
 */
@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "is_locked")
	private boolean isLocked;

	@PrePersist
	public void addDefaultValues() {
		if (!isEnabled) {
			isEnabled = true;
		}
		if (!isLocked) {
			isLocked = true;
		}
	}

}
