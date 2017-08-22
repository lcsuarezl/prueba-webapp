package co.facturalo.project.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import co.facturalo.project.model.AppIssue;

/**
 * Carga la informacion necesaria para probar el aplicativo
 * 
 * @author leon
 *
 */

@Startup
@Singleton
public class AppLoader {

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	protected EntityManager em;

	private DataGenerator dg = new DataGenerator();

	public AppLoader() {
		super();
	}

	@PostConstruct
	private void generateIssues() {
		AppIssue ai;
		for (int i = 1; i < 30; i++) {
			ai = new AppIssue(dg.getPath(), dg.getNomHom(), dg.getNomMuj(), dg.getDate(), dg.getDate(), dg.getText(100),
					dg.getText(50));
			ai.setId(Long.valueOf(i));
			em.persist(ai);
			em.flush();
			em.detach(ai);
		}

	}

}
