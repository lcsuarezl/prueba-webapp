package co.facturalo.project.core;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Clase abstracta que se encaga de implementar de forma general los metodos
 * principales de crud busqueda seguridad y otros. Es la clase Core de nuestra
 * app, por medio de ella pasan todos los entities a la persistencia e
 * implementa de la forma mas general posible algunas operaciones transversales
 * 
 * @author leon
 *
 * @param <T>Entity
 *            Sobre la que se desea hacer CRUD
 * @param <E>Primary
 *            Key de la Entity sobre la que se desea hacer CRUD
 */
public abstract class AbstractBean<T, E extends Serializable> implements InterfaceBean<T, E>, Serializable {

	private static final long serialVersionUID = -4904566886281814879L;

	/**
	 * Tipo de llave que tiene nuestra entidad
	 */
	protected Class<E> idClass;

	/**
	 * Entidad que es gestionada por el AbstractBean
	 */
	protected Class<T> entityClass;

	/**
	 * Para realizar log de la aplicacion
	 */
	protected Logger logger = Logger.getLogger("AppLogger");

	/**
	 * Constructor de la clase, lo que hace es mirar el tipo de clase T que va a
	 * gestionar y construir una instancia vacia para la clas0 Te y para la
	 * llave de la clase E
	 */
	@SuppressWarnings("unchecked")
	public AbstractBean() {
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) superClass.getActualTypeArguments()[0];
		this.idClass = (Class<E>) superClass.getActualTypeArguments()[1];
		try {
			this.instance = this.entityClass.newInstance();
			if (!this.idClass.isInstance(new Long(0))) {
				this.id = this.idClass.newInstance();
				// no null para llaves primarias en elementos de tercer nivel
			}
			// this.setTid(new TidPK());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Justo despues del constructor realizamos la validacion de seguridad
	 * verificamos que exista un usuario logueado, que NO tenga asignado un
	 * Tenant si existe el usuario entonces lo ponemos en la variable de
	 * Auditoria Aud e incrementamos el contador de instancias creadas BeanId En
	 * caso que no exista el usuario entonces redirigimos al formulario de
	 * inicio de sesion
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void postConstruct() {

	}

	/**
	 * Cuando el ciclo de vida de nuestro bean termina lo quitamos del prfSess
	 */
	@PreDestroy
	public void preDestroy() {

	}

	/**
	 * Actualiza el valor de nuestra instancia de la base de datos, yendo a
	 * buscar los valores que tiene actualmente
	 */
	public void refresh() {
		this.em.refresh(this.instance);
	}

	/**
	 * La PK del Entity administrado por el bean
	 */
	protected E id;

	public E getId() {
		return this.id;
	}

	public void setId(E id) {
		this.id = id;
	}

	/**
	 * Calcula el id autoincrementado para un registro de tipo T
	 * 
	 * @param stringQuery
	 *            "SELECT max(o.id.id) FROM " + this.entityClass.getSimpleName()
	 *            + " o where o.id.tid=:tid" query que permite encontrar el id
	 *            maximo actual para dicho campo
	 * @return el id siguiente en la secuencia de numeracion de la tabla.
	 */
	public Long incrementId() {
		String stringQuery = "SELECT max(o.id) FROM " + this.entityClass.getSimpleName() + " o ";
		Query createQuery = this.em.createQuery(stringQuery);
		Long id = (Long) createQuery.getSingleResult();
		if (id != null) {
			return ++id;
		} else {
			return 1L;
		}
	}

	/**
	 * Calcula el incremento para el objeto que recibe como parametro
	 */
	public Long incrementId(Object e) {
		String stringQuery = "SELECT max(o.id) FROM " + e.getClass().getSimpleName() + " o";
		Query query = this.em.createQuery(stringQuery);
		Long id = (Long) query.getSingleResult();
		return (id != null) ? ++id : 1L;
	}

	/**
	 * El entity a ser administrado por el bean
	 */
	protected T instance;

	public T getInstance() {
		return this.instance;
	}

	public void setInstance(T t) {
		this.instance = t;
	}

	/**
	 * El encargado de todas las operaciones de ineraccion con la base de datos.
	 * Utilizamos el Contexto de persistencia de tipo Extendido así el contexto
	 * se inicia justo despues de la construccion del bean y termina en su
	 * destruccion
	 * 
	 */
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	protected EntityManager em;

	/**
	 * El metodo start inicia nuevamente la instancia de T
	 */
	public String start() {
		this.instance = newInstance();
		return null;
	}

	/**
	 * Similar al anterior, resetea la instancia a vacio
	 */
	public void reset() {
		this.instance = newInstance();
	}

	/**
	 * Crea una nueva instancia de T
	 * 
	 * @return
	 */
	public T newInstance() {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Llamamos a isPostBack para saber si se esta ejecutando en la primera
	 * peticion de la pagina o en una peticion ajax, si es ajax no hacemos nada.
	 * <br/>
	 * Si nuestro id es null entonces creamos una instancia vacia <br/>
	 * Si tenemos un id vamos a buscar en la BD y lo guardamos en instance
	 */
	public void retrieve() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.id == null) {
			this.instance = newInstance();
		} else {
			this.instance = this.findById(this.id);
		}
	}

