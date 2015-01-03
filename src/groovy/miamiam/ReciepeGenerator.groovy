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
package miamiam

import groovy.sql.Sql

class ReciepeGenerator {
	
    void generateAll(def config) {
		initDefaultRecipes(config)
		
		initDefaultRecipePhotos()
	}
	
	void initDefaultRecipes(def config)  {
		String sqlFilePath = './data/AllRecipes.sql'
		String sqlString = new File(sqlFilePath).text

		def sql = Sql.newInstance(config.dataSource.url,
				config.dataSource.username,
				config.dataSource.password,
				config.dataSource.driverClassName)

		sql.execute(sqlString)
	}

def initDefaultRecipePhotos = {
		String recipePicturesPath = './data/pictures/'
		
		String fileCharlotte1 = recipePicturesPath + 'CharlotteChocoBlanc_resized_250_160.JPG'
		String fileCharlotte2 = recipePicturesPath + 'CharlotteChoco_resized_250_166.JPG'
		String fileCharlotte3 = recipePicturesPath + 'CharlotteChocoBlancCoupe_resized_250_160.JPG'
		associatePhoto(fileCharlotte1, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')
		associatePhoto(fileCharlotte2, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')
		associatePhoto(fileCharlotte3, 0, 'Charlotte au chocolat légère', 'Charlotte au chocolat légère')

		String fileGateauMoelleuxChoco = recipePicturesPath + 'moelleu_chocolat_resized_250_188.JPG'
		associatePhoto(fileGateauMoelleuxChoco, 1, 'Gâteau moelleux au chocolat', 'Gâteau moelleux au chocolat')
		
		String fileTarteChocoFondant = recipePicturesPath + 'tarte_chocolat_fondant_resized_250_188.JPG'
		associatePhoto(fileTarteChocoFondant, 2, 'Ma tarte au chocolat fondante', 'Ma tarte au chocolat fondante')

		String fileMilleFeuilles1 = recipePicturesPath + 'mille_feuille_v1_resized_250_188.JPG'
		String fileMilleFeuilles2 = recipePicturesPath + 'mille_feuille_v2_resized_250_188.JPG'
		String fileMilleFeuilles3 = recipePicturesPath + 'mille_feuille_v3_resized_250_188.JPG'
		String fileMilleFeuilles4 = recipePicturesPath + 'mille_feuille_v4_resized_250_188.JPG'
		associatePhoto(fileMilleFeuilles1, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles2, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles3, 3, 'Millefeuilles', 'Millefeuilles')
		associatePhoto(fileMilleFeuilles4, 3, 'Millefeuilles', 'Millefeuilles')

		String fileTiramisu = recipePicturesPath + 'tiramisu_v2_resized_250_188.JPG'
		associatePhoto(fileTiramisu, 5, 'Tiramisu', 'Tiramisu')

		String fileBeignets1 = recipePicturesPath + 'beignet_sale_cut_resized_250_188.JPG'
		String fileBeignets2 = recipePicturesPath + 'beignet_sale_resized_250_188.JPG'
		associatePhoto(fileBeignets1, 6, 'Beignets salés de mon enfance', 'Beignets salés de mon enfance')
		associatePhoto(fileBeignets2, 6, 'Beignets salés de mon enfance', 'Beignets salés de mon enfance')

		String fileCakeSale = recipePicturesPath + 'cake_sale_resized_250_180.JPG'
		associatePhoto(fileCakeSale, 7, 'Cake salé', 'Cake salé')

		String fileMoelleuxMais1 = recipePicturesPath + 'moelleu_mais_fromage_resized_250_188.JPG'
		String fileMoelleuxMais2 = recipePicturesPath + 'moelleu_mais_fromage_vertical_resized_188_250.JPG'
		associatePhoto(fileMoelleuxMais1, 8, 'Moelleux de maïs au fromage', 'Moelleux de maïs au fromage')
		associatePhoto(fileMoelleuxMais2, 8, 'Moelleux de maïs au fromage', 'Moelleux de maïs au fromage')

		String fileTuilesParmesan = recipePicturesPath + 'tuile_parmesan_resized_250_166.JPG'
		associatePhoto(fileTuilesParmesan, 9, 'Tuiles au parmesań', 'Tuiles au parmesań')

		String fileClafoutis1 = recipePicturesPath + 'clafoutti_coupe_resized_250_188.JPG'
		String fileClafoutis2 = recipePicturesPath + 'clafoutti_resized250_188.JPG'
		associatePhoto(fileClafoutis1, 10, 'Clafoutis aux fruits de son choix', 'Clafoutis aux fruits de son choix')
		associatePhoto(fileClafoutis2, 10, 'Clafoutis aux fruits de son choix', 'Clafoutis aux fruits de son choix')

		String fileCoupelle1 = recipePicturesPath + 'Coupelle_croquer_resized_250_188.JPG'
		String fileCoupelle2 = recipePicturesPath + 'Coupelle_croquer_zoom_resized_250_188.JPG'
		associatePhoto(fileCoupelle1, 11, 'Coupellle crémeuse à croquer', 'Coupellle crémeuse à croquer')
		associatePhoto(fileCoupelle2, 11, 'Coupellle crémeuse à croquer', 'Coupellle crémeuse à croquer')

		String fileCremeBrulee = recipePicturesPath + 'creme_brullee_resized_250_188.JPG'
		associatePhoto(fileCremeBrulee, 12, 'Crème brûlée à la vanillé', 'Crème brûlée à la vanillé')
		
		String fileFondantMiCuit = recipePicturesPath + 'fondant_chocolat_resized_250_333.JPG'
		associatePhoto(fileFondantMiCuit, 13, 'Fondant au chocolat mi-cuit́', 'Fondant au chocolat mi-cuit́')

		String fileTarteFromage1 = recipePicturesPath + 'gateau_fromage_1_resized_250_188.JPG'
		String fileTarteFromage2 = recipePicturesPath + 'gateau_fromage_2_resized_250_188.JPG'
		String fileTarteFromage3 = recipePicturesPath + 'gateau_fromage_3_resized_250_188.JPG'
		String fileTarteFromage4 = recipePicturesPath + 'gateau_fromage_morceau_resized_250_188.JPG'
		associatePhoto(fileTarteFromage1, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage2, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage3, 16, 'Tarte au fromage', 'Tarte au fromage')
		associatePhoto(fileTarteFromage4, 16, 'Tarte au fromage', 'Tarte au fromage')

		String fileBeignetAlsacien = recipePicturesPath + 'beignet_alsacien_resized_250_188.JPG'
		associatePhoto(fileBeignetAlsacien, 17, 'Beignets alsacienś', 'Beignets alsacienś')

		String fileBrownies = recipePicturesPath + 'Brownies_resized_250_188.JPG'
		associatePhoto(fileBrownies, 18, 'Brownies fait maisoń', 'Brownies fait maisoń')

		String fileCakeNoixOrange1 = recipePicturesPath + 'cake_orange_coupe_250_160.JPG'
		String fileCakeNoixOrange2 = recipePicturesPath + 'cake_orange_resized_250_188.JPG'
		associatePhoto(fileCakeNoixOrange1, 19, 'Cake noix/orange', 'Cake noix/orange')
		associatePhoto(fileCakeNoixOrange2, 19, 'Cake noix/orange', 'Cake noix/orange')

		String fileChinois = recipePicturesPath + 'chinois_resized_250_188.JPG'
		associatePhoto(fileChinois, 20, 'Chinois', 'Chinois')


		String fileGateauChocoPatissier1 = recipePicturesPath + 'gateau_choco_resized_250_188.JPG'
		String fileGateauChocoPatissier2 = recipePicturesPath + 'gateau_chocolat_patissier_coupe_resized_250_166.JPG'
		associatePhoto(fileGateauChocoPatissier1, 21, 'Gâteau chocolat comme le pâtissier', 'Gâteau chocolat comme le pâtissier')
		associatePhoto(fileGateauChocoPatissier2, 21, 'Gâteau chocolat comme le pâtissier', 'Gâteau chocolat comme le pâtissier')

		String fileGaufrePomme = recipePicturesPath + 'graufre_pomme_resized_250_188.JPG'
		associatePhoto(fileGaufrePomme, 22, 'Gaufres à la pomme', 'Gaufres à la pomme')

		String fileMesCroquants = recipePicturesPath + 'croquant_prefere_250_166.JPG'
		associatePhoto(fileMesCroquants, 23, 'Mes croquants préférés', 'Mes croquants préférés')
		
		String fileMuffins1 = recipePicturesPath + 'mufin_v2_resized_250_188.JPG'
		String fileMuffins2 = recipePicturesPath + 'mufin_v3_resized_250_188.JPG'
		String fileMuffins3 = recipePicturesPath + 'mufin_v4_resized_250_188.JPG'
		String fileMuffins4 = recipePicturesPath + 'mufin_v6_resized_250_188.JPG'
		associatePhoto(fileMuffins1, 26, 'Muffins', 'Muffins')
		associatePhoto(fileMuffins2, 26, 'Muffins', 'Muffins')
		associatePhoto(fileMuffins3, 26, 'Muffins', 'Muffins')
		associatePhoto(fileMuffins4, 26, 'Muffins', 'Muffins')

		String fileRizAuLait1 = recipePicturesPath + 'gateau_riz_resized_250_188.JPG'
		String fileRizAuLait2 = recipePicturesPath + 'gateau__riz_v2_resized_250_188.JPG'
		associatePhoto(fileRizAuLait1, 27, 'Riz au lait parfumé à la cannelle/citron', 'Riz au lait parfumé à la cannelle/citron')
		associatePhoto(fileRizAuLait2, 27, 'Riz au lait parfumé à la cannelle/citron', 'Riz au lait parfumé à la cannelle/citron')

		String fileBouletteRiz1 = recipePicturesPath + 'boulette_de_riz_coupee_resized_250_188.JPG'
		String fileBouletteRiz2 = recipePicturesPath + 'Boulettes_de_riz_250_188.JPG'
		associatePhoto(fileBouletteRiz1, 29, 'Boulettes de riz', 'Boulettes de riz')
		associatePhoto(fileBouletteRiz2, 29, 'Boulettes de riz', 'Boulettes de riz')

		String fileChoucroute = recipePicturesPath + 'choucroute_resized_255_166.JPG'
		associatePhoto(fileChoucroute, 30, 'Choucroute façon belle-mère', 'Choucroute façon belle-mère')

		String filePoivronFarci1 = recipePicturesPath + 'choux_farci_1_resized_250_188.JPG'
		String filePoivronFarci2 = recipePicturesPath + 'choux_farci_2_resized_250_188.JPG'
		String filePoivronFarci3 = recipePicturesPath + 'choux_farci_3_resized_250_188.JPG'
		String filePoivronFarci4 = recipePicturesPath + 'choux_farci_resized_250_166.JPG'
		associatePhoto(filePoivronFarci1, 31, 'Choux ou poivrons farcis', 'Choux ou poivrons farcis')
		associatePhoto(filePoivronFarci2, 31, 'Choux ou poivrons farcis', 'Choux ou poivrons farcis')
		associatePhoto(filePoivronFarci3, 31, 'Choux ou poivrons farcis', 'Choux ou poivrons farcis')
		associatePhoto(filePoivronFarci4, 31, 'Choux ou poivrons farcis', 'Choux ou poivrons farcis')

		String fileGalettePommeTerre1 = recipePicturesPath + 'galette_pdt_resized_250_188.JPG'
		String fileGalettePommeTerre2 = recipePicturesPath + 'galette_pdt_stuck_resized_250_188.JPG'
		associatePhoto(fileGalettePommeTerre1, 32, 'Galette de pommes de terre', 'Galette de pommes de terre')
		associatePhoto(fileGalettePommeTerre2, 32, 'Galette de pommes de terre', 'Galette de pommes de terre')

		String fileHamburger1 = recipePicturesPath + 'hamburger_closed_vertical_resized_188_250.JPG'
		String fileHamburger2 = recipePicturesPath + 'hamburger_vertical_resized_188_250.JPG'
		String fileHamburger3 = recipePicturesPath + 'hamburger_hori_closed_resized_250_188.JPG'
		String fileHamburger4 = recipePicturesPath + 'hamburger_hori_opened_resized_250_188.JPG'
		associatePhoto(fileHamburger1, 33, 'Hamburger fait maison', 'Hamburger fait maison')
		associatePhoto(fileHamburger2, 33, 'Hamburger fait maison', 'Hamburger fait maison')
		associatePhoto(fileHamburger3, 33, 'Hamburger fait maison', 'Hamburger fait maison')
		associatePhoto(fileHamburger4, 33, 'Hamburger fait maison', 'Hamburger fait maison')

		String filePouele = recipePicturesPath + 'pouele_resized_250_160.JPG'
		associatePhoto(filePouele, 34, 'La poêlée réclamée par mes fils', 'La poêlée réclamée par mes fils')

		String filePizza = recipePicturesPath + 'pizza_resized_250_188.JPG'
		associatePhoto(filePizza, 35, 'Pizza', 'Pizza')
	}

	def associatePhoto(file, recipeId, name, description) {
		File image = new File(file)
		
		def recipe = Recipe.get(recipeId)
		def recipePhoto = new PhotoStep(name : name,
									   description : description,
									   photo : image.getBytes(),
									   recipe : recipe).save(failOnError: true)

	}

	
}
