import grails.util.Environment;
import grails.util.GrailsUtil
import miamiam.SecRole
import miamiam.SecUser
import miamiam.SecUserSecRole
import miamiam.Category
import miamiam.Recipe
import miamiam.PhotoStep

import grails.plugins.springsecurity.*
import groovy.sql.Sql


class BootStrap {
	def grailsApplication
	
    def init = { servletContext ->
		
		String current = Environment.getCurrent()
		
		switch (current) {
			case "DEVELOPMENT":
				createDevelopmentEntries()
			break
			}
    	}

	def destroy = {
    }
	
    def createAdmin = {
		// definition of the security users and roles
		def springSecurityService
		
		def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
		def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
		
		def adminUser = SecUser.findByUsername('admin') ?: new SecUser(username: 'supernana',
																	   //password: springSecurityService.encodePassword('admin'),
																	   password: 'leo+tom',
																	   enabled: true).save(failOnError: true)
		
		if (!adminUser.authorities.contains(adminRole)) {
			SecUserSecRole.create adminUser, adminRole
		}

	}	
	
	def createDevelopmentEntries = {

		createAdmin()
		 
		initDefaultRecipes()
		
		initDefaultRecipePhotos()
	}

	def initDefaultRecipes =  {
		String sqlFilePath = './data/AllRecipes.sql'
		String sqlString = new File(sqlFilePath).text

		def config = grailsApplication.config

		def sql = Sql.newInstance(config.dataSource.url,
				config.dataSource.username,
				config.dataSource.password,
				config.dataSource.driverClassName)

		sql.execute(sqlString)
	}
	
	def initDefaultRecipePhotos = {
		String sqlFilePath = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/CharlotteChocoBlanc_resized_250_160.JPG'
		File image = new File(sqlFilePath)
		
		
		
		def charlotteRecipe = Recipe.get(0)
		def charlottePhoto1 = new PhotoStep(name : 'Charlotte au chocolat légère',
			                           description : 'Charlotte au chocolat légère',
									   photo : image.getBytes(),
									   recipe : charlotteRecipe).save(failOnError: true)	
	}
	
	def createDevelopmentEntriesGORM = {
		
		createAdmin()

		// definition of default recipes
		def sale = new Category(name:'sale', description:'').save(failOnError: true)
		def sucre = new Category(name:'sucre', description:'').save(failOnError: true)
		new Recipe(name:'tarte a l\'oignon', description: 'etre de bonne humeur', category: sale).save(failOnError: true)

		
		def recipePaveChoco = new Recipe(name:'Pav�s au chocolat', 
										ingredient: '250 gr de sucre\n' +
													'100 gr de farine\n' +
													'6 �ufs\n' +
													'4 cuill�res � soupe d\'amandes moulues\n' + 
													'250 gr de beurre\n' +
													'250 gr de chocolat\n',
										description: 'M�langer les �ufs et le sucre au fouet pour obtenir la cr�me.\n' + 
													'Puis faire fondre le chocolat et le beurre.\n' +
													'Ajouter � la cr�me la farine puis les amandes et enfin le m�lange beurre chocolat.\n' + 
													'Beurrer la plaque de cuisson ou utiliser du papier sulfuris�e pour que la p�te n\'attache pas .' + 
													'�taler la p�te sur la plaque et laisser cuire pendant 20 � 25 minutes � la temp�rature de 180�C. ', 
										category: sucre
										).save(failOnError: true)

			
	}
}