	/**
	 * Construye los id de navegacion hacia la visualizacion de una instancia
	 * por su ID dependiendo del tipo de ID
	 */
	public String toAction(String action) {
		String path = "";
		if (this.id instanceof Long) {
			path = "id=" + id;
		}
		return action + "?" + path + "&faces-redirect=true";
	}

	/**
	 * Construye los id de navegacion hacia la visualizacion de una instancia
	 * por su ID dependiendo del tipo de ID y la acccion
	 */
	public String toAction(String action, Long id) {
		String path = "";
		if (id != null) {
			path = "id=".concat(id.toString());
		}
		return action + "?" + path + "&faces-redirect=true";
	}

	/**
	 * Realiza la acctualizacion de una instancia de T <br/>
	 * El proceso ejecuta primero una validacion validateUpdate()<br/>
	 * luego verifica si T es instancia de la interfaz de Auditoria, si lo es
	 * entoces pone el usuario actual para la auditoria<br/>
	 * guarda los cambios en la base de datos<br/>
	 * y ejecuta el metodo afterUpdate
	 */
	public String update() {
		if (validateUpdate()) {
			try {
				this.em.merge(this.instance);
				this.em.flush();
				return afterUpdate();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				e.printStackTrace();
				this.start();
				return null;
			}
		}
		return null;
	}

