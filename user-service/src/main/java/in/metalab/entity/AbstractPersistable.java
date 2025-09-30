package in.metalab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class AbstractPersistable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Field(name = "created_at")
	@CreatedDate
	private LocalDateTime createdAt;
	@Field(name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Field(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	@Field(name = "is_active")
	private Boolean isActive = Boolean.TRUE;
}
