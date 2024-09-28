package com.ChemQuest.ChemQuest.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "points")
@ToString
@EqualsAndHashCode
public class Points {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "points", nullable = false)
	@ColumnDefault("0")
	private Long points = 0L;
}