	/**
	 * Persiste una instancia de T en la base de datos <br/>
	 * Ejecuta las validaciones definidas en validateCreate <br/>
	 * Y realiza las acciones de auditoria si T es instancia de la interfaz de
	 * auditoria<br/>
	 * Guarda la instancia de T y ejecuta el metodo afterCreate
	 */
	public String create() {
		if (validateCreate()) {
			try {
				this.em.persist(this.instance);
				this.em.flush();
				return afterCreate();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Elimina una instancia de T en la base de datos <br/>
	 * Ejecuta las validaciones definidas en validateDelete <br/>
	 * Busca el elemento a eliminar en la BD<br/>
	 * Y realiza las acciones de auditoria si T es instancia de la interfaz de
	 * auditoria<br/>
	 * Guarda la instancia de T y ejecuta el metodo afterDelete
	 */
	public String delete() {
		if (validateDelete()) {
			try {
				T deletableEntity = findById(getId());
				if (deletableEntity == null) {
					return afterDelete();
				}
				logger.info("log para AbstractBean." + deletableEntity.getClass() + ".delete()");
				this.em.remove(deletableEntity);
				this.em.flush();
				return afterDelete();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				return null;
			}
		}
		return null;
	}

	/**
	 * Elimina una instancia de T por su id
	 * 
	 * @param tid
	 * @return
	 */
	public String delete(Long tid) {
		return delete();
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public boolean validateCreate() {
		return true;
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public boolean validateUpdate() {
		return true;
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public boolean validateDelete() {
		return true;
	}

	/**
	 * Metodo para navegar en la aplicacion, redirige a to
	 */
	public String navigate(String to) {
		return to + "?faces-redirect=true";
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public String prepareView() {
		return null;
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public void prepareCreate() {
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public void prepareUpdate() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		this.retrieve();
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public String afterCreate() {
		return "search?faces-redirect=true";
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public String afterUpdate() {
		return toAction("view");
	}

	/**
	 * Metodo para implementar en los beans concretos segun lo requiera el
	 * negocio las validaciones u operaciones correspondientes
	 */
	public String afterDelete() {
		return "search?faces-redirect=true";
	}

	/*
	 * Support searching Group entities with pagination
	 */

	/**
	 * La pagina actual en la busqueda
	 */
	private int page;

	/**
	 * Cantidad de elementos a mostrar en cada pagina
	 */
	private long count;

	/**
	 * Instancias de T encontradas en la BD para cada busqueda
	 */
	private List<T> pageItems;

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	/**
	 * Limpia la busqueda
	 */
	public String search() {
		try {
			this.instance = this.entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;// "search?faces-redirect=true";
	}

	/**
	 * Debe implementarce para cada bean segun parametros de busqueda
	 */
	public abstract Predicate[] getSearchPredicates(Root<T> root);

	/**
	 * Metodo que realiza la paginacion en las busquedas.<br/>
	 * Capturamos el parametro de la pagina de la peticion <br/>
	 * Construimos los criterios de busqueda y la sentencia SQL cutilizando el
	 * criteriaBuilder<br/>
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void paginate() {
		logger.info("query");
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			if (params.containsKey("page")) {
				this.page = Integer.valueOf(params.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		// Populate this.count
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<T> root = (Root<T>) countCriteria.from(this.instance.getClass());
		countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
		this.setCount(this.em.createQuery(countCriteria).getSingleResult());
		// Populate this.pageItems
		CriteriaQuery<T> criteria = (CriteriaQuery<T>) builder.createQuery(this.instance.getClass());
		root = (Root<T>) criteria.from(this.instance.getClass());
		TypedQuery<T> query = this.em.createQuery(criteria.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * this.getPageSize()).setMaxResults(getPageSize());
		this.pageItems = query.getResultList();
	}

	public List<T> getPageItems() {
		logger.info("pageItems");
		return this.pageItems;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCount() {
		return this.count;
	}

	/**
	 * Obtiene todos los elementos de T de la BD<br/>
	 * Se debe utilizar con precaucion pues carga todo a memoria!
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String stringQuery = "SELECT o FROM " + this.entityClass.getSimpleName() + " o ";
		Query createQuery = this.em.createQuery(stringQuery, this.instance.getClass());
		List<T> listT = createQuery.getResultList();
		return listT;
	}

	/**
	 * Metodo que retorna los candidatos a padre para una lista de seleccion
	 * anidada, solo deberia ser llamado por beans que manejen este tipo de
	 * listas
	 * 
	 * @param level
	 *            nivel a partir del cual miramos hacia arriba en la jerarquia
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllParentsCandidate(int level) {
		String stringQuery = "SELECT o FROM " + this.entityClass.getSimpleName()
				+ " o where o.l2Pk.tid=:tid and o.level<:level ";
		Query createQuery = this.em.createQuery(stringQuery, this.instance.getClass());
		List<T> listT = createQuery.getResultList();
		return listT;
	}

	/**
	 * Busca una instancia de T por una instancia de E id
	 */
	public T findById(E id) {
		return this.em.find(this.entityClass, id);
	}

	@Resource
	protected SessionContext sessionContext;

	/**
	 * Muestra un mensaje en la interfaz de usuario
	 */
	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
