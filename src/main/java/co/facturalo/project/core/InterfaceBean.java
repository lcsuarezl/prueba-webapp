package co.facturalo.project.core;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Define todos los metodos que debe tener nuestro bean de administraccion de
 * entidades
 * 
 * @author leon
 *
 * @param <T>
 *            Entidad a adminstrar
 * @param <E>
 *            Llave primaria de la Entidad a administrar
 */
public interface InterfaceBean<T, E extends Serializable> {

	/**
	 * Permite acceder al identificador del objeto siendo manejado, generalmente
	 * debe implementar la interfaz ITidPk pues todos los objetos manejados por
	 * un tenant deben contener la informacion del tenant en cuestion
	 * 
	 * @return
	 */
	public E getId();

	/**
	 * Permite generar el ID del elemento para persistencia, se crea puesto que
	 * una llave compuesta no acepta campos autoincrement ni secuencia, es
	 * necesario un metodo para generar el Id
	 * 
	 * @return
	 */
	public Long incrementId();

	public Long incrementId(Object e);

	public void setId(E id);

	/**
	 * Permite obtener la instancia manejada
	 * 
	 * @return
	 */
	public T getInstance();

	public void setInstance(T t);

	public T findById(E id);

	/**
	 * Permite buscar el objeto por el Id en la BD
	 */
	public void retrieve();

	/**
	 * Refresco el estado de mi instancia si estoy dentro de una conversacion
	 */
	public void refresh();

	/**
	 * Inicia un flujo de creacion de un objeto, incia la converzación y
	 * redirige a la pagina de creacion
	 * 
	 * @return
	 */
	public String start();

	/**
	 * Construye los id de navegacion hacia la visualizacion de una instancia
	 * por su ID dependiendo del tipo de ID
	 */
	public String toAction(String action);

	/**
	 * Construye los id de navegacion hacia la visualizacion de una instancia
	 * por su ID dependiendo del tipo de ID
	 */
	public String toAction(String action, Long l1);

	/**
	 * realiza las operaciones necesarias para visualizar correctamente la
	 * instancia en la visualizacion
	 */
	public String prepareView();

	/**
	 * Inicia la instancia de creacion a implementar en cada Bean segun
	 * carcterísticas propias del objeto a tratar
	 */
	public void prepareCreate();

	/**
	 * Inicia la instancia de actualizacion a implementar en cada Bean segun
	 * carcterísticas propias del objeto a tratar
	 */
	public void prepareUpdate();

	/**
	 * Valida las caracteristicas de una instancia para que sea permitida su
	 * creacion, se ejecuta justo antes de iniciar el bloque create que persiste
	 * el objeto en la base de datos, si su retorno es false no lo crea
	 */
	public boolean validateCreate();

	/**
	 * Valida las caracteristicas de una instancia para que sea permitida su
	 * actualización, se ejecuta justo antes de iniciar el bloque actualizacion
	 * que persiste el objeto en la base de datos, si su retorno es false no lo
	 * deja actualizar
	 */
	public boolean validateUpdate();

	/**
	 * Valida las caracteristicas de una instancia para que sea permitida su
	 * eliminación, se ejecuta justo antes de iniciar el bloque delete que
	 * elimina el objeto de la base de datos, si su retorno es false no lo deja
	 * eliminar
	 */
	public boolean validateDelete();

	public String navigate(String to);

	/**
	 * Redirige a la pagina de busqueda luego de persistir el elemento
	 */
	public String afterCreate();

	/**
	 * Redirige a la pagina de visualizacion por el ID luego de actualizar el
	 * elemento
	 */
	public String afterUpdate();

	/**
	 * Redirige a la pagina de busqueda luego de persistir el elemento
	 */
	public String afterDelete();

	/**
	 * persiste el objeto, establece en el Id que debe implementar ITidPK el id
	 * del tenant del usuario
	 * 
	 * @return
	 */
	public String create();

	/**
	 * Actualiza la informacion de un objeto manejado, valida primero que el id
	 * del objeto tenga el tenant de la sesion activa
	 * 
	 * @return
	 */
	public String update();

	/**
	 * Elimina un objeto manejado, valida primero que el id del objeto tenga el
	 * tenant de la sesion activa
	 * 
	 * @return
	 */
	public String delete();

	public int getPage();

	public void setPage(int page);

	public int getPageSize();

	public String search();

	public void reset();

	/**
	 * Metodo de restricciones de busquedas, lanza una excepcion en el caso que
	 * no haya un valor para TID pues no se permite la ejecucion de ningun
	 * metodo sin el filtro por tenant
	 * 
	 * @throws IOException
	 */
	public Predicate[] getSearchPredicates(Root<T> root);

	public void paginate();

	public List<T> getPageItems();

	public long getCount();

	public void setCount(long count);

	public List<T> getAll();

	public List<T> getAllParentsCandidate(int level);

	public void addMessage(FacesMessage message);
}
