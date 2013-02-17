import grails.util.GrailsUtil
import miamiam.SecRole
import miamiam.SecUser
import miamiam.SecUserSecRole
import miamiam.Category
import miamiam.Recipe

import grails.plugins.springsecurity.*

class BootStrap {

    def init = { servletContext ->
		
		switch (GrailsUtil.environment) {
			case "development":
				createDevelopmentEntries()
			break
			}
    	}

	def destroy = {
    }
	
	def createDevelopmentEntries = {
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

		// definition of default recipes
		def sale = new Category(name:'sale', description:'').save(failOnError: true)
		def sucre = new Category(name:'sucre', description:'').save(failOnError: true)
		new Recipe(name:'tarte a l\'oignon', description: 'etre de bonne humeur', category: sale).save(failOnError: true)

		
		def recipePaveChoco = new Recipe(name:'Pavés au chocolat', 
										ingredient: '250 gr de sucre\n' +
													'100 gr de farine\n' +
													'6 œufs\n' +
													'4 cuillères à soupe d\'amandes moulues\n' + 
													'250 gr de beurre\n' +
													'250 gr de chocolat\n',
										description: 'Mélanger les œufs et le sucre au fouet pour obtenir la crème.\n' + 
													'Puis faire fondre le chocolat et le beurre.\n' +
													'Ajouter à la crème la farine puis les amandes et enfin le mélange beurre chocolat.\n' + 
													'Beurrer la plaque de cuisson ou utiliser du papier sulfurisée pour que la pâte n\'attache pas .' + 
													'Étaler la pâte sur la plaque et laisser cuire pendant 20 à 25 minutes à la température de 180°C. ', 
										category: sucre
										).save(failOnError: true)

			
	}
}
