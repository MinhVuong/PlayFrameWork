package business;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

	//create an object of SingleObject
	 private static EntityManagerFactory instance = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
	   
	 private EntityManagerFactorySingleton(){}
	//Get the only object available
	public static EntityManagerFactory getInstance(){
		return instance;
	}
	   
	   
}
