package co.facturalo.project.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Es una Clase Abstracta que sirve como ID para algunos objetos de la App<br/>
 * Utiliza una MappedSuperclas que nos facilita la definicion de las PK en las
 * entidades de la app<br/>
 * Definimos la forma de Herencia como una Tabla por Clase, asi cada Entidad de
 * la app tiene su propia tabla<br/>
 * 
 * @author leon
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AAppObject implements IAppObject, Serializable {

	private static final long serialVersionUID = -2234890478573922205L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AAppObject other = (AAppObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
