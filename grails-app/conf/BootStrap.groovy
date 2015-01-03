/*  <MiaMiam: Web application for publishing recipes
    Copyright (C) <2014>  <Michel Petrovic> <email: dotnetmobile@gmail.com>

    Contributor(s): _____________________.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import grails.util.Environment;
import grails.util.GrailsUtil
import miamiam.ReciepeGenerator
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
	}

	def initDefaultRecipes = {
		ReciepeGenerator defaultReciepes = new ReciepeGenerator()
		
		defaultReciepes.generateAll(grailsApplication.config)
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
