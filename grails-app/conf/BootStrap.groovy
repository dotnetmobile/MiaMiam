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
		String fileCharlotte1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/CharlotteChocoBlanc_resized_250_160.JPG'
		String fileCharlotte2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/CharlotteChoco_resized_250_166.JPG'
		String fileCharlotte3 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/CharlotteChocoBlancCoupe_resized_250_160.JPG'
		associatePhoto(fileCharlotte1, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')
		associatePhoto(fileCharlotte2, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')
		associatePhoto(fileCharlotte3, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')

		String fileGateauMoelleuxChoco = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/moelleu_chocolat_resized_250_188.JPG'
		associatePhoto(fileGateauMoelleuxChoco, 1, 'Gâteau moelleux au chocolat', 'Gâteau moelleux au chocolat')
		
		String fileTarteChocoFondant = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/tarte_chocolat_fondant_resized_250_188.JPG'
		associatePhoto(fileTarteChocoFondant, 2, 'Ma tarte au chocolat fondante', 'Ma tarte au chocolat fondante')

		String fileMilleFeuilles1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/mille_feuille_v1_resized_250_188.JPG'
		String fileMilleFeuilles2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/mille_feuille_v2_resized_250_188.JPG'
		String fileMilleFeuilles3 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/mille_feuille_v3_resized_250_188.JPG'
		String fileMilleFeuilles4 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/mille_feuille_v4_resized_250_188.JPG'
		associatePhoto(fileMilleFeuilles1, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles2, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles3, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles4, 3, 'Millefeuilles', 'Millefeuilles')

		String fileTiramisu = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/tiramisu_v2_resized_250_188.JPG'
		associatePhoto(fileTiramisu, 5, 'Tiramisu', 'Tiramisu')

		String fileBeignets1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/beignet_sale_cut_resized_250_188.JPG'
		String fileBeignets2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/beignet_sale_resized_250_188.JPG'
		associatePhoto(fileBeignets1, 6, 'Beignets salés de mon enfance', 'Beignets salés de mon enfance')
		associatePhoto(fileBeignets2, 6, 'Beignets salés de mon enfance', 'Beignets salés de mon enfance')

		String fileCakeSale = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/cake_sale_resized_250_180.JPG'
		associatePhoto(fileCakeSale, 7, 'Cake salé', 'Cake salé')

		String fileMoelleuxMais1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/moelleu_mais_fromage_resized_250_188.JPG'
		String fileMoelleuxMais2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/moelleu_mais_fromage_vertical_resized_188_250.JPG'
		associatePhoto(fileMoelleuxMais1, 8, 'Moelleux de maïs au fromage', 'Moelleux de maïs au fromage')
		associatePhoto(fileMoelleuxMais2, 8, 'Moelleux de maïs au fromage', 'Moelleux de maïs au fromage')

		String fileTuilesParmesan = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/tuile_parmesan_resized_250_166.JPG'
		associatePhoto(fileTuilesParmesan, 9, 'Tuiles au parmesań', 'Tuiles au parmesań')

		String fileClafoutis1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/clafoutti_coupe_resized_250_188.JPG'
		String fileClafoutis2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/clafoutti_resized250_188.JPG'
		associatePhoto(fileClafoutis1, 10, 'Clafoutis aux fruits de son choix', 'Clafoutis aux fruits de son choix')
		associatePhoto(fileClafoutis2, 10, 'Clafoutis aux fruits de son choix', 'Clafoutis aux fruits de son choix')

		String fileCoupelle1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/Coupelle_croquer_resized_250_188.JPG'
		String fileCoupelle2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/Coupelle_croquer_zoom_resized_250_188.JPG'
		associatePhoto(fileCoupelle1, 11, 'Coupellle crémeuse à croquer', 'Coupellle crémeuse à croquer')
		associatePhoto(fileCoupelle2, 11, 'Coupellle crémeuse à croquer', 'Coupellle crémeuse à croquer')

		String fileCremeBrulee = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/creme_brullee_resized_250_188.JPG'
		associatePhoto(fileCremeBrulee, 12, 'Crème brûlée à la vanillé', 'Crème brûlée à la vanillé')
		
		String fileFondantMiCuit = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/fondant_chocolat_resized_250_333.JPG'
		associatePhoto(fileFondantMiCuit, 13, 'Fondant au chocolat mi-cuit́', 'Fondant au chocolat mi-cuit́')

		String fileTarteFromage1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/gateau_fromage_1_resized_250_188.JPG'
		String fileTarteFromage2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/gateau_fromage_2_resized_250_188.JPG'
		String fileTarteFromage3 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/gateau_fromage_3_resized_250_188.JPG'
		String fileTarteFromage4 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/gateau_fromage_morceau_resized_250_188.JPG'
		associatePhoto(fileTarteFromage1, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage2, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage3, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage4, 16, 'Tarte au fromage', 'Tarte au fromage')

		String fileBeignetAlsacien = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/beignet_alsacien_resized_250_188.JPG'
		associatePhoto(fileBeignetAlsacien, 17, 'Beignets alsacienś', 'Beignets alsacienś')

		String fileBrownies = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/Brownies_resized_250_188.JPG'
		associatePhoto(fileBrownies, 18, 'Brownies fait maisoń', 'Brownies fait maisoń')

		String fileCakeNoixOrange1 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/cake_orange_coupe_250_160.JPG'
		String fileCakeNoixOrange2 = '/Users/michel_petrovic/Desktop/PiqueAssiettePhotos/cake_orange_resized_250_188.JPG'
		associatePhoto(fileCakeNoixOrange1, 19, 'Cake noix/orange', 'Cake noix/orange')
		associatePhoto(fileCakeNoixOrange2, 19, 'Cake noix/orange', 'Cake noix/orange')
	}

	def associatePhoto(file, recipeId, name, description) {
		File image = new File(file)
		
		def recipe = Recipe.get(recipeId)
		def recipePhoto = new PhotoStep(name : name,
									   description : description,
									   photo : image.getBytes(),
									   recipe : recipe).save(failOnError: true)

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
